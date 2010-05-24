package org.grammaticalframework.reader;

public class CoerceProduction extends Production {
    private  int initId;
    
    public CoerceProduction(int fId, int _initId) {
	super(1, fId);
	initId = _initId;
    }
		  
    public int getInitId() {return initId;}
    
    public int[] domain() {
        return new int[] {this.initId};
    }

    public String toString(){
        return "Coercion(" + this.fId + " -> " +initId + ")";
    }

    public boolean equals(Object o)
        {if(o instanceof CoerceProduction)
        	return ((CoerceProduction) o).initId == initId;
        return false;}     
}


