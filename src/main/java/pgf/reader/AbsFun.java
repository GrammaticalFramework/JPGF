package pgf.reader;

public class AbsFun {
String str;
Type t;
int arit;
Eq[] eqs;

public AbsFun(String _str, Type _t, int _arit, Eq[] _eqs)
{str = _str;
 t = _t;
 arit = _arit;
 eqs = _eqs;
}

public String toString()
{String ss = "Name : "+str + "\nType : (" + t.toString() + ")\nArity : "+arit+" , Equations : (";
for(int i=0; i<eqs.length; i++)
	ss+=(" "+eqs[i].toString());
ss+=")\n";
return ss;
 
	}
}
