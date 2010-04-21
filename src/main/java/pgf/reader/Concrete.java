package pgf.reader;

public class Concrete {
    String str ;// What is str ???
    Flag[] 	flags ; 
    PrintName[] printnames ;	
    Sequence[] seqs ;	
    CncFun[] cncFuns ; 	
    ProductionSet[] prods ; 	
    CncCat[] cncCats ;
    int fId ;
    // what is that fId ?
    // is it "total number of forest ids allocated for the grammar " ?
    // (in that case, 'fId' is a bad name...)
    
    public Concrete (String _str, Flag[] _flags, PrintName[] _printnames, 
		     Sequence[] _seqs, CncFun[] _cncFuns, 
		     ProductionSet[] _prods, CncCat[] _cncCats, int _fId) {
	str = _str;
	flags = _flags;
	printnames = _printnames;
	seqs = _seqs;
	cncFuns = _cncFuns;
	prods = _prods; 
	cncCats = _cncCats;
	fId = _fId;	
    }
	
public String toString()
{String ss = " Name : "+str + " , Flags : [";
 for(int i=0;i<flags.length; i++)
	 ss+=(" "+flags[i].toString());
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
}
