package org.grammaticalframework.reader;

import java.util.Map;

public class Abstract {
    private String name;
    private Map<String,RLiteral> flags;
    private AbsFun[] absFuns;
    private AbsCat[] absCats;


    public Abstract(String name,
                    Map<String, RLiteral> _flags, 
                    AbsFun[] _absFuns, 
                    AbsCat[] _absCats)
    {
        this.name = name;
        flags = _flags;
        absFuns = _absFuns;
        absCats = _absCats;
    }

    public String name() {
        return name;
    }
    public AbsFun[] getAbsFuns() {return absFuns;}
    public AbsCat[] getAbsCats() {return absCats;}

    public String toString()
    {String ss = "Name : "+ name + " , Flags : (";
        // for(int i=0; i<flags.length;i++)
        // 	ss+=(" "+flags[i].toString());
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
