/* Alternative.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework.reader;

// An alternative is like a token (TokSymbol) but restricted to the case
// where the folowing words match "prefix"
// Todo: subclass TokSymbol ???

public class Alternative {
    private String[] tokens;
    private String[] prefix;

    public Alternative(String[] _alt1, String[] _alt2) {
        tokens = _alt1;
        prefix = _alt2;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	for (String t : tokens) {
	    sb.append(t);
	    sb.append(" "); }
	sb.append("/ ");
	for (String t : prefix) {
	    sb.append(t);
	    sb.append(" "); }
        return sb.toString();
    }

    public String[] getAlt1() {
        return tokens;
    }

    public String[] getAlt2() {
        return prefix;
    }
}
