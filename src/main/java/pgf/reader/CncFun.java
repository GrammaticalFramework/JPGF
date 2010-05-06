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

public String getName(){return name;}
public int[] getInds(){return inds;}

}
