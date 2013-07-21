/* PGFBuilder.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.*;
import java.io.ByteArrayOutputStream;

import java.lang.RuntimeException;
import org.grammaticalframework.reader.*;

public class PGFBuilder {

    private static final boolean DBG = false;

    /* ************************************************* */
    /* Public reading functions                          */
    /* ************************************************* */
    /**
     * Reads a PGF binary from a file idenfied by filename.
     *
     * @param filename the path of the pgf file.
     */
    public static PGF fromFile(String filename)
        throws FileNotFoundException, IOException
    {
	if (DBG) System.err.println("Reading pgf from file : " + filename);
        InputStream stream = new FileInputStream(filename);
	try {
	    return new PGFReader(stream).readPGF();
	} catch (UnknownLanguageException e) {
	    throw new RuntimeException();
	}
    }

    /**
     * Reads a PGF binary from a file idenfied by filename.
     * A list of the desired languages is given to the function so that the pgf
     * doesn't have to be read entierely. The pgf file have to be indexed
     * 
     *
     * @param filename the path of the pgf file.
     * @param languages the list of desired languages
     */
    public static PGF fromFile(String filename, String[] languages)
        throws FileNotFoundException, IOException, UnknownLanguageException
    {
	if (DBG) System.err.println("Reading pgf from file : " + filename);
        InputStream stream = new FileInputStream(filename);
        return new PGFReader(stream, languages).readPGF();
    }

    /**
     * Reads a pgf from an input stream
     *
     * @param inStream and InputStream to read the pgf binary from.
     */
    public static PGF fromInputStream(InputStream stream)
        throws IOException
    {
	try {
	    return new PGFReader(stream).readPGF();
	} catch (UnknownLanguageException e) {
	    throw new RuntimeException();
	}
    }

    /**
     * Reads a pgf from an input stream
     * A list of the desired languages is given to the function so that the pgf
     * doesn't have to be read entierely. The pgf file have to be indexed
     *
     * @param inStream and InputStream to read the pgf binary from.
     */
    public static PGF fromInputStream(InputStream stream, String[] languages)
	throws IOException, UnknownLanguageException 
    {
        return new PGFReader(stream, languages).readPGF();
    }
}

class PGFReader {

    private static final boolean DBG = false;

    private DataInputStream mDataInputStream;
    private Set<String> languages;
    
    public PGFReader(InputStream is) {
        this.mDataInputStream = new DataInputStream(is);
    }
    
    public PGFReader(InputStream is, String[] languages) throws UnknownLanguageException {
        this.mDataInputStream = new DataInputStream(is);
	this.languages = new HashSet(Arrays.asList(languages));
    }
    
    public PGF readPGF() throws IOException, UnknownLanguageException {
	Map<String, Integer> index = null;
        // Reading the PGF version
        int ii[]=new int[4];
        for(int i=0;i<4;i++)
            ii[i]=mDataInputStream.read();
	if (DBG) System.err.println("PGF version : "  + ii[0] + "." + ii[1] 
				    + "." + ii[2] + "." + ii[3]);
        // Reading the global flags
        Map<String,RLiteral> flags = getListFlag();
	if (flags.containsKey("index")) {
	    index = readIndex(((StringLit)flags.get("index")).getValue());
	    if (DBG) System.err.println(index.toString());
	}
        // Reading the abstract
        Abstract abs = getAbstract();
        String startCat = abs.startcat();
        // Reading the concrete grammars
        int nbConcretes = getInt();
        Concrete[] concretes;
	if (languages != null)
	    concretes = new Concrete[languages.size()];
	else
	    concretes = new Concrete[nbConcretes];
	int k = 0;
	for (int i=0; i<nbConcretes; i++) {
	    String name = getIdent();
	    if (DBG) System.err.println("Language " + name);
	    if (languages == null || languages.remove(name)) {
	       concretes[k] = getConcrete(name, startCat);
	       k++;
	    } else {
		if (index != null) {
		    this.mDataInputStream.skip(index.get(name));
		    if (DBG) System.err.println("Skiping " + name);
		} else
		    getConcrete(name, startCat);
	    }
	}
	// test that we actually found all the selected languages
	if (languages != null && languages.size() > 0)
		for (String l: languages)
		    throw new UnknownLanguageException(l);
	
        // builds and returns the pgf object.
        PGF pgf = new PGF(makeInt16(ii[0],ii[1]),
                          makeInt16(ii[2],ii[3]),
                          flags, abs, concretes);
        mDataInputStream.close();
        return pgf;
    }

