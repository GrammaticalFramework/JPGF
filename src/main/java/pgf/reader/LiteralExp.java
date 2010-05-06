package pgf.reader;

public class LiteralExp extends Expr {
 private Literal value;
 
 public LiteralExp(Literal _value) {value = _value; }

 public String toString()
 {return "Literal Expression : " + value.toString();}

public Literal getLiteral() {return value;}
}
