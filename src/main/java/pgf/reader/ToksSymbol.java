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

		}


