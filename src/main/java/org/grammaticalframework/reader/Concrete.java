package org.grammaticalframework.reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.HashSet;

public class Concrete {
    private String name ;
    private Map<String, RLiteral> flags ;
    private PrintName[] printnames ;
    private Sequence[] seqs ;
    private CncFun[] cncFuns ;
    private ProductionSet[] prods ;
    private CncCat[] cncCats ;
    private int fId ;

    public Concrete (String name,
                     Map<String,RLiteral> flags,
                     PrintName[] _printnames,
                     Sequence[] _seqs,
                     CncFun[] _cncFuns,
                     ProductionSet[] _prods,
                     CncCat[] _cncCats,
                     int _fId)
    {
        this.name = name;
        this.flags = flags;
        printnames = _printnames;
        seqs = _seqs;
        cncFuns = _cncFuns;
        prods = _prods;
        cncCats = _cncCats;
        fId = _fId;
    }

    public String getName() {return name;}
    // FIXME : needed ???
    //public Flag[] getFlags() {return flags;}
    public PrintName[] getPrintNames() {return printnames;}
    public Sequence[] getSequences() {return seqs;}
    public CncFun[] getCncFuns() {return cncFuns;}
    public ProductionSet[] getProductionSet() {return prods;}
    public CncCat[] getCncCat() {return cncCats;}
    public int getFId() {return fId;}

    /**
     * Accessors
     */
    public String name() { return this.name; }

    public int startCat() {
        RLiteral cat = this.flags.get("startcat");
        if (cat == null)
            return 0;
        else
            return ((IntLit)cat).value();
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
        for(int i=0; i<cncCats.length; i++)
            ss+=(" "+cncCats[i].toString());
        ss+="]\n , forest ID : "+fId;
        return ss;
    }
    
    public HashMap<Integer,HashSet<Production>> getSetOfProductions() 
    {HashMap<Integer,HashSet<Production>> hm = new HashMap<Integer,HashSet<Production>>();
    for(int i=0; i<prods.length; i++)
    	hm.put(new Integer(prods[i].getId()), prods[i].getSetOfProductions());


    return hm;} 
}
