/*
 * NonTerminalRule.java
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

import se.chalmers.cs.gf.abssyn.*;

import java.util.*;

/**
 *  A CF grammar prodcution whose right hand side is a list of non-terminals.
 */
public class NonTerminalRule extends Rule {

        private List<Category> rhs;

        private String fun;

        private List<Profile> profile;

        public NonTerminalRule(Category cat, List<Category> rhs, 
                               String fun, List<Profile> profile) {
                super(cat);
                this.rhs = rhs;
                this.fun = fun;
                this.profile = profile;
        }

        /**
         * Get the right hand side of the rule.
         * Do not modify the returned list.
         */
        public List<Category> getRhs() {
                return rhs;
        }

        public String getFun() {
                return fun;
        }

        public List<Profile> getProfile() {
                return profile;
        }

        /**
         *  Checks if the right hand side of rule starts with the given 
         *  non-terminal.
         */
        public boolean startsWith(Category c) {
                return rhs.size() > 0 && rhs.get(0).equals(c);
        }

        public int getSize() {
                return rhs.size();
        }

        /**
         *  Checks if the right hand side of this rule consists of a 
         *  single terminal.
         */
        public boolean isTerminal() {
                return false;
        }

        public Tree makeTree(Tree children[], int startIndex, int endIndex) {
                if (isCoersion()) {
                        return children[0];
                } else {
                        Tree[] childNodes = new Tree[profile.size()];
                        for (int i = 0; i < childNodes.length; i++) {
                                childNodes[i] = profile.get(i).buildChild(children);
                                if (childNodes[i] == null)
                                        return null;
                        }
                        return new Fun(fun, childNodes, startIndex, endIndex);
                }
        }

        private boolean isCoersion() {
                return fun.equals("_");
        }

        public boolean equals(Object o) {
                return o instanceof NonTerminalRule && equals((NonTerminalRule)o);
        }

        public boolean equals(NonTerminalRule r) {
                return getCategory().equals(r.getCategory())
                        && rhs.equals(r.rhs)
                        && fun.equals(r.fun)
                        && profile.equals(r.profile);
        }

        public int hashCode() {
                return getCategory().hashCode() 
                        + rhs.hashCode()
                        + fun.hashCode()
                        + profile.hashCode();
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(getFun());
                sb.append(profile);
                sb.append(". ");

                sb.append(getCategory());
                sb.append(" -> ");
                for (Category c : getRhs())
                        sb.append(c).append(" ");

                return sb.toString();
        }

}
