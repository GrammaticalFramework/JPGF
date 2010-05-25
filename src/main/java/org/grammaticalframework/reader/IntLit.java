package org.grammaticalframework.reader;

public class IntLit extends RLiteral {
    private int value ;
    public IntLit(int _value) {value = _value; }
    
    public int value() { return this.value; }

    public String toString() {
        String s = "Integer Literal : "+value;
        return s;	  
    }
    
    public int getValue() {return value;}
}
