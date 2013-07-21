/* Concrete.java
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Concrete {
    private String name;
    private Map<String, RLiteral> flags ;
    private Sequence[] seqs;
    private CncFun[] cncFuns;
    private ProductionSet[] prods;
    private Map<String, CncCat> cncCats ;
    private int fId ;
    public final String startCat;

    public Concrete (String name,
                     Map<String,RLiteral> flags,
                     Sequence[] _seqs,
                     CncFun[] _cncFuns,
                     ProductionSet[] _prods,
                     Map<String, CncCat> cncCats,
                     int _fId,
                     String defaultStartCat)
    {
        this.name = name;
        this.flags = flags;
        seqs = _seqs;
        cncFuns = _cncFuns;
        prods = _prods;
        this.cncCats = cncCats;
        fId = _fId;
        this.startCat = defaultStartCat;
    }

    /**
     * Accessors
     */
    public String name() {
        return name;
    }

    public class UnknownCategory extends Exception {
	private final String cat;
	
	public UnknownCategory(String c) {
	    this.cat = c;
	}

	public String toString() {
	    return "Unknown category " + cat;
	}

    }

    /**
     * returns the concretes categories (forest indices) corresponding to the
     * given abstract category.
     * @param absCat the name of the abstract category
     * @return the CncCat object or null if the category in unknown.
     **/
    public CncCat concreteCats(String absCat) throws UnknownCategory {
        CncCat c = this.cncCats.get(absCat);
	if (c != null)
	    return c;
	else
	    throw new UnknownCategory(absCat);
    }

    public Sequence[] getSequences() {return seqs;}
    public CncFun[] getCncFuns() {return cncFuns;}
    public ProductionSet[] getProductionSet() {return prods;}
    public CncCat[] getCncCat() {
        CncCat[] array = new CncCat[this.cncCats.size()];
        int i = 0;
        for ( CncCat c : this.cncCats.values()) {
            array[i] = c;
            i++;
        }
        return array;
    }
    public int getFId() {return fId;}

    public CncCat startCat() {
        CncCat cat = this.cncCats.get(this.startCat);
        if (cat == null)
            return new CncCat(this.startCat,0,0,null);
        else
            return cat;
    }

    public Production[] productions() {
        int size = 0;
        for (ProductionSet ps : this.prods) {
            size += ps.length();
        }
        Production [] prods = new Production[size];
        int i = 0;
        for (ProductionSet ps : this.prods)
            for (Production p: ps.productions()) {
                prods[i] = p;
                i++;
            }
        return prods;
    }

    public String toString() {
        return "concrete"  + this.name;
    }

    public HashMap<Integer,HashSet<Production>> getSetOfProductions() {
        HashMap<Integer,HashSet<Production>> hm =
            new HashMap<Integer,HashSet<Production>>();
        for(int i=0; i<prods.length; i++)
            hm.put(new Integer(prods[i].getId()),
                   prods[i].getSetOfProductions());
        return hm;
    }
}
