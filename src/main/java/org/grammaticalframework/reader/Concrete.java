package org.grammaticalframework.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Concrete {
    private String name;
    private Map<String, RLiteral> flags ;
    private PrintName[] printnames ;
    private Sequence[] seqs ;
    private CncFun[] cncFuns ;
    private ProductionSet[] prods ;
    private Map<String, CncCat> cncCats ;
    private int fId ;
    private String startCat;

    public Concrete (String name,
                     Map<String,RLiteral> flags,
                     PrintName[] _printnames,
                     Sequence[] _seqs,
                     CncFun[] _cncFuns,
                     ProductionSet[] _prods,
                     Map<String, CncCat> cncCats,
                     int _fId,
                     String defaultStartCat)
    {
        this.name = name;
        this.flags = flags;
        printnames = _printnames;
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

    public PrintName[] getPrintNames() {return printnames;}
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

    public String toString()
    {
        String ss = " Name : "+ name + " , Flags : [";
        // for(int i=0;i<flags.length; i++)
        //     ss+=(" "+flags[i].toString());
        ss+="]\n , Print Names : [";
        for(int i=0; i<printnames.length; i++)
            ss+=("\n      "+printnames[i].toString());
        ss+="]\n , Sequences : [";
        for(int i=0; i<seqs.length; i++)
            ss+=("\n      "+seqs[i].toString());
        ss+="]\n , Concrete Functions : [";
        for(int i=0; i<cncFuns.length; i++)
            ss+=("\n   "+cncFuns[i].toString());
        ss+="]\n , Productions : [";
        for(int i=0; i<prods.length; i++)
            ss+=("\n   "+prods[i].toString());
        ss+="]\n , Concrete Categories : [";
        // for(int i=0; i<cncCats.length; i++)
        //     ss+=(" "+cncCats[i].toString());
        ss+="]\n , forest ID : "+fId;
        return ss;
    }

    public HashMap<Integer,HashSet<Production>> getSetOfProductions() 
    {HashMap<Integer,HashSet<Production>> hm = new HashMap<Integer,HashSet<Production>>();
    for(int i=0; i<prods.length; i++)
    	hm.put(new Integer(prods[i].getId()), prods[i].getSetOfProductions());


    return hm;} 
}
