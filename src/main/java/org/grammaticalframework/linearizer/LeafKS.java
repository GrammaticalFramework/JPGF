package org.grammaticalframework.linearizer;

public class LeafKS extends BracketedTokn{
	private String[] strs;
	
	public LeafKS(String[] _strs)
	{strs = _strs;}
	
	public String[] getStrs() {return strs;}
    public String toString()
    {String rez = "string names : [";
     for(int i=0; i<strs.length; i++)
    	 rez+=(" "+strs[i]);
     rez+="]";
     return rez;}
}
