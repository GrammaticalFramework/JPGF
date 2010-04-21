package pgf.reader;

public class CncCat {
	String name; 	
	int firstFId;
	int lastFId;	
	String[] labels; 

public CncCat(String _name, int _firstFId, int _lastFId, String[] _labels)
{name = _name;
 firstFId = _firstFId;
 lastFId = _lastFId;
 labels = _labels;
}

public String toString()
{String ss = "Name : "+name+" , First id : "+firstFId+" , Last id : "+lastFId+"\n Labels :";
for(int i=0; i<labels.length; i++)
	ss+=(" "+labels[i]);
return ss;	
}
}
