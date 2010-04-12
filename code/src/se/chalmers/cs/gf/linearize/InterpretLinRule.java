/*
 * InterpretLinRule.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.linearize;

import se.chalmers.cs.gf.GFC.*;
import se.chalmers.cs.gf.GFC.Absyn.*;
import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.linearize.gfvalue.*;
import se.chalmers.cs.gf.util.Pair;
import se.chalmers.cs.gf.GFException;

import java.util.*;

import static se.chalmers.cs.gf.linearize.gfvalue.Concat.concat;



/**
 *  Does linearization by interpreting a GFC linearization rule.
 */
public class InterpretLinRule implements Term.Visitor<Value,Value[]> {

	
	private GFCModule module;
	
	public InterpretLinRule(GFCModule module) {
		this.module = module;
	}

        /**
         *  Linearize a Token.
         */
        private static Tokn.Visitor<Value,Object> toknToValue 
                = new Tokn.Visitor<Value,Object>() {
                        public Value visit(KS p, Object arg) {
                                return new Str(p.string_);
                        }

                        /* Prefix-dependent choice */
                        // KP.   Tokn  ::= "[" "pre" [String] "{" [Variant] "}" "]" ;
                        // Var.  Variant ::= [String] "/" [String] ;
                        // default, then value / prefix?
                        public Value visit(KP p, Object arg) {
                                Value def = concat(p.liststring_);

                                List<Pair<Value,List<String>>> vs = 
                                  new LinkedList<Pair<Value,List<String>>>();
                                for (Variant var : p.listvariant_) {
                                        // FIXME: use visitor
                                        Var v = (Var)var;
                                        Value t = concat(v.liststring_1);
                                        vs.add(new Pair<Value,List<String>>(t, v.liststring_2));
                                }

                                return new Pre(def, vs);
                        }

                        // This is an internal GF construction
                        public Value visit(KM p, Object arg) {
                                return unused(this, p, arg);
                        }
                };

        /**
         *  Convert a Label to a string.
         */
        private static Label.Visitor<String,Object> labelToString 
                = new Label.Visitor<String,Object>() {
                        public String visit(L p, Object arg) {
                                return p.ident_;
                        }

                        // FIXME: implement
                        // LV.   Label  ::= "$" Integer ;
                        // Doesn't seem to occur in canonical GF grammars?
                        public String visit(LV p, Object arg) {
                                 return unused(this, p, arg); 
                        }
                };

        /**
         *  Convert a CIdent to a string.
         */
        private static String cidentToString(CIdent cid) {
                return cid.accept(new CIdent.Visitor<String,Object>() {
                        public String visit(CIQ p, Object arg) {
                                return p.ident_1 + "." + p.ident_2;
                        }}, null);
        }

        /**
         *  Convert an ArgVar to a Value, using the function arguments.
         */
        private static ArgVar.Visitor<Value,Value[]> argVarValue
                = new ArgVar.Visitor<Value,Value[]>() {

                        /* simple argument variable */
                        public Value visit(A p, Value[] arg) {
                                return arg[p.integer_];
                        }
                        
                        /* arguments of higher-order type */
                        // AB.   ArgVar ::= Ident "+" Integer "@" Integer ; -- with a number of bindings
                        public Value visit(AB p, Value[] arg) {
                                return arg[p.integer_2];
                        }
                };

        /**
         *  Convert a pattern to a value.
         *  FIXME: not general enough, since a wildcard might 
         *  produce multiple values.
         */
        private static Patt.Visitor<Value,Object> patternValue 
        = new Patt.Visitor<Value,Object>() {
                public Value visit(PC p, Object arg) {
                        String s = cidentToString(p.cident_);
                        Value[] ps = new Value[p.listpatt_.size()];
                        int i = 0;
                        for (Patt pa : p.listpatt_)
                                ps[i++] = pa.accept(this, arg);
                        return new Param(s, ps);
                }
                public Value visit(PV p, Object arg) {
                        return new Param(p.ident_, new Param[0]);
                }
                
                /* wildcard */
                // FIXME: implement
                // should produce multiple params
                public Value visit(PW p, Object arg) {
                        return unused(this, p, arg);
                }

                /* Pattern matching on records */
                // PR.   Patt ::= "{" [PattAssign] "}" ;
                // PAss. PattAssign ::= Label "=" Patt ;
                public Value visit(PR p, Object arg) {
                        Record r = new Record();
                        for (PattAssign pass : p.listpattassign_) {
                                PAss pa = (PAss)pass; // FIXME: use visitor?
                                String l = pa.label_.accept(labelToString, null);
                                Value v = pa.patt_.accept(this, arg);
                                r.set(l, v);
                        }
                        return r;
                }

                // FIXME: implement
                // PI.   Patt ::= Integer ;
                public Value visit(PI p, Object arg) {
                        return unused(this, p, arg);
                }

                // FIXME: implement
                // PF.   Patt ::= Double ;
                public Value visit(PF p, Object arg) {
                        return unused(this, p, arg);
                }

        };

