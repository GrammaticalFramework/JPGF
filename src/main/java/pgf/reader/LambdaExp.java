package reader;

public class LambdaExp extends Expr{
	 private boolean bType ; 
	 private String vName ;
	 private Expr body;	

public LambdaExp(boolean _bType, String _vName, Expr _body) 
  {bType = _bType;
   vName = _vName;
   body = _body;}

public String toString() {
	return "Lambda Expression : [Bound Type : "+bType+" , Name : "+ vName + " , Body : "+body.toString() + "]";
}

public boolean getType() {return bType ;} 
public String getVarName() {return vName ;}
public Expr getBody() {return body ;}	


}
