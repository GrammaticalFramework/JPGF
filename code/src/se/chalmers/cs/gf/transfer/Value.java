/*
 * Value.java
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

public abstract class Value {

	public static final Value TYPE = new VType();
	public static final Value TRUE = new VCons("True");
	public static final Value FALSE = new VCons("False");
	public static final Value LT = new VCons("LT");
	public static final Value EQ = new VCons("EQ");
	public static final Value GT = new VCons("GT");

	public interface Visitor <R,A> {
		public R visit(VStr p, A arg);
		public R visit(VInt p, A arg);
		public R visit(VDbl p, A arg);
		public R visit(VType p, A arg);
		public R visit(VRec p, A arg);
		public R visit(VClos p, A arg);
		public R visit(VCons p, A arg);
		public R visit(VPrim p, A arg);
		public R visit(VMeta p, A arg);
	}

	public abstract <R,A> R accept(Visitor<R,A> v, A arg);

	public static Value toBool(boolean b) { return b ? TRUE : FALSE; }

	public static Value toOrdering(int c) { 
		switch (c) {
		case -1: return LT;
		case 0:  return EQ;
		case 1:  return GT;
		default: throw new GFException("Bad ordering value: " + c);
		}
	}

	public static boolean isTrue(Value v) {
		if (v instanceof VCons) {
			return ((VCons)v).getCons().equals("True");
		}
		throw new GFException(v + " is not a Bool.");
	}


	public static class VStr extends Value {
		private final String str;
		public VStr(String str) {
			this.str = str;
		}
		public String getString() { return str; }
		public String toString() {
			return se.chalmers.cs.gf.util.StringUtil.singleQuote(str);
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VInt extends Value {
		private final Integer integer; // FIXME: use BigInteger
		public VInt(Integer integer) {
			this.integer = integer;
		}
		public Integer getInteger() { return integer; }
		public String toString() {
			return integer.toString();
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VDbl extends Value {
		private final Double dbl;
		public VDbl(Double dbl) {
			this.dbl = dbl;
		}
		public Double getDouble() { return dbl; }
		public String toString() {
			return Double.toString(dbl);
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VType extends Value {
		public VType() {
		}
		public String toString() {
			return "Type";
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VRec extends Value {
		private final Map<String,Value> rec;
		public VRec(Map<String,Value> rec) {
			this.rec = rec;
		}
		public Value get(String field) {
			Value v = rec.get(field);
			if (v == null)
				throw new GFException(this + " has no field " + field);
			return v;
		}
		public String toString() {
			return rec.toString();
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VClos extends Value {
		private final Env env;
		private final Exp exp;
		public VClos(Env env, Exp exp) {
			this.env = env;
			this.exp = exp;
		}
		public Env getEnv() { return env; }
		public Exp getExp() { return exp; }
		public String toString() {
			return "<closure (" + exp + ")>";
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}

	public static class VCons extends Value {
		private final String cons;
		private final List<Value> args;
		public VCons(String cons) {
			this(cons,Collections.<Value>emptyList());
		}
		public VCons(String cons, List<Value> args) {
			this.cons = cons;
			this.args = args;
		}
		public String getCons() { return cons; }
		public int numArgs() { return args.size(); }
		public Value getArg(int i) { return args.get(i); }
		public String toString() {
			StringBuilder sb = new StringBuilder(cons);
			for (Value c : args) {
				sb.append(' ');
				if (c instanceof VCons && !((VCons)c).args.isEmpty()) {
					sb.append('(').append(c.toString()).append(')');
				} else {
					sb.append(c.toString());
				}
			}
			return sb.toString();
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}

	public static abstract class VPrim extends Value {
		private String name;
		public VPrim(String name) { this.name = name; }
		public String getName() { return name; }
		public abstract Value apply(Value arg);
		public String toString() {
			return "<primitive operation>";
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
	public static class VMeta extends Value {
		private final BigInteger n;
		public VMeta(BigInteger n) {
			this.n = n;
		}
		public String toString() {
			return "?" + n;
		}
		public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }
	}
}
