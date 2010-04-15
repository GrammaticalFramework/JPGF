/*
 * TerminalRule.java
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
import se.chalmers.cs.gf.parse.Token;

import java.util.*;

public class TerminalRule extends Rule {

        private Terminal terminal;

        public TerminalRule(Category cat, Terminal terminal) {
                super(cat);
                this.terminal = terminal;
        }

        public String getFun() {
                return getCategory().getName();
        }

        public Terminal getTerminal() {
                return terminal;
        }

        public int getSize() {
                return 1;
        }

        public boolean isTerminal() {
                return true;
        }

        /**
         *  Build an abstract syntax tree from this rule. Since this is a
         *  terminal rule, we don't need to know about the children.
         */
        public Tree makeTree(Token token, int startIndex, int endIndex) {
                return new Fun(getFun(), startIndex, endIndex);
        }

        public boolean equals(Object o) {
                return o instanceof TerminalRule && equals((TerminalRule)o);
        }

        public boolean equals(TerminalRule r) {
                return getCategory().equals(r.getCategory())
                        && terminal.equals(r.terminal);
        }

        public int hashCode() {
                return getCategory().hashCode()
                        + terminal.hashCode();
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(getFun());
                sb.append(". ");
                sb.append(getCategory());
                sb.append(" -> ");
                sb.append(terminal);

                return sb.toString();
        }

}
