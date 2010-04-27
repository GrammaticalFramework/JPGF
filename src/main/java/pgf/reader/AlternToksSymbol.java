package reader;

public class AlternToksSymbol extends Symbol {
          String[] toks;
          Alternative[] alts;
          
		  public AlternToksSymbol(String[] _toks, Alternative[] _alts) { toks = _toks; alts = _alts; }
          public String toString()
          {String s = "Tokens : "+toks + " Alternatives : "+alts;
          return s;	   
          };
	
		}


