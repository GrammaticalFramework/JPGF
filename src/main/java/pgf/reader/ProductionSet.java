package pgf.reader;
import java.util.HashSet;
public class ProductionSet {
private int id;
private Production[] prods;

public ProductionSet(int _id, Production[] _prods)
{id = _id;
prods = _prods;
}

    public int length() {
        return this.prods.length;
    }
    
    public Production[] productions() {
        return this.prods;
    }

public String toString()
{String ss = "Id : "+id+" , Productions : [";
for(int i=0; i<prods.length; i++)
ss+=(" "+prods[i].toString());
ss+="]";
return ss;
}

public int getId() {return id; }
public Production[] getProductions() {return prods;}

public HashSet<Production> getSetOfProductions() 
{HashSet<Production> hs = new HashSet<Production>();
for(int i=0; i<prods.length; i++)
	hs.add(prods[i]);
return hs;
}
}
