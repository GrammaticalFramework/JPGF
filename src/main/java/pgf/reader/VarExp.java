package reader;

public class VarExp extends Expr{
   int ind;

 public VarExp(int _ind) 
  {ind = _ind;}

 public String toString()
 {return "Variable Expression : [Index : "+ind+"]";}
 
}
