package reader;

public class Abstract {
private String str;
private Flag[] flags;
private AbsFun[] absFuns;
private AbsCat[] absCats;

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

public String getName() {return str;}
public Flag[] getFlags() {return flags;}
public AbsFun[] getAbsFuns() {return absFuns;}
public AbsCat[] getAbsCats() {return absCats;}

}
