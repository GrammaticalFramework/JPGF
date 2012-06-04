package org.grammaticalframework.reader;

public class Hypo {
    public final boolean bind;
    public final String name;
    public final Type type;

    public Hypo(boolean _bind, String _str, Type _t) {
	this.bind = _bind;
	this.name = _str;
	this.type = _t;
    }

    public String toString() {
	return "Bound Type : "+bind+ " , Name : "+ name +" , Type : ("+ type +")";}

    public boolean getBind() {return bind;}
    public String getName() {return name;}
    public Type getType() {return type;}
}
