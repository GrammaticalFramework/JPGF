package pgf.reader;

public class Sequence {
private Symbol[] symbs;

public Sequence(Symbol[] _symbs)
{symbs = _symbs;
}

public String toString()
{String ss = "Symbols : [";
 for(int i=0; i<symbs.length; i++)
  ss+=(" "+symbs[i].toString());
ss+="]";
return ss;
}

    public int length() {
        return this.symbs.length;
    }

    public String toString()
    {String ss = "Symbols : [";
        for(int i=0; i<symbs.length; i++)
            ss+=(" "+symbs[i].toString());
        ss+="]";
        return ss;
    }
}
