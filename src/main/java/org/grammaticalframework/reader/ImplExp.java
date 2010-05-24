package org.grammaticalframework.reader;

public class ImplExp extends Expr{
	 private Expr arg;
	 
 public ImplExp(Expr _arg) 
  {arg = _arg;}

 public String toString()
 {return "Implicit Arguments Expression : [ Argument : "+arg.toString()+"]"; }

 public Expr getExp() {return arg;}
}
