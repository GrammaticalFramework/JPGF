package pgf.reader;

public class MetaExp extends Expr{
	 int id;
	 
 public MetaExp(int _id) 
  { id = _id;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.MetaExp) {
     return true;
   }
   return false;
 }

 public String toString()
 {return "Meta Expression : [Id : "+id+"]";}
}
