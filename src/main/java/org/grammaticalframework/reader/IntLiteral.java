package org.grammaticalframework.reader;

public class IntLiteral extends Literal {
    private int value ;
    public IntLiteral(int _value) {value = _value; }
    
    public int value() { return this.value; }

    public String toString() {
        String s = "Integer Literal : "+value;
        return s;	  
    }
    
    public int getValue() {return value;}
}
