package org.grammaticalframework.reader;

public class AbsFun {
    private String str;
    private Type t;
    private int arit;
    private Eq[] eqs;
    private final double weight;
    
    public AbsFun(String _str, Type _t, int _arit, Eq[] _eqs, double weight) {
	str = _str;
	t = _t;
	arit = _arit;
	eqs = _eqs;
	this.weight = weight;
    }
    
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("<function name=");
	sb.append(str);
	sb.append(" type=");
	sb.append(t);
	sb.append(" arity=");
	sb.append(arit);
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
    
    public String getName() {return str;}
    public Type getType() {return t;}
    public int getArit() {return arit;}
    public Eq[] getEqs() {return eqs;}

}
