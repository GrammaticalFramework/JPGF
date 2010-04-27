package reader;

public class StringLiteral extends Literal {
String value;

public StringLiteral(String _value) { value = _value; }

public String toString()
{String s = "String literal : "+value;
return s;	   
};

public String getValue() {return value;}
}