    /**
     * This function guess the default start category from the
     * PGF flags: if the startcat flag is set then it is taken as default cat.
     * otherwise "Sentence" is taken as default category.
     */
    private String getStartCat(Map<String,RLiteral> flags) {
        RLiteral cat = flags.get("startcat");
        if (cat == null)
            return "Sentence";
        else
            return ((StringLit)cat).getValue();

    }

    private Map<String,Integer> readIndex(String s) {
	String[] items = s.split(" +");
	Map<String,Integer> index = new HashMap<String,Integer>();
	for (String item: items) {
	    String[] i = item.split(":");
	    index.put(i[0],new Integer(i[1]));
	}
	return index;
    }

    
    /* ************************************************* */
    /* Reading abstract grammar                          */
    /* ************************************************* */
    /**
     * This function reads the part of the pgf binary corresponding to
     * the abstract grammar.
     * @param is the stream to read from.
     */
    private Abstract getAbstract() throws IOException
    {
        String name = getIdent();
	if (DBG) System.err.println("Abstract syntax [" + name + "]");
        Map<String,RLiteral> flags = getListFlag();
        AbsFun[] absFuns = getListAbsFun();
        AbsCat[] absCats = getListAbsCat();
        return new Abstract(name,flags,absFuns,absCats);
    }

    private Pattern[] getListPattern() throws IOException {
        int npoz = getInt();
        Pattern[] patts = new Pattern[npoz];
        for(int i=0; i<npoz; i++)
            patts[i]=getPattern();
        return patts;
    }

    private Eq getEq() throws IOException {
        Pattern[] patts = getListPattern();
        Expr exp = getExpr();
        return new Eq(patts,exp);
    }



    private AbsFun getAbsFun() throws IOException {
        String name = getIdent();
	if (DBG) System.err.println("AbsFun: '"
				    + name + "'");
        Type t = getType();
        int i = getInt();
        int has_equations = mDataInputStream.read();
	Eq[] equations;
	if (has_equations == 0)
	    equations = new Eq[0];
	else
	    equations = getListEq();
	double weight = getDouble();
        AbsFun f = new AbsFun(name,t,i,equations, weight);
	if (DBG) System.err.println("/AbsFun: " + f);
	return f;
    }

    private AbsCat getAbsCat() throws IOException {
        String name = getIdent();
        Hypo[] hypos = getListHypo();
        WeightedIdent[] functions = getListWeightedIdent();
        AbsCat abcC = new AbsCat(name,hypos, functions);
        return abcC;
    }

    private AbsFun[] getListAbsFun() throws IOException {
        int npoz = getInt();
        AbsFun[] absFuns = new AbsFun[npoz];

        if(npoz == 0)
            return absFuns;

        else for (int i=0; i<npoz; i++)
                 absFuns[i] = getAbsFun();

        return absFuns;
    }

    private AbsCat[] getListAbsCat() throws IOException {
        int npoz = getInt();
        AbsCat[] absCats = new AbsCat[npoz];
        if(npoz == 0)
            return absCats;
        else
            for (int i=0; i<npoz; i++)
                absCats[i] = getAbsCat();

        return absCats;
    }


    private Type getType() throws IOException {
        Hypo[] hypos = getListHypo();
        String returnCat = getIdent();
        Expr[] exprs = getListExpr();
	Type t = new Type(hypos, returnCat, exprs);
	if (DBG) System.err.println("Type: " + t);
        return t;

    }

    private Hypo getHypo() throws IOException { 
	int btype = mDataInputStream.read();
        boolean b = btype == 0 ? false : true;
        String varName = getIdent();
        Type t = getType();
        Hypo hh = new Hypo (b,varName,t);
        return hh;
    }

