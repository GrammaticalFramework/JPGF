package pgf.reader;

public class ToksSymbol extends Symbol {
        private String[] toks ;
          
		  public ToksSymbol(String[] _toks) { toks = _toks; }
          
		  public String toString()
          {String s = "Tokens : ";
           for(int i=0; i<toks.length; i++)
        	   s+=(" "+toks[i]);  
          return s;	   
          };

          public String[] getToks(){return toks;}
		}


