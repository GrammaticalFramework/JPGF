package reader;

public class TypedExp extends Expr{
	private Expr exp ; 
	private Type t ;
	 
 public TypedExp(Expr _exp, Type _t) 
  {exp = _exp;
   t = _t ;}

 public String toString()
 {return "Typed Expression : [Expr : "+exp.toString()+" , Type : "+t.toString()+"]";}

 public Expr getExpr() {return exp;}
 public Type getType() {return t;}
 
}
