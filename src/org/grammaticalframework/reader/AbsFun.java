package org.grammaticalframework.reader;

public class AbsFun {
    public final String name;
    public final Type type;
    public final int arity;
    public final Eq[] eqs;
    public final double weight;
    
    public AbsFun(String name, Type type, int arity, Eq[] _eqs, double weight) {
	this.name = name;
	this.type = type;
	this.arity = arity;
	this.eqs = _eqs;
	this.weight = weight;
    }
 
    public String toString() {
	return name + " : " + this.type ;
    }
}
