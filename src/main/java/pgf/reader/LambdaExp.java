package pgf.reader;

public class LambdaExp extends Expr{
	 boolean bType ; 
	 String vName ;
	 Expr body;	
 public LambdaExp(boolean _bType, String _vName, Expr _body) 
  {bType = _bType;
   vName = _vName;
   body = _body;}

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.LambdaExp) {
     return true;
   }
   return false;
 }

@Override
public String toString() {
	return "Lambda Expression : [Bound Type : "+bType+" , Name : "+ vName + " , Body : "+body.toString() + "]";
}

}
