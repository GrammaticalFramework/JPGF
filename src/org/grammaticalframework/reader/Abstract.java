/* Abstract.java
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

import java.util.*;
import com.google.common.collect.*;

public class Abstract {
    private final String name;
    private final Map<String,RLiteral> flags;
    private final AbsFun[] absFuns;
    private final AbsCat[] absCats;
    private final ImmutableMap<String,AbsCat> categories;
    private final ImmutableMap<String,AbsFun> functions;

    public Abstract(String name,
                    Map<String, RLiteral> _flags,
                    AbsFun[] _absFuns,
                    AbsCat[] _absCats)
    {
        this.name = name;
        flags = _flags;
        absFuns = _absFuns;
        absCats = _absCats;
	ImmutableMap.Builder<String, AbsCat> catBuilder = 
	    new ImmutableMap.Builder<String, AbsCat>();
	for (AbsCat c: absCats)
	    catBuilder.put(c.name, c);
	categories = catBuilder.build();

	ImmutableMap.Builder<String, AbsFun> funBuilder = 
	    new ImmutableMap.Builder<String, AbsFun>();
	for (AbsFun f: absFuns)
	    funBuilder.put(f.name, f);
	functions = funBuilder.build();
    }

    public String name() {
        return name;
    }

    public String startcat() {
	RLiteral cat = this.flags.get("startcat");
        if (cat == null)
            return "Sentence";
        else
            return ((StringLit)cat).getValue();
    }

    /**
     * Returns all the functions whose return type
     * is returnType. 
     **/
    public Collection<AbsFun> functions(String returnType) {
	Vector<AbsFun> fs = new Vector();
	for (WeightedIdent wi: this.categories.get(returnType).getFunctions()) {
	    fs.add(functions.get(wi.ident()));
	}
	return fs;
    }


    public AbsFun[] getAbsFuns() {return absFuns;}
    public AbsCat[] getAbsCats() {return absCats;}

    public String toString() {
	String ss = "Name : "+ name + " , Flags : (";
        // for(int i=0; i<flags.length;i++)
        // 	ss+=(" "+flags[i].toString());
        ss+=") , Abstract Functions : (";
        for(int i=0;i<absFuns.length;i++)
            ss+=(" "+absFuns[i].toString());
        ss+=") , Abstract Categories : (";
        for(int i=0;i<absCats.length;i++)
            ss+=(" "+absCats[i].toString());
        ss+=")";
        return ss;
    }
}
