package reader;

public class AbsCat {
private String str;
private Hypo[] hypos;
private String[] strs;

public AbsCat(String _str, Hypo[] _hypos, String[] _strs)
{str = _str;
 hypos = _hypos;
 strs = _strs;}

public String toString()
{String ss = "Name : "+ str + " , Hypotheses : (";
for(int i=0; i<hypos.length; i++)
	ss+=(" "+hypos[i].toString());
ss+=") , String Names : (";
for(int i=0; i<strs.length; i++)
	ss+=(" "+strs[i].toString());
ss+=")";
return ss;
}
}
