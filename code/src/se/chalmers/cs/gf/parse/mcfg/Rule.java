/*
 * Rule.java
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
package se.chalmers.cs.gf.parse.mcfg;

import se.chalmers.cs.gf.parse.Token;
import se.chalmers.cs.gf.util.StringUtil;

import java.util.List;

public class Rule {

        private String fun;

        // FIXME: add profile

        private Category cat;

        private List<Category> childCats;

        private Record<List<Symbol>> rhs;

        public Rule(String fun, Category cat, List<Category> childCats,
                    Record<List<Symbol>> rhs) {
                this.fun = fun;
                this.cat = cat;
                this.childCats = childCats;
                this.rhs = rhs;
        }

        /**
         *  Get the function name used to construct syntax trees
         *  using this rule.
         */
        public String getFun() {
                return fun;
        }

        /**
         *  Get the left hand side category of the rule.
         */
        public Category getCategory() {
                return cat;
        }

        /**
         *  Get the child categories of the rule.
         *  Do not modify the returned list.
         */
        public List<Category> getChildCats() {
                return childCats;
        }

        public Record<List<Symbol>> getRhs() {
                return rhs;
        }

	/**
	 *  Get the first symbol of the first non-empty linearization row.
	 *  @return null if there is no such symbol, i.e. if all linearization
	 *  rows are empty.
	 */
	public Symbol getLeftCorner() {
		for (List<Symbol> a : rhs.values()) {
			if (!a.isEmpty())
				return a.get(0);
		}
		return null;
	}

        //
        // RHS symbols
        //

        /** An MCFG grammar symbol: a terminal or a record projection. */
        public static abstract class Symbol {
		public abstract boolean isTerminal();
	}

        /** An MCFG grammar terminal. */
	// FIXME: add suport for string and integer literals
        public static class Terminal extends Symbol {

                private String tok;

                public Terminal(String tok) {
                        this.tok = tok;
                }
                public String getValue() { return tok; }
		public boolean matches(Token t) {
			return t.isWord() 
				&& t.getValue().equals(tok); 
		}
		public boolean isTerminal() { return true; }
		public String toString() { return tok; }
        }

        /** An MCFG record projection (in the RHS of a rule). */
        public static class Projection extends Symbol {

		private Category cat;
                private int arg;
                private String label;

                public Projection(Category cat, int arg, String label) {
			this.cat = cat;
                        this.arg = arg;
                        this.label = label;
                }
		
		public Category getCategory() { return cat; }
		public int getArg() { return arg; }
		public String getLabel() { return label; }
		public boolean isTerminal() { return false; }
		public String toString() {
			return cat + "@" + arg + "." + label;
		}
        }

	public String toString() {
		return fun + "[" /* FIXME: profile */ + "]." 
			+ cat + " -> " + StringUtil.join(" ", childCats)
			+ " := {" + rhs + "}";
	}

}
