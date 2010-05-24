package org.grammaticalframework.reader;

public class AbsNameExp extends Expr{ 
	 String name ;
	 
 public AbsNameExp(String _name) 
  {name = _name;}

  public String toString()
 {return "Abstract Name Expression : [Name : "+name+"]";}

  public String getName(){return name;}

  
}
