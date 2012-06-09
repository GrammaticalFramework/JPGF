package org.grammaticalframework.parser;

public class ParseError extends Exception {
    private final String msg;
    
    public ParseError(String msg) {
	this.msg = msg;
    }

    public String toString() {
	return "Parse error: " + msg;
    }


}