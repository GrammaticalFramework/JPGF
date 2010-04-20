package reader;

public class ToksSymbol extends Symbol {
          String[] toks ;
          
		  public ToksSymbol(String[] _toks) { toks = _toks; }
          public String toString()
          {String s = "Tokens : ";
           for(int i=0; i<toks.length; i++)
        	   s+=(" "+toks[i]);  
          return s;	   
          };
		  public <R,A> R accept(reader.Symbol.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

		  public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o instanceof reader.ToksSymbol) {
		      return true;
		    }
		    return false;
		  }

		}


