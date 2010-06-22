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
    private Abstract abstr;
    private Map<String, Concrete> concretes;

    /* ******************************** API ******************************** */
    /**
     * Reads a PGF grammar from a file.
     *
     * @param filepath the path of the pgf file.
     * @return the PGF object read from the file.
     */
    public static PGF readFromFile(String filepath)
        throws java.io.FileNotFoundException, java.io.IOException
    {
        return PGFReader.readFile(filepath);
    }

    /**
     * Reads a PGF grammar from an InputStream
     *
     * @param is the InputStream
     * @return the PGF object read from the stream.
     */
    public static PGF readFromInputStream(java.io.InputStream is)
        throws java.io.IOException
    {
        return new PGFReader().readInputStream(is);
    }

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

    public class UnknownLanguageException extends Exception {
	private String language;
	public UnknownLanguageException(String language) {
	    this.language = language;
	}

	public String getLanguage() {
	    return this.language;
	}
    }

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
