package org.grammaticalframework.linearizer;

import org.grammaticalframework.reader.Alternative;


public class LeafKP extends BracketedTokn{
	 private String[] strs;
	 private Alternative[] alts ;
	 
	 public LeafKP(String[] _strs, Alternative[] _alts)
	 {strs = _strs; alts = _alts;}
	 
	 public String[] getStrs() {return strs;}
	 public Alternative[] getAlts() {return alts;}
	 public String toString()
	 {String rez = "string names : [";
	  for(int i=0;i<strs.length;i++)
		  rez+=(" "+strs[i]);
	  rez+="] , Alternatives : [";
	  for(int i=0; i<alts.length;i++)
		  rez+=(" "+alts[i].toString());
	  rez+="]";
	  return rez;
		 
	 }

}
