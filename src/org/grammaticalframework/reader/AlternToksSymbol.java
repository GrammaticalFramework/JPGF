/* AlternToksSymbol.java
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

public class AlternToksSymbol extends ToksSymbol {
    Alternative[] alts;

    public AlternToksSymbol(String[] toks, Alternative[] _alts) {
        super(toks);
        alts = _alts;
    }

    public boolean isTerminal() { return true; }

    public String toString()
    {
	StringBuffer sb = new StringBuffer("pre { ");
	for (String s : toks) {
	    sb.append(s);
	    sb.append(" "); }
	sb.append("; ");
	for (Alternative a : alts) {
	    sb.append(a);
	    sb.append("; "); }
	sb.append("}");
        return sb.toString();
    };

    public String[] getToks(){return toks;}
    public Alternative[] getAlternatives(){return alts;}

}


