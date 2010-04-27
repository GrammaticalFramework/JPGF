package reader;

public class IntLiteral extends Literal {
int value ;

public IntLiteral(int _value) {value = _value; }

public String toString()
{String s = "Integer Literal : "+value;
 return s;	  
}

public int getValue() {return value;}
}
