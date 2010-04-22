package pgf.reader;

public class Type {
Hypo [] hypos;
String str;
Expr[] exprs;

public Type(Hypo[] _hypos, String _str, Expr[] _exprs)
{hypos = _hypos; 
 str = _str;
 exprs = _exprs;
}

public String toString()
{String ss = "Hypotheses : (";
for (int i=0; i<hypos.length;i++)
 ss+=(" "+hypos[i].toString());
 ss+=(") , Name : "+ str + " , Expressions : (");
for (int i=0; i<exprs.length; i++)
	ss+=(" "+ exprs[i].toString());
ss+=")";
return ss;
}
}
