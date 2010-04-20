package reader;

public class Abstract {
String str;
Flag[] flags;
AbsFun[] absFuns;
AbsCat[] absCats;

public Abstract(String _str, Flag[] _flags, AbsFun[] _absFuns, AbsCat[] _absCats)
{str = _str;
 flags = _flags;
 absFuns = _absFuns;
 absCats = _absCats; 
}

public String toString()
{String ss = "Name : "+str + " , Flags : (";
for(int i=0; i<flags.length;i++)
	ss+=(" "+flags[i].toString());
ss+=") , Abstract Functions : (";
for(int i=0;i<absFuns.length;i++)
	ss+=(" "+absFuns[i].toString());
ss+=") , Abstract Categories : (";
for(int i=0;i<absCats.length;i++)
	ss+=(" "+absCats[i].toString());
ss+=")";
return ss;
}
}
