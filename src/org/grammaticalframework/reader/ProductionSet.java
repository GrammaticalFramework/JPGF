/* ProductionSet.java
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
import java.util.HashSet;

public class ProductionSet {
    private int id;
    private Production[] prods;

    public ProductionSet(int _id, Production[] _prods) {
        id = _id;
        prods = _prods;
    }

    public int length() {
        return this.prods.length;
    }
    
    public Production[] productions() {
        return this.prods;
    }

    public String toString() {
        String ss = "Id : "+id+" , Productions : [";
        for(int i=0; i<prods.length; i++)
            ss+=(" "+prods[i].toString());
        ss+="]";
        return ss;
    }

    public int getId() {return id; }
    public Production[] getProductions() {return prods;}

    public HashSet<Production> getSetOfProductions() {
        HashSet<Production> hs = new HashSet<Production>();
        for(int i=0; i<prods.length; i++)
            hs.add(prods[i]);
        return hs;
    }
}
