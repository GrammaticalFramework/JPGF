/*
 * MetaVariable.java
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

/**
 *  A metavariable in untyped abstract syntax trees.
 */
public class MetaVariable extends Tree {

        /**
	 * 
	 */
	private static final long serialVersionUID = 4614244214308431921L;
		private String type;

        public MetaVariable() { 
                this(null);
        }

        public MetaVariable(String type) {
                this.type = type;
        }

        public boolean isLiteral() {
                return false;
        }

        public String getType() {
                return type;
        }

        public String toString() {
                return "?" + (type == null ? "" : " : " + type);
        }

        public boolean equals(Object o) {
                return o instanceof MetaVariable && equals((MetaVariable)o);
        }

        public boolean equals(MetaVariable v) {
                return true;
        }

        public int hashCode() {
                return 42; // some random constant
        }

        public <R,A> R accept(TreeVisitor<R,A> v, A arg) {
                return v.visit(this, arg); 
        }

}
