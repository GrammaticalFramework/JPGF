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
	StringBuffer sb = new StringBuffer();
	sb.append("<function name=");
	sb.append(name);
	sb.append(" type=");
	sb.append(type);
	sb.append(" arity=");
	sb.append(arity);
	sb.append(" equations=[");
	for (Eq e : this.eqs) {
	    sb.append(eqs);
	    sb.append(", ");
	}
	sb.append("] weight=");
	sb.append(weight);
	sb.append(">");
	return sb.toString();
    }
}
