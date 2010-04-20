package reader;

public class LiteralExp extends Expr {
 Literal value;
 
 public LiteralExp(Literal _value) {value = _value; }

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.LiteralExp) {
     return true;
   }
   return false;
 }

 public String toString()
 {return "Literal Expression : " + value.toString();}
}
