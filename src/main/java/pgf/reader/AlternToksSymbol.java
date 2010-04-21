package pgf.reader;

public class AlternToksSymbol extends Symbol {
          String[] toks;
          Alternative[] alts;
          
		  public AlternToksSymbol(String[] _toks, Alternative[] _alts) { toks = _toks; alts = _alts; }
          public String toString()
          {String s = "Tokens : "+toks + " Alternatives : "+alts;
          return s;	   
          };
		  public <R,A> R accept(reader.Symbol.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

		  public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o instanceof reader.AlternToksSymbol) {
		      return true;
		    }
		    return false;
		  }

		}