    private Hypo[] getListHypo() throws IOException {
        int npoz = getInt();
        Hypo[] hypos = new Hypo[npoz];
        for (int i=0; i<npoz; i++)
            hypos[i]=getHypo();
        return hypos;
    }

    private Expr[] getListExpr( ) throws IOException {
        int npoz = getInt();
        Expr[] exprs = new Expr[npoz];
        for(int i=0; i<npoz; i++)
            exprs[i]=getExpr();
        return exprs;
    }

    private Expr getExpr( ) throws IOException {
        int sel = mDataInputStream.read();
        Expr expr = null;
        switch (sel) {
        case 0 : //lambda abstraction
            int bt = mDataInputStream.read();
            boolean btype = bt == 0 ? false : true ;
            String varName = getIdent();
            Expr e1 = getExpr();
            expr = new LambdaExp(btype,varName,e1);
            break;
        case 1 : //expression application
            Expr e11 = getExpr();
            Expr e2 = getExpr();
            expr = new AppExp(e11,e2);
            break;
        case 2 : //literal expression
            RLiteral lit = getLiteral();
            expr = new LiteralExp(lit);
            break;
        case 3 : //meta variable
            int id = getInt();
            expr = new MetaExp(id);
            break;
        case 4 : //abstract function name
            String absFun = getIdent();
            expr = new AbsNameExp(absFun);
            break;
        case 5 : //variable
            int v = getInt();
            expr = new VarExp(v);
            break;
        case 6 : //type annotated expression
            Expr e = getExpr();
            Type t = getType();
            expr = new TypedExp(e,t);
            break;
        case 7 : //implicit argument
            Expr ee = getExpr();
            expr = new ImplExp(ee);
            break;
        default : throw new IOException("invalid tag for expressions : "+sel);
        }
        return expr;
    }


    private Eq[] getListEq( ) throws IOException {
        int npoz = getInt();
        Eq[] eqs = new Eq[npoz];
        for (int i=0; i<npoz;i++)
            eqs[i]=getEq();
        return eqs;
    }

    private Pattern getPattern( ) throws IOException {
        int sel = mDataInputStream.read();
        Pattern patt = null;
        switch (sel) {
        case 0 : //application pattern
            String absFun = getIdent();
            Pattern[] patts = getListPattern();
            patt = new AppPattern(absFun,patts);
            break;
        case 1 : //variable pattern
            String varName = getIdent();
           patt = new VarPattern(varName);
            break;
        case 2 : //variable as pattern
            String pVarName = getIdent();
            Pattern p = getPattern();
            patt = new VarAsPattern(pVarName,p);
            break;
        case 3 : //wild card pattern
            patt = new WildCardPattern();
            break;
        case 4 : //literal pattern
            RLiteral lit = getLiteral();
            patt = new LiteralPattern(lit);
            break;
        case 5 : //implicit argument
            Pattern pp = getPattern();
            patt = new ImpArgPattern(pp);
        case 6 : //inaccessible pattern
            Expr e = getExpr();
            patt = new InaccPattern(e);
            break;
        default : throw new IOException("invalid tag for patterns : "+sel);
        }
        return patt;
    }

    private RLiteral getLiteral( ) throws IOException {
        int sel = mDataInputStream.read();
        RLiteral ss = null;
        switch (sel) {
        case 0 :
            String str = getString();
            ss = new StringLit(str);
            break;
        case 1 :
            int i = getInt();
            ss = new IntLit(i);
            break;
        case 2 :
            double d = getDouble();
            ss = new FloatLit(d);
            break;
        default :
            throw new IOException("Incorrect literal tag "+sel);
        }
        return ss;
    }

    /* ************************************************* */
    /* Reading concrete grammar                          */
    /* ************************************************* */
    private Concrete getConcrete(String name, String startCat)
	throws IOException
    {
	if (DBG) System.err.println("Concrete: " + name);
	if (DBG) System.err.println("Concrete: Reading flags");
        Map<String,RLiteral> flags = getListFlag();
        // We don't use the print names, but we need to read them to skip them
	if (DBG) System.err.println("Concrete: Skiping print names");
        getListPrintName();
	if (DBG) System.err.println("Concrete: Reading sequences");
        Sequence[] seqs = getListSequence();
        CncFun[] cncFuns = getListCncFun(seqs);
	// We don't need the lindefs for now but again we need to
	// parse them to skip them
	getListLinDef();
        ProductionSet[] prods = getListProductionSet(cncFuns);
        Map<String, CncCat> cncCats = getListCncCat();
        int i = getInt();
        return new Concrete(name,flags,seqs,cncFuns,prods,cncCats,i,startCat);
    }

