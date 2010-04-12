/*
 * InterpretCore.java
 * Copyright (C) 2006, Bjorn Bringert (bringert@cs.chalmers.se)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.transfer;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.Core.Absyn.*;
import java.math.BigInteger;
import java.util.*;

public class InterpretCore {

	public static Value eval(Exp exp, Env env) {
		return exp.accept(evaluator, env);
	}

	public static Value apply(Value v1, Value v2) {
		if (v1 instanceof Value.VClos) {
			Value.VClos vc = (Value.VClos)v1;
			if (vc.getExp() instanceof EAbs) {
				EAbs a = (EAbs)vc.getExp();
				Env env2 = bind(a.patternvariable_, v2, vc.getEnv());
				return eval(a.exp_, env2);
			}
		} else if (v1 instanceof Value.VPrim) {
			Value.VPrim vp = (Value.VPrim)v1;
			return vp.apply(v2);
		} else if (v1 instanceof Value.VCons) {
			Value.VCons vc = (Value.VCons)v1;
			List<Value> args = new ArrayList<Value>(vc.numArgs()+1);
			for (int i = 0; i < vc.numArgs(); i++) {
				args.add(vc.getArg(i));
			}
			args.add(v2);
			return new Value.VCons(vc.getCons(),args);
		}
		throw new GFException("Bad application (" + v1 + ") (" 
				      + v2 + ")");
	}

	public static Env match(Pattern p, Value v) {
		return p.accept(matcher, v);
	}

	public static Env bind(PatternVariable pv, final Value v, Env env) {
		return pv.accept(new PatternVariable.Visitor<Env,Env>() {
			public Env visit(PVVar p, Env env) { 
				Env env2 = new Env(env); 
				env2.add(p.cident_, v);
				return env2;
			}
			public Env visit(PVWild p, Env env) { return env; }

		}, env);
	}

	private static Eval evaluator = new Eval();

	private static class Eval implements Exp.Visitor<Value,Env> {
		public Value visit(ELet p, Env env) {
			Env env2 = new Env(env);
			for (LetDef d : p.listletdef_) {
				String name = d.cident_;
				Value value = eval(d.exp_, env2);
				env2.add(name,value);
			}
			return eval(p.exp_, env2);
		}

		public Value visit(ECase p, Env env) {
			Value v = eval(p.exp_, env);
			for (Case c : p.listcase_) {
				Env bs = match(c.pattern_, v);
				if (bs != null) {
					Env env2 = new Env(env);
					env2.addAll(bs);
					if (Value.isTrue(eval(c.exp_1, env2)))
						return eval(c.exp_2, env2);
				}
			}
			throw new GFException("No pattern matched " + v);
		}

		public Value visit(EAbs p, Env env) {
			return new Value.VClos(env,p);
		}
		public Value visit(EPi p, Env env) {
			return new Value.VClos(env,p);
		}
		public Value visit(EApp p, Env env) {
			Value v1 = eval(p.exp_1,env);
			Value v2 = eval(p.exp_2,env);
			return apply(v1, v2);
		}
		public Value visit(EProj p, Env env) {
			Value v = eval(p.exp_, env);
			if (v instanceof Value.VRec) {
				return ((Value.VRec)v).get(p.cident_);
			}
			throw new GFException(v + " is not a record, can't get field " 
					      + p.cident_ + ".");
		}

		public Value visit(ERecType p, Env env) {
			Map<String,Value> rec = new HashMap<String,Value>();
			for (FieldType fv : p.listfieldtype_) {
				Value v = eval(fv.exp_, env);
				rec.put(fv.cident_, v);
			}
			return new Value.VRec(rec);
		}

		public Value visit(ERec p, Env env) {
			Map<String,Value> rec = new HashMap<String,Value>();
			for (FieldValue fv : p.listfieldvalue_) {
				Value v = eval(fv.exp_, env);
				rec.put(fv.cident_, v);
			}
			return new Value.VRec(rec);
		}

		public Value visit(EVar p, Env env) {
			return env.lookup(p.cident_);
		}

		public Value visit(EType p, Env env) {
			return new Value.VType();
		}

		public Value visit(EStr p, Env env) {
			return new Value.VStr(p.string_);
		}

		public Value visit(EInteger p, Env env) {
			return new Value.VInt(p.integer_);
		}

		public Value visit(EDouble p, Env env) {
			return new Value.VDbl(p.double_);
		}

		public Value visit(EMeta p, Env env) {
			return new Value.VMeta(new BigInteger(p.tmeta_.substring(1)));
		}

	}

	private static Match matcher = new Match();

	private static class Match implements Pattern.Visitor<Env,Value> {
		public Env visit(PCons p, Value v) {
			if (v instanceof Value.VCons) {
				Value.VCons vc = (Value.VCons)v;
				if (p.cident_.equals(vc.getCons())) {
					if (p.listpattern_.size() !=
					    vc.numArgs()) {
						throw new GFException("Wrong number of arguments to " + vc.getCons());
					}
					Env bs = new Env();
					for (int i = 0; i < p.listpattern_.size(); i++) {
						Env b = match(p.listpattern_.get(i), vc.getArg(i));
						if (b == null)
							return null;
						bs.addAll(b);
					}
					return bs;
				}
			}
			return null;
		}
		public Env visit(PVar p, Value v) {
			return bind(p.patternvariable_,v,new Env());
		}
		public Env visit(PRec p, Value v) {
			if (v instanceof Value.VRec) {
				Value.VRec vr = (Value.VRec)v;
				Env bs = new Env();
				for (FieldPattern fp : p.listfieldpattern_) {
					Env b = match(fp.pattern_,vr.get(fp.cident_));
					if (b == null)
						return null;
					bs.addAll(b);	
				}
				return bs;
			}
			return null;
		}
		public Env visit(PStr p, Value v) {
			if (v instanceof Value.VStr) {
				Value.VStr vs = (Value.VStr)v;
				if (vs.getString().equals(p.string_))
					return new Env();
			}
			return null;
		}
		public Env visit(PInt p, Value v) {
			if (v instanceof Value.VInt) {
				Value.VInt vi = (Value.VInt)v;
				if (vi.getInteger() == p.integer_)
					return new Env();
			}
			return null;
		}
	}

}
