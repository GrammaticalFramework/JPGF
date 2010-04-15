package reader;

public class FloatLiteral extends Literal {
      float value;
       
	  public FloatLiteral(float _value) {value = _value; }

	  public <R,A> R accept(reader.Literal.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

	  public String toString()
	  {String s = "Float literal : "+value;
	  return s;
	  }
	  
	  public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o instanceof reader.FloatLiteral) {
	      return true;
	    }
	    return false;
	  }

	  public int hashCode() {
	    return 37;
	  }


	}
