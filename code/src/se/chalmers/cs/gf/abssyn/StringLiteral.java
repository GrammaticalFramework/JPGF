/*
 * StringLiteral.java
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
package se.chalmers.cs.gf.abssyn;

import se.chalmers.cs.gf.util.Pair;

import java.util.Set;

/**
 *  A string literal in untyped abstract syntax trees.
 */
public class StringLiteral extends Tree {

        private String value;

        public StringLiteral(String value) {
                this.value = value;
        }

        public StringLiteral(String value, int startIndex, int endIndex) {
		super(startIndex, endIndex);
                this.value = value;
        }

	public StringLiteral(StringLiteral l, Set<Pair<Integer,Integer>> inputRange) {
		super(inputRange);
		this.value = l.value;
	}

        public boolean isLiteral() {
                return true;
        }

        public String getValue() {
                return value;
        }

        public String toString() {
                // FIXME: escape
                return '"' + value + '"';
        }

        public boolean equals(Object o) {
                return o instanceof StringLiteral && equals((StringLiteral)o);
        }

        public boolean equals(StringLiteral l) {
                return value.equals(l.value);
        }

        public int hashCode() {
                return value.hashCode();
        }

        public <R,A> R accept(TreeVisitor<R,A> v, A arg) {
                return v.visit(this, arg); 
        }

}
