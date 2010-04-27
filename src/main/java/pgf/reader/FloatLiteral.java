package reader;

public class FloatLiteral extends Literal {
private double value;
       
public FloatLiteral(double _value) {value = _value; }

public String toString()
  {String s = "Float literal : "+value;
  return s;	  
}

public double getValue() {return value;}

	}
