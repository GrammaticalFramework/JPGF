package reader;

public class ProductionSet {
private int id;
private Production[] prods;

public ProductionSet(int _id, Production[] _prods)
{id = _id;
prods = _prods;
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

}
