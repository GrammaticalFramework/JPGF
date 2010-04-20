package reader;

public class AppExp extends Expr{
	 Expr lExp ; 
	 Expr rExp ;
		
 public AppExp(Expr _lExp, Expr _rExp) 
  {lExp = _lExp;
   rExp = _rExp;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public String toString()
 {return "Expression application [Left-hand side : ( "+lExp.toString() + "), Right-hand side : ("+rExp.toString()+")]";}
 
 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.AppExp) {
     return true;
   }
   return false;
 }

}
