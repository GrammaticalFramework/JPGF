package reader;

public class LiteralExp extends Expr {
 Literal value;
 
 public LiteralExp(Literal _value) {value = _value; }

 public String toString()
 {return "Literal Expression : " + value.toString();}
}
