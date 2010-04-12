/*
 * NonTerminalEdge.java
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
package se.chalmers.cs.gf.parse.chart;

/**
 *  A parse chart edge produced by a non-terminal rule.
 */
public class NonTerminalEdge extends Edge {

        private NonTerminalRule rule;

        /** Position of the dot in the rule. */
        private int pos;

        private NonTerminalEdge(NonTerminalRule rule, int pos) {
                this.rule = rule;
                this.pos = pos;
        }

        public NonTerminalEdge(NonTerminalRule rule) {
                this(rule, 0);
        }

        public NonTerminalEdge(NonTerminalEdge e) {
                this(e.rule, e.pos);
        }

        /**
         *  Get the rule that this edge is for.
         */
        public NonTerminalRule getRule() {
                return rule;
        }

        /**
         *  Checks if the next symbol needed is the given category.
         */
        public boolean needs(Category c) {
                return isActive() && ((NonTerminalRule)rule).getRhs().get(pos).equals(c);
        }

        /**
         *  Checks if this edge is active, that is, if it is still missing 
         *  some symbol.
         */
        public boolean isActive() {
                return pos < rule.getSize();
        }

        /**
         *  Move the dot one step forward.
         */
        public void stepForward() {
                if (!isActive())
                        throw new IllegalStateException(
                                "Can't go forward in a passive rule.");
                pos++;
        }
        
        public boolean equals(Object o) {
                return o instanceof NonTerminalEdge && equals((NonTerminalEdge)o);
        }

        public boolean equals(NonTerminalEdge e) {
                return rule.equals(e.rule) && pos == e.pos;
        }

        public int hashCode() {
                return rule.hashCode() + pos;
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(rule);
                sb.append(" (").append(pos).append(")");
                return sb.toString();
        }

}
