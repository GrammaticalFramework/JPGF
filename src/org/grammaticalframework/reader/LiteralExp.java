package org.grammaticalframework.reader;

public class LiteralExp extends Expr {
 private RLiteral value;
 
 public LiteralExp(RLiteral _value) {value = _value; }

 public String toString()
 {return "Literal Expression : " + value.toString();}

public RLiteral getLiteral() {return value;}
}
