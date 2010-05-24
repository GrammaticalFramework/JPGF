package org.grammaticalframework.reader;

public class MetaExp extends Expr{
	 private int id;
	 
 public MetaExp(int _id) 
  { id = _id;}

 public String toString()
 {return "Meta Expression : [Id : "+id+"]";}

public int getMeta() {return id;}
}
