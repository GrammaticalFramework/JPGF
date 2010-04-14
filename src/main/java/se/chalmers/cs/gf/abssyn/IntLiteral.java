/*
 * IntLiteral.java
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
 *  An integer literal in untyped abstract syntax trees.
 */
public class IntLiteral extends Tree {

        private int value;

        public IntLiteral(int value) {
                this.value = value;
        }

        public IntLiteral(int value, int startIndex, int endIndex) {
		super(startIndex, endIndex);
                this.value = value;
        }

	public IntLiteral(IntLiteral l, Set<Pair<Integer,Integer>> inputRange) {
		super(inputRange);
		this.value = l.value;
	}

        public boolean isLiteral() {
                return true;
        }

        public int getValue() {
                return value;
        }

        public String toString() {
                return String.valueOf(value);
        }

        public boolean equals(Object o) {
                return o instanceof IntLiteral && equals((IntLiteral)o);
        }

        public boolean equals(IntLiteral l) {
                return value == l.value;
        }

        public int hashCode() {
                return value;
        }

        public <R,A> R accept(TreeVisitor<R,A> v, A arg) {
                return v.visit(this, arg); 
        }

}
