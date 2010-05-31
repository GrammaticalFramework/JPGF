package org.grammaticalframework.reader;

/**
 * Concrete category are a maping from category names (abstract-categories)
 * to multiple, conjoint, concrete categories.
 * They are represented in the pgf binary by :
 *  - the name of the abstract category (ex: Adj)
 *  - the first concrete categoy (ex : C18)
 *  - the last corresponding concrete category (ex : C21)
 *  - a list of labels (names of fields in the pmcfg tuples)
 * Here we will keep only the indices.
 */
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
