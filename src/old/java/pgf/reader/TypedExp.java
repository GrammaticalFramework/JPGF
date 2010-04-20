package reader;

public class TypedExp extends Expr{
	 Expr exp ; 
	 Type t ;
	 
 public TypedExp(Expr _exp, Type _t) 
  {exp = _exp;
   t = _t ;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.TypedExp) {
     return true;
   }
   return false;
 }
 
 public String toString()
 {return "Typed Expression : [Expr : "+exp.toString()+" , Type : "+t.toString()+"]";}

}
