package reader;

public class ImplExp extends Expr{
	 Expr arg;
	 
 public ImplExp(Expr _arg) 
  {arg = _arg;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.ImplExp) {
     return true;
   }
   return false;
 }
 public String toString()
 {return "Implicit Arguments Expression : [ Argument : "+arg.toString()+"]"; }

}
