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
    private String startCat;

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

    /**
     * returns the concretes categories (forest indices) corresponding to the
     * given abstract category.
     * @param absCat the name of the abstract category
     * @return the CncCat object or null if the category in unknown.
     **/
    public CncCat concreteCats(String absCat) {
        return this.cncCats.get(absCat);
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

    public HashMap<Integer,HashSet<Production>> getSetOfProductions() 
    {HashMap<Integer,HashSet<Production>> hm = new HashMap<Integer,HashSet<Production>>();
    for(int i=0; i<prods.length; i++)
    	hm.put(new Integer(prods[i].getId()), prods[i].getSetOfProductions());


    return hm;} 
}
