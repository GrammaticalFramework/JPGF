/*
 * Terminal.java
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

import se.chalmers.cs.gf.parse.Token;

/**
 *  A CF grammar terminal.
 */
public class Terminal extends Symbol {

        private String tok;

        public Terminal(String tok) {
                this.tok = tok;
        }

        public String getToken() {
                return tok;
        }

        public boolean isTerminal() {
                return true;
        }

        public boolean equals(Object o) {
                return o instanceof Terminal && equals((Terminal)o);
        }

        public boolean equals(Terminal t) {
                return tok.equals(t.tok);
        }

        public int hashCode() {
                return tok.hashCode();
        }

        public String toString() {
                return '"' + tok + '"';
        }

}
