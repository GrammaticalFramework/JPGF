package pgf.reader;

public class AbsNameExp extends Expr{ 
	 String name ;
	 
 public AbsNameExp(String _name) 
  {name = _name;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.AbsNameExp) {
     return true;
   }
   return false;
 }
 public String toString()
 {return "Abstract Name Expression : [Name : "+name+"]";}

}
