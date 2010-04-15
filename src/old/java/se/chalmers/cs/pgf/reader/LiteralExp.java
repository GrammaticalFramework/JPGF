package reader;

public class LiteralExp {
 Literal value;
 
 public LiteralExp(Literal _value) {value = _value; }

 public <R,A> R accept(reader.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

 public String toString()
 {String s = "Literal expression : "+value;
 return s;	  
 }
 
 public boolean equals(Object o) {
   if (this == o) return true;
   if (o instanceof reader.LiteralExp) {
     return true;
   }
   return false;
 }

}
