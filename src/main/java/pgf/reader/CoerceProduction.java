package pgf.reader;

public class CoerceProduction extends Production {
          int initId;
    
          
		  public CoerceProduction(int _initId) { initId = _initId; }
		  
          public String toString()
          {return "Initial id : "+initId; };
		  
          public <R,A> R accept(reader.Production.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

		  public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o instanceof reader.CoerceProduction) {
		      return true;
		    }
		    return false;
		  }

		}


