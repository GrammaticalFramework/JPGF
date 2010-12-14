package org.grammaticalframework.reader;

public class StringLit extends RLiteral {
    String value;
    
    public StringLit(String _value) {
	value = _value;
    }
    
    public String toString() {
	String s = "String literal : "+value;
	return s;	   
    };
    
    public String getValue() {
	return value;
    }
}


