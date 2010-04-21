package pgf.reader;

public class StringLiteral extends Literal {
          String value;
		  public StringLiteral(String _value) { value = _value; }
          public String toString()
          {String s = "String literal : "+value;
          return s;	   
          };
		  public <R,A> R accept(reader.Literal.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

		  public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o instanceof reader.StringLiteral) {
		      return true;
		    }
		    return false;
		  }

		  public int hashCode() {
		    return 37;
		  }


		}


