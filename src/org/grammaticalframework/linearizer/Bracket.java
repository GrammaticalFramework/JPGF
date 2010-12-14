package org.grammaticalframework.linearizer;

import java.util.Vector;

public class Bracket extends BracketedTokn{
	private String cId;
	private int lIndex;
	private int fId;
	private Vector<BracketedTokn> bss ;
	
	public Bracket(String _cId, int _lIndex, int _fId, Vector<BracketedTokn> _bss)
	{cId = _cId; lIndex = _lIndex; fId = _fId; bss = _bss;}
	
   public String getCId() {return cId;}
   public int getLIndex() {return lIndex;}
   public int getFId() {return fId;}
   public Vector<BracketedTokn> getBracketedToks() {return bss;}
   public String toString() {String rez = "name : "+cId + ", linIndex : "+lIndex+", fId : "+fId+", bracketed tokens : "+bss.toString();
                             //for(int i=0;i<bss.length;i++)
                             //	 rez+=(" "+bss[i].toString());
                             return rez;}
}
