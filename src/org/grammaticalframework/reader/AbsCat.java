/* AbsCat.java
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

public class AbsCat {
    public final String name;
    private final Hypo[] hypos;
    private final WeightedIdent[] functions;
    
    public AbsCat(String name, Hypo[] _hypos, WeightedIdent[] functions)
    {
        this.name = name;
        this.hypos = _hypos;
        this.functions = functions;
    }

    public String name() {
        return name;
    }

    public Hypo[] getHypos() {
	return hypos;
    }
    
    public WeightedIdent[] getFunctions() {
	return functions;
    }

    public String toString() {
        String ss = "Name : "+ name + " , Hypotheses : (";
        for(int i=0; i<hypos.length; i++)
            ss+=(" "+hypos[i].toString());
        ss+=") , String Names : (";
        for(int i=0; i<functions.length; i++)
            ss+=(" "+functions[i].toString());
        ss+=")";
        return ss;
    }

}