    /* ************************************************* */
    /* Reading print names                               */
    /* ************************************************* */
    // FIXME : not used, we should avoid creating the objects
    private PrintName getPrintName( ) throws IOException
    {
        String absName = getIdent();
        String printName = getString();
        return new PrintName(absName, printName);

    }

    private PrintName[] getListPrintName( )
        throws IOException
    {
        int npoz = getInt();
        PrintName[] pnames = new PrintName[npoz];
        if(npoz == 0) return pnames;
        else
            for (int i=0; i<npoz; i++)
                pnames[i] = getPrintName();
        return pnames;
    }

    /* ************************************************* */
    /* Reading sequences                                 */
    /* ************************************************* */
    private Sequence getSequence( ) throws IOException {
        Symbol[] symbols = getListSymbol();
        return new Sequence(symbols);
    }

    private Sequence[] getListSequence( )
        throws IOException
    {
        int npoz = getInt();
        Sequence[] seqs = new Sequence[npoz];
        for(int i=0; i<npoz; i++)
            seqs[i]=getSequence();
        return seqs;
    }

    private Symbol getSymbol( ) throws IOException {
        int sel = mDataInputStream.read();
	if (DBG) System.err.println("Symbol: type=" + sel);
        Symbol symb = null;
        switch (sel) {
        case 0 : // category (non terminal symbol)
        case 1 : // Lit (Not implemented properly)
            int i1 = getInt();
            int i2 = getInt();
            symb = new ArgConstSymbol(i1,i2);
            break;
	case 2: // Variable (Not implemented)
	    throw new UnsupportedOperationException(
		  "Var symbols are not supported yet");
        case 3: //sequence of tokens
            String[] strs = getListString();
            symb = new ToksSymbol(strs);
            break;
        case 4 : //alternative tokens
            String[] altstrs = getListString();
            Alternative[] as = getListAlternative();
            symb = new AlternToksSymbol(altstrs,as);
            break;
        default : throw new IOException("invalid tag for symbols : "+sel);
        }
	if (DBG) System.err.println("/Symbol: " + symb);
        return symb;
    }

    private Alternative[] getListAlternative( )
        throws IOException
    {
        int npoz = getInt();
        Alternative[] alts = new Alternative[npoz];
        for(int i=0;i<npoz;i++)
            alts[i] = getAlternative();
        return alts;
    }

    private Alternative getAlternative( )
        throws IOException
    {
        String[] s1 = getListString();
        String[] s2 = getListString();
        return new Alternative(s1,s2);
    }

    private Symbol[] getListSymbol( )
        throws IOException
    {
        int npoz = getInt();
        Symbol[] symbols = new Symbol[npoz];
        for(int i=0; i<npoz; i++)
            symbols[i]=getSymbol();
        return symbols;
    }

    /* ************************************************* */
    /* Reading concrete functions                        */
    /* ************************************************* */
    private CncFun getCncFun(Sequence[] sequences)
        throws IOException
    {
        String name = getIdent();
        int[] sIndices = getListInt();
        int l = sIndices.length;
        Sequence[] seqs = new Sequence[l];
        for (int i = 0 ; i < l ; i++)
            seqs[i] = sequences[sIndices[i]];
        return new CncFun(name,seqs);
    }

    private CncFun[] getListCncFun(Sequence[] sequences)
        throws IOException
    {
        int npoz = getInt();
        CncFun[] cncFuns = new CncFun[npoz];
        for(int i=0; i<npoz; i++)
            cncFuns[i]=getCncFun(sequences);
        return cncFuns;
    }

    /* ************************************************* */
    /* Reading LinDefs                                   */
    /* ************************************************* */
    // LinDefs are stored as an int map (Int -> [Int])

