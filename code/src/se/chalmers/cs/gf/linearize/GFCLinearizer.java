/*
 * GFCLinearizer.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.linearize.gfvalue.*;
import se.chalmers.cs.gf.parse.Token;
import se.chalmers.cs.gf.GFC.*;
//import se.chalmers.cs.gf.GFC.Absyn.*;
import static se.chalmers.cs.gf.linearize.LinearizeLogger.*;

import java.util.*;
import java.io.Serializable;

/**
 *  A linearizer which interprets a concrete GFC module.
 */
public class GFCLinearizer implements Serializable,Linearizer {

        private GFCModule module;

        /**
         *  @param module A GFC module
         */
	public GFCLinearizer(GFCModule module) {
                this.module = module;
        }

        public List<Token> linearize(Tree t) {
                log.fine("Linearizing with " + getName());
                Value v = t.accept(new TreeLinearizer(), null);
                log.finer("Done: " + v);
                return valueToTokens(v);
        }

	public List<Token> linearizeWithRanges(Tree t) {
		log.fine("Linearizing with " + getName());
		Value v = t.accept(new TreeLinearizerWithRanges(), null);
		log.finer("Done: " + v);
                return valueToTokens(v);
	}

        public String getName() {
                return module.getName();
        }

        public boolean tryThisOne() {
                return true;
        }
	
	public List<Token> valueToTokens(Value v) {
		return v.accept(toTokens, new LinkedList<Token>());
	}

	private ValueVisitor<List<Token>,List<Token>> toTokens
		= new ValueVisitor<List<Token>,List<Token>>() {

		public List<Token> visit(Concat v, List<Token> rest) {
			return v.getFirst().accept(this,
						   v.getSecond().accept(this,rest));
		}

		public List<Token> visit(Meta v, List<Token> rest){
			return cons(v.toString(), rest);
		}

		public List<Token> visit(Param v, List<Token> rest){
			return cons(v.toString(), rest);
		}

		public List<Token> visit(Pre v, List<Token> rest){
			String head = rest.isEmpty() ? "" : rest.get(0).getValue();
			return cons(head, rest);
		}

		public List<Token> visit(Record v, List<Token> rest){
			Value f = v.get("s");
			if (f == null) {
				return cons(v.toString(), rest);
			} else {
				return f.accept(this, rest);
			}
		}

		public List<Token> visit(Str v, List<Token> rest){
			return cons(v.toString(), rest);
		}

		public List<Token> visit(Table v, List<Token> rest){
			return cons(v.toString(), rest);
		}

		private List<Token> cons(String s, List<Token> rest) {
			rest.add(0, new Token(s));
			return rest;
		}
	};



        /**
         *  Linearizes an abstract syntax tree by recursively applying 
         *  the linearization rules for each function.
         */
        private class TreeLinearizer 
                implements TreeVisitor<Value,Object> {

                public Value visit(Fun t, Object arg) {
                        Tree[] c = t.getChildren();
                        Value[] args = new Value[c.length];
                        for (int i = 0; i < c.length; i++)
                                args[i] = c[i].accept(this, arg);
                        String label = t.getLabel();
                        LinRule rule = module.findLinRule(label);
                        // FIXME: hacky, needed to work around missing
                        // linearizations for type arguments
                        if (rule == null)
                                return Record.ss("<missing lin:" + label + ">");

                        if (rule.getArity() != args.length)
                                throw new GFException(label + " requires " + rule.getArity() 
						      + " arguments, got " + args.length);
                        return rule.getTerm().accept(new InterpretLinRule(module), args);
                }

                public Value visit(IntLiteral l, Object arg) {
                        return Record.ss(String.valueOf(l.getValue()));
                }

                public Value visit(StringLiteral l, Object arg) {
                        return Record.ss(l.getValue());
                }

                public Value visit(MetaVariable v, Object arg) {
                        // FIXME: use lindef
                        return new Meta();
                        //return Record.ss("? : " + v.getType());
                }

        }

        private class TreeLinearizerWithRanges 
	        extends TreeLinearizer {

                public Value visit(Fun t, Object arg) {
			return addRanges(t, super.visit(t, arg));
                }

                public Value visit(IntLiteral t, Object arg) {
                        return addRanges(t, super.visit(t, arg));
                }

                public Value visit(StringLiteral t, Object arg) {
                        return addRanges(t, super.visit(t, arg));
                }

                public Value visit(MetaVariable t, Object arg) {
			return addRanges(t, super.visit(t, arg));
                }

		private Value addRanges(Tree t, Value v) {
			return WrapStr.wrap(v, "(", "= " + t.inputRangesToString() + ")");
		}

        }

}
