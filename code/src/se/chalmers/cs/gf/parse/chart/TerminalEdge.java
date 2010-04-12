/*
 * TerminalEdge.java
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

import java.util.*;

import se.chalmers.cs.gf.parse.Token;

public class TerminalEdge extends Edge {

        private TerminalRule rule;

        private Token token;

        public TerminalEdge(TerminalRule rule, Token token) {
                this.rule = rule;
                this.token = token;
        }

        /**
         *  Get the rule that this edge is for.
         */
        public TerminalRule getRule() {
                return rule;
        }

        /**
         *  Get the input token that this terminal edge covers.
         */
        public Token getToken() {
                return token;
        }

        /**
         *  Checks if the next symbol needed is the given category.
         */
        public boolean needs(Category c) {
                return false;
        }

        /**
         *  Checks if this edge is active, that is, if it is still missing 
         *  some symbol.
         */
        public boolean isActive() {
                return false;
        }
        
        public boolean equals(Object o) {
                return o instanceof TerminalEdge && equals((TerminalEdge)o);
        }

        public boolean equals(TerminalEdge e) {
                return rule.equals(e.rule) && token.equals(token);
        }

        public int hashCode() {
                return rule.hashCode() + token.hashCode();
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(rule);
                sb.append(" '").append(token).append("'");
                return sb.toString();
        }

}
