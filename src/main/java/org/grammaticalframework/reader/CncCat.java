package org.grammaticalframework.reader;

public class CncCat {
	private String name; 	
	private int firstFId;
	private int lastFId;	
	private String[] labels; 

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

public String getName() {return name;} 	
public int getFirstId() {return firstFId;}
public int getLastId() {return lastFId;}	
public String[] getLabels() {return labels;} 


}
