package reader;

public class IntLiteral extends Literal {
      int value ;
	  public IntLiteral(int _value) {value = _value; }

	  public <R,A> R accept(reader.Literal.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

	  public String toString()
	  {String s = "Integer Literal : "+value;
	  return s;	  
	  }
	  
	  public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o instanceof reader.IntLiteral) {
	      return true;
	    }
	    return false;
	  }

	  public int hashCode() {
	    return 37;
	  }


	}
