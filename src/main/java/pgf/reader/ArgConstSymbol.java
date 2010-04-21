package reader;

public class ArgConstSymbol extends Symbol {
          int arg;
          int cons;
          
		  public ArgConstSymbol(int _arg, int _cons) { arg = _arg; cons = _cons; }
          public String toString()
          {String s = "Argument : "+arg + " Constituent : "+cons;
          return s;	   
          };

		}


