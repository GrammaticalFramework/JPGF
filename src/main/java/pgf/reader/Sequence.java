package pgf.reader;

public class Sequence {
    Symbol[] symbs;

    public Sequence(Symbol[] _symbs)
    {
        symbs = _symbs;
    }

    public Symbol symbol(int index) {
        return this.symbs[index];
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
