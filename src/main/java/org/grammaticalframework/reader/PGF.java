package org.grammaticalframework.reader;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class PGF {
    private int majorVersion;
    private int minorVersion;
    private Map<String, RLiteral> flags;
    private Abstract abstr;
    private Map<String, Concrete> concretes;
    //    Concrete[] concretes;
    
    public PGF(int _majorVersion, int _minorVersion, 
               Map<String, RLiteral> _flags, 
               Abstract _abstr, 
               Concrete[] concretes)
    {
        majorVersion = _majorVersion;
        minorVersion = _minorVersion;
        flags = _flags;
        abstr = _abstr;
        this.concretes = new HashMap<String,Concrete>();
        for(Concrete cnc : concretes)
            this.concretes.put( cnc.name(), cnc);
    }

    /**
     * Accessors
     */
    public Concrete concrete(String name) {
        return this.concretes.get(name);
    }

    public String toString() {
        String ss =  "PGF : \nmajor version : "+ majorVersion 
            + ", minor version : "+ minorVersion + "\n";
        ss += "flags : (" ;
        for (String flagName : this.flags.keySet() )
            ss += flagName + ": " + this.flags.get(flagName).toString() + "\n";
        ss+= (")\nabstract : (" + abstr.toString() + ")\nconcretes : (");
        for (String name : this.concretes.keySet() )
            ss += name + ", ";
        ss+=")";
        return ss;}

    public int getMajorVersion()
    {return majorVersion;}
    
    public int getMinorVersion()
    {return minorVersion;}
    
    //public Flag[] getFlags()
    //{return flags;}
    
    public Abstract getAbstract()
    {return abstr;}
    
    public HashMap<String, Concrete> getConcretes()
    {HashMap<String, Concrete> hm = (HashMap<String,Concrete>)concretes;
    return hm;	}
    
}
