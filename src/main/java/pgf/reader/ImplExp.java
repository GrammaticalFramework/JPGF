package pgf.reader;

public class ImplExp extends Expr{
	 Expr arg;
	 
 public ImplExp(Expr _arg) 
  {arg = _arg;}

 public String toString()
 {return "Implicit Arguments Expression : [ Argument : "+arg.toString()+"]"; }

}
