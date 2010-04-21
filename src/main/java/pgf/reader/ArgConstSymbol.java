package pgf.reader;

public class ArgConstSymbol extends Symbol {
          int arg;
          int cons;
          
		  public ArgConstSymbol(int _arg, int _cons) { arg = _arg; cons = _cons; }
          public String toString()
          {String s = "Argument : "+arg + " Constituent : "+cons;
          return s;	   
          };
		  public <R,A> R accept(reader.Symbol.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

		  public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o instanceof reader.ArgConstSymbol) {
		      return true;
		    }
		    return false;
		  }

		}


