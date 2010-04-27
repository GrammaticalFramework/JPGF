package reader;

public class Hypo {
private boolean bind;
private String str;
private Type t;

public Hypo(boolean _bind, String _str, Type _t)
{bind = _bind;
 str = _str;
 t = _t;
}

public String toString()
{return "Bound Type : "+bind+ " , Name : "+str+" , Type : ("+t+")";}

public boolean getBind() {return bind;}
public String getName() {return str;}
public Type getType() {return t;}

}
