package reader;

public class MetaExp extends Expr{
	 int id;
	 
 public MetaExp(int _id) 
  { id = _id;}

 public String toString()
 {return "Meta Expression : [Id : "+id+"]";}
}
