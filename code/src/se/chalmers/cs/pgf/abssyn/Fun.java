/*
 * Fun.java
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
package se.chalmers.cs.pgf.abssyn;

import se.chalmers.cs.pgf.util.Pair;
import java.util.Set;

/**
 *  A node in abstract syntax.
 */
public class Fun extends Tree {

        /**
	 * 
	 */
	private static final long serialVersionUID = -2670618417133564236L;

		private String label;

        private Tree[] children;

        public Fun(String label) {
                this(label, new Tree[0]);
        }

        public Fun(String label, Tree[] children) {
                this.label = label;
                this.children = children;
        }

        public Fun(String label, int startIndex, int endIndex) {
		this(label, new Tree[0], startIndex, endIndex);
        }

        public Fun(String label, Tree[] children, int startIndex, int endIndex) {
		super(startIndex, endIndex);
                this.label = label;
                this.children = children;
        }

        public Fun(String label, Tree[] children, Set<Pair<Integer,Integer>> inputRanges) {
		super(inputRanges);
                this.label = label;
                this.children = children;
        }

        public boolean isLiteral() {
                return false;
        }

        public String getLabel() {
                return label;
        }

        public int countChildren() {
                return children.length;
        }

        public Tree getChild(int i) {
                return children[i];
        }

        public Tree[] getChildren() {
                return children;
        }

        public boolean equals(Object o) {
                return o instanceof Fun && equals((Fun)o);
        }

        public boolean equals(Fun f) {
                if (!label.equals(f.label))
                        return false;
                if (children.length != f.children.length)
                        return false;
                for (int i = 0; i < children.length; i++)
                        if (!children[i].equals(f.children[i]))
                                return false;
                return true;
        }

        public int hashCode() {
                int c = label.hashCode();
                for (int i = 0; i < children.length; i++)
                        // FIXME: is this a good formula?
                        c += children[i].hashCode() * (i+1); 
                return c;
        }

        public String toString() {
                StringBuilder sb = new StringBuilder(label);
                for (Tree c : children) {
                        sb.append(' ');
                        if (c.isLiteral() || (c instanceof Fun && ((Fun)c).getChildren().length == 0)) {
                                sb.append(c.toString());
                        } else {
                                sb.append('(').append(c.toString()).append(')');
                        }
                }
                return sb.toString();
        }

	public String toStringWithRanges() {
                StringBuilder sb = new StringBuilder(label);
                for (Tree c : children)
                        sb.append(' ').append('(').append(c.toStringWithRanges()).append(')');
		sb.append(" = ").append(inputRangesToString());
                return sb.toString();
	}

        public <R,A> R accept(TreeVisitor<R,A> v, A arg) {
                return v.visit(this, arg); 
        }

}
