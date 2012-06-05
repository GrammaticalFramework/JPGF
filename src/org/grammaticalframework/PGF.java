package org.grammaticalframework;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.reader.*;

public class PGF {

    private int majorVersion;
    private int minorVersion;
    private Map<String, RLiteral> flags;
    public final Abstract abstr;
    private Map<String, Concrete> concretes;

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

    /* ******************************** API ******************************** */
    /**
     * access the concrete grammar by its name
     * @param name the name of the concrete grammar
     * @return the concrete grammar of null if there is no grammr with
     *             that name.
     */
    public Concrete concrete(String name) throws UnknownLanguageException {
	Concrete l = this.concretes.get(name);
        if (l == null)
	    throw new UnknownLanguageException(name);
	return l;
    }

    /* ************************************************* */
    /* Accessing the fields                              */
    /* ************************************************* */

    public int getMajorVersion() {
	return majorVersion;
    }

    public int getMinorVersion() {
	return minorVersion;
    }

    public Abstract getAbstract() {
	return abstr;
    }

    /**
     * Return true if the given name crrespond to a concrete grammar
     * in the pgf, false otherwise.
     */
    public boolean hasConcrete(String name) {
	return this.concretes.containsKey(name);
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
        return ss;
    }
}
