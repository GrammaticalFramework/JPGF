package reader;

public class VarExp extends Expr{
   int ind;

 public VarExp(int _ind) 
  {ind = _ind;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.VarExp) {
     return true;
   }
   return false;
 }

 public String toString()
 {return "Variable Expression : [Index : "+ind+"]";}
 
}