    private LinDef[] getListLinDef()
        throws IOException
    {
        int size = getInt();
        LinDef[] linDefs = new LinDef[size];
        for(int i=0 ; i < size ; i++)
            linDefs[i] = getLinDef();
        return linDefs;
    }

    private LinDef getLinDef()
        throws IOException
    {
        int key = getInt();
        int listSize = getInt();
	int[] funIds = new int[listSize];
        for(int i=0 ; i < listSize ; i++)
            funIds[i] = getInt();
        return new LinDef(key, funIds);
    }

    /* ************************************************* */
    /* Reading productions and production sets           */
    /* ************************************************* */
    /**
     * Read a production set
     * @param is is the input stream to read from
     * @param cncFuns is the list of concrete function
     */
    private ProductionSet getProductionSet(CncFun[] cncFuns)
        throws IOException
    {
        int id = getInt();
        Production[] prods = getListProduction( id, cncFuns);
        ProductionSet ps = new ProductionSet(id,prods);
        return ps;
    }

    /**
     * Read a list of production set
     * @param is is the input stream to read from
     * @param cncFuns is the list of concrete function
     */
    private ProductionSet[] getListProductionSet(CncFun[] cncFuns)
        throws IOException
    {
        int npoz = getInt();
        ProductionSet[] prods = new ProductionSet[npoz];
        for(int i=0; i<npoz; i++)
            prods[i]= getProductionSet(cncFuns);
        return prods;
    }

    /**
     * Read a list of production
     * @param is is the input stream to read from
     * @param leftCat is the left hand side category of this production (
     * read only once for the whole production set)
     * @param cncFuns is the list of concrete function
     */
    private Production[] getListProduction(int leftCat,
                                             CncFun[] cncFuns)
        throws IOException
    {
        int npoz = getInt();
        Production[] prods = new Production[npoz];
        for(int i=0; i<npoz; i++)
            prods[i]=getProduction(leftCat, cncFuns);
        return prods;
    }

    /**
     * Read a production
     * @param is is the input stream to read from
     * @param leftCat is the left hand side category of this production
     *                (read only once for the whole production set)
     * @param cncFuns is the list of concrete function, used here to set the
     *                function of the production (only given by its index in
     *                the list)
     */
    private Production getProduction(int leftCat, CncFun[] cncFuns)
        throws IOException
    {
        int sel = mDataInputStream.read();
	if (DBG) System.err.println("Production: type=" + sel);
        Production prod = null;
        switch (sel) {
        case 0 : //application
            int i = getInt();
            int[] domain = getDomainFromPArgs();
            prod = new ApplProduction(leftCat, cncFuns[i],domain);
            break;
        case 1 : //coercion
            int id = getInt();
            prod = new CoerceProduction(leftCat, id);
            break;
        default : throw new IOException("invalid tag for productions : "+sel);
        }
	if (DBG) System.err.println("/Production: " + prod);
	return prod;
    }

    // This function reads a list of PArgs (Productions arguments)
    // but returns only the actual domain (the category of the argumetns)
    // since we don't need the rest for now...
    private int[] getDomainFromPArgs()
        throws IOException
    {
        int size = getInt();
        int[] domain = new int[size];
        for(int i=0; i < size; i++) {
	    // Skiping the list of integers
	    getListInt();
            domain[i]= getInt();
	}
        return domain;
    }

    /* ************************************************* */
    /* Reading concrete categories                       */
    /* ************************************************* */
    private CncCat getCncCat( ) throws IOException
    {
        String sname = getIdent();
        int firstFId = getInt();
        int lastFId = getInt();
        String[] ss = getListString();
        return new CncCat(sname,firstFId,lastFId,ss);
    }

    private Map<String, CncCat> getListCncCat( ) throws IOException
    {
        int npoz = getInt();
        Map<String, CncCat> cncCats = new HashMap<String,CncCat>();
        String name;
        int firstFID, lastFID;
        String[] ss;
        for(int i=0; i<npoz; i++) {
            name = getIdent();
            firstFID = getInt();
            lastFID = getInt();
            ss = getListString();
            cncCats.put(name, new CncCat(name,firstFID,lastFID,ss));
        }
        return cncCats;
    }

