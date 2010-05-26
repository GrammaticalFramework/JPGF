package org.grammaticalframework.reader;

public class AbsCat {
    private String name;
    private Hypo[] hypos;
    private String[] strs;
    
    public AbsCat(String name, Hypo[] _hypos, String[] _strs)
    {
        this.name = name;
        hypos = _hypos;
        strs = _strs;
    }

    public String name() {
        return name;
    }
    public Hypo[] getHypos() {return hypos;}
    public String[] getFunctions() {return strs;}

    public String toString() {
        String ss = "Name : "+ name + " , Hypotheses : (";
        for(int i=0; i<hypos.length; i++)
            ss+=(" "+hypos[i].toString());
        ss+=") , String Names : (";
        for(int i=0; i<strs.length; i++)
            ss+=(" "+strs[i].toString());
        ss+=")";
        return ss;
    }

}

