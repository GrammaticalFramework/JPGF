package org.grammaticalframework.reader;

public class AbsCat {
    public final String name;
    private final Hypo[] hypos;
    private final WeightedIdent[] functions;
    
    public AbsCat(String name, Hypo[] _hypos, WeightedIdent[] functions)
    {
        this.name = name;
        this.hypos = _hypos;
        this.functions = functions;
    }

    public String name() {
        return name;
    }

    public Hypo[] getHypos() {
	return hypos;
    }
    
    public WeightedIdent[] getFunctions() {
	return functions;
    }

    public String toString() {
        String ss = "Name : "+ name + " , Hypotheses : (";
        for(int i=0; i<hypos.length; i++)
            ss+=(" "+hypos[i].toString());
        ss+=") , String Names : (";
        for(int i=0; i<functions.length; i++)
            ss+=(" "+functions[i].toString());
        ss+=")";
        return ss;
    }

}

