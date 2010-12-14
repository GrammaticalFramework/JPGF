package org.grammaticalframework.linearizer;

public class CncType {
   private String cId;
   private int fId;
   
   public CncType(String _cId, int _fId)
   {cId = _cId; fId = _fId;}
   public String getCId() {return cId;}
   public int getFId() {return fId;}
   public String toString() {return "name : "+cId+" , fId : "+fId;}	
	
}
