package org.grammaticalframework.reader;

public class FloatLit extends RLiteral {
private double value;
       
public FloatLit(double _value) {value = _value; }

public String toString()
  {String s = "Float literal : "+value;
  return s;	  
}

public double getValue() {return value;}

	}
