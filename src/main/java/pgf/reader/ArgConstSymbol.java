package pgf.reader;

public class ArgConstSymbol extends Symbol {
        private  int arg;
        private  int cons;
          
		  public ArgConstSymbol(int _arg, int _cons) { arg = _arg; cons = _cons; }
          
		  public String toString()
          {String s = "Argument : "+arg + " Constituent : "+cons;
          return s;	   
          };
         
          public int getArg() {return arg;}
          public int getCons() {return cons;}
		}


