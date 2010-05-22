package pgf.reader;

public class AlternToksSymbol extends Symbol {
        private  String[] toks;
        private  Alternative[] alts;
          
		  public AlternToksSymbol(String[] _toks, Alternative[] _alts) { toks = _toks; alts = _alts; }
          public String toString()
          {String s = "Tokens : "+toks + " Alternatives : "+alts;
          return s;	   
          };
	
          public String[] getToks(){return toks;}
          public Alternative[] getAlternatives(){return alts;}
		}