    /* ************************************************* */
    /* Reading flags                                     */
    /* ************************************************* */
    private Map<String,RLiteral> getListFlag( )
        throws IOException {
        int npoz = getInt();
        Map<String,RLiteral> flags = new HashMap<String,RLiteral>();
        if (npoz == 0)
            return flags;
        for (int i=0; i<npoz; i++) {
            String ss = getIdent();
            RLiteral lit = getLiteral();
            flags.put(ss, lit);
        }
        return flags;
    }

    /* ************************************************* */
    /* Reading strings                                   */
    /* ************************************************* */
    private String getString( ) throws IOException {
        // using a byte array for efficiency
        ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
        int npoz = getInt();
        int r ;
        for (int i=0; i<npoz; i++) {
            r = mDataInputStream.read();
            os.write((byte)r);
            if (r <= 0x7f) {}                              //lg = 0;
            else if ((r >= 0xc0) && (r <= 0xdf))
                os.write((byte)mDataInputStream.read());   //lg = 1;
            else if ((r >= 0xe0) && (r <= 0xef)) {
                os.write((byte)mDataInputStream.read());   //lg = 2;
                os.write((byte)mDataInputStream.read());
            } else if ((r >= 0xf0) && (r <= 0xf4)) {
                os.write((byte)mDataInputStream.read());   //lg = 3;
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
            } else if ((r >= 0xf8) && (r <= 0xfb)) {
                os.write((byte)mDataInputStream.read());   //lg = 4;
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
            } else if ((r >= 0xfc) && (r <= 0xfd)) {
                os.write((byte)mDataInputStream.read());   //lg =5;
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
                os.write((byte)mDataInputStream.read());
            } else throw new IOException("Undefined for now !!! ");
        }
        return os.toString("UTF-8"); 
    }

    private String[] getListString( )
        throws IOException
    {
        int npoz = getInt();
        String[] strs = new String[npoz];
        if(npoz == 0)
            return strs;
        else {for (int i=0; i<npoz; i++)
                strs[i] = getString();
        }
        return strs;
    }

    /**
     * Some string (like categories identifiers) are not allowed to
     * use the full utf8 tables but only latin 1 caracters.
     * We can read them faster using this knowledge.
     **/
    private String getIdent( ) throws IOException {
        int nbChar = getInt();
        byte[] bytes = new byte[nbChar];
        this.mDataInputStream.read(bytes);
        return new String(bytes, "ISO-8859-1");
    }

    private String[] getListIdent( )
        throws IOException
    {
        int nb = getInt();
        String[] strs = new String[nb];
        for (int i=0; i<nb; i++)
            strs[i] = getIdent();
        return strs;
    }


    // Weighted idents are a pair of a String (the ident) and a double
    // (the ident).
    private WeightedIdent[] getListWeightedIdent( )
        throws IOException
    {
        int nb = getInt();
        WeightedIdent[] idents = new WeightedIdent[nb];
        for (int i=0; i<nb; i++) {
	    double w = getDouble();
	    String s = getIdent();
	    idents[i] = new WeightedIdent(s,w);
	}
        return idents;
    }

    /* ************************************************* */
    /* Reading integers                                  */
    /* ************************************************* */
    // this reads a 'Int' in haskell serialized by the pgf serializer.
    // Those are srialized with a variable length (like some strings)
    // to gain space.
    private int getInt( ) throws IOException {
        long rez = (long)mDataInputStream.read();
        if (rez <= 0x7f)
            return (int)rez;
        else {
            int ii = getInt();
            rez = (ii <<7) | (rez & 0x7f);
            return (int)rez;
        }
    }

    private int[] getListInt() throws IOException
    {
        int npoz = getInt();
        int[] vec = new int[npoz];
        for(int i=0; i<npoz; i++)
            vec[i] = getInt();
        return vec;
    }

    private int makeInt16(int j1, int j2) {
        int i = 0;
        i |= j1 & 0xFF;
        i <<= 8;
        i |= j2 & 0xFF;
        return i;
    }

    // Reading doubles
    private double getDouble() throws IOException {
	return mDataInputStream.readDouble();
    }
}





