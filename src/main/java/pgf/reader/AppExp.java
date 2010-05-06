package pgf.reader;

public class AppExp extends Expr{
	 private Expr lExp ; 
	 private Expr rExp ;
		
 public AppExp(Expr _lExp, Expr _rExp) 
  {lExp = _lExp;
   rExp = _rExp;}

 public String toString()
 {return "Expression application [Left-hand side : ( "+lExp.toString() + "), Right-hand side : ("+rExp.toString()+")]";}
 
public Expr getLeftExpr() {return lExp;}
public Expr getRightExpr() {return rExp;}
}
