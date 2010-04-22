package pgf.reader;

public class TypedExp extends Expr{
	 Expr exp ; 
	 Type t ;
	 
 public TypedExp(Expr _exp, Type _t) 
  {exp = _exp;
   t = _t ;}


 public String toString()
 {return "Typed Expression : [Expr : "+exp.toString()+" , Type : "+t.toString()+"]";}

}