        /**
         *  Map a visitor over a list of terms.
         */
        private <X extends Term> List<Value> visitList(List<X> xs, Value[] arg) {
                List<Value> ys = new LinkedList<Value>();
                for (X x : xs)
                        ys.add(x.accept(this, arg));
                return ys;
        }

        /* record construction */
        public Value visit(R p, Value[] arg) {
                Record r = new Record();
                for (Assign a : p.listassign_) {
                        // FIXME: use Assign.Visitor
                        Ass ass = (Ass)a;
                        String l = ass.label_.accept(labelToString, null);
                        r.set(l, ass.term_.accept(this, arg));
                }
                return r;
        }

        /* concatenation */
        public Value visit(C p, Value[] arg) {
                Value v1 = p.term_1.accept(this, arg);
                Value v2 = p.term_2.accept(this, arg);
                return new Concat(v1,v2);
        }

        /* string constant */
        public Value visit(K p, Value[] arg) {
                return p.tokn_.accept(toknToValue, null);
        }
        
        /* record projection  */
        public Value visit(P p, Value[] arg) {
                Value v = p.term_.accept(this, arg);
                String l = p.label_.accept(labelToString, null);
                if (v instanceof Record) {
                        Record r = (Record)v;
                        return r.get(l);
                } else if (v instanceof Meta) {
                        return new Meta();
                } else {
                        throw new GFException(v.getClass().getName() + " v = '" + v 
                                              + "' is not a record in projection v." + l);
                }
        }

        /* argument variable */
        public Value visit(Arg a, Value[] arg) {
                return a.argvar_.accept(argVarValue, arg);
        }

        /* parameter constructor */
        public Value visit(Par par, Value[] arg) {
                Value[] as = visitList(par.listterm_, arg).toArray(new Value[par.listterm_.size()]);
                return new Param(cidentToString(par.cident_), as);
        }

        /* table selection */
        public Value visit(S s, Value[] arg) {
                Value v = s.term_1.accept(this, arg);
                if (v instanceof se.chalmers.cs.gf.linearize.gfvalue.Table) {
                        se.chalmers.cs.gf.linearize.gfvalue.Table t = 
                                (se.chalmers.cs.gf.linearize.gfvalue.Table)s.term_1.accept(this, arg);
                        Value p = s.term_2.accept(this, arg);
                        if (p instanceof Meta) {
                                return t.firstValue();
                        } else {
                                return t.select(p);
                        }
                } else if (v instanceof Meta) {
                        return new Meta();
                } else {
                        throw new GFException(v.getClass().getName() 
                                                   + " " + v 
                                                   + " is not a table");
                }
        }

        /* table construction */
        public Value visit(T t, Value[] arg) {
//                t.ctype_.accept(this, arg);
                List<Pair<Value,Value>> tm = new ArrayList<Pair<Value,Value>>();
                for (Case c : t.listcase_) {
                        Cas k = (Cas)c; // FIXME
                        Value v = k.term_.accept(this, arg);
                        for (Patt p : k.listpatt_) {
                                Value pa = p.accept(patternValue, null);
                                tm.add(Pair.pair(pa,v));
                        }
                }
                return new se.chalmers.cs.gf.linearize.gfvalue.Table(tm);
        }

        /* empty */
        public Value visit(E p, Value[] arg) {
                return new Str(""); // FIXME: { s = ... } ?
        }

        /** Free variation. We just pick the first one if there is one. */
        public Value visit(FV p, Value[] arg) {
                if (p.listterm_.size() == 0) {
                        //       throw new GFException("No variants.");
                        return new Str("<no variants>");
                }
                // FIXME: return all possibilities?
                return p.listterm_.get(0).accept(this, arg);
        }

        // FIXME: implement
        // LI.   Term2 ::= "$" Ident ;  -- from pattern variables
        public Value visit(LI p, Value[] arg) {
                return unused(this, p, arg);
        }

        // FIXME: implement
        // I.    Term2 ::= CIdent ; -- from resources
        public Value visit(I p, Value[] arg) {
		return module.findOper(p.cident_).getTerm().accept(this, new Value[0]);
        }

        // FIXME: implement
        // EInt. Term2 ::= Integer ;
        public Value visit(EInt p, Value[] arg) {
                return unused(this, p, arg);
        }

        // FIXME: implement
        // EFloat. Term2 ::= Double ;
        public Value visit(EFloat p, Value[] arg) {
                return unused(this, p, arg);
        }

        // FIXME: implement
        //V.    Term1 ::= "table" CType "[" [Term2] "]" ;
        public Value visit(V p, Value[] arg) {
                return unused(this, p, arg);
        }

        private static <R,A> R unused(Object v, Object p, A arg) {
                throw new GFException(v.getClass().getName() + " has no case for " + p.getClass().getName());
        }

}
