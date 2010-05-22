package pgf.reader;

public class CncFun {
private String name;
private int[] inds;

public CncFun(String _name, int[] _inds)
{name = _name;
 inds = _inds;
}

public String toString()
{String ss = "Name : "+name + " , Indices : ";
for(int i=0; i<inds.length; i++)
 ss+=(" "+inds[i]);
return ss;
}

public boolean equals(Object o)
{if(o instanceof CncFun)
	{CncFun newo = (CncFun) o;
	if(!name.equals(newo.getName())) return false;
	if(inds.length != newo.getInds().length) return false;
	for(int i=0;i<inds.length;i++)
		if(inds[i]!=newo.getInds()[i]) return false;
	return true;
	}
return false;
}

public String getName(){return name;}
public int[] getInds(){return inds;}

}
