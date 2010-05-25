package org.grammaticalframework.reader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.*;

public class NewReader {
  private static Logger log = Logger.getLogger("PGF.Reader");
  
  protected int makeInt16(int j1, int j2)
  {int i = 0;
   i |= j1 & 0xFF;
   i <<= 8;
   i |= j2 & 0xFF;
   return i;  }

    public PGF readFile(String filename) throws FileNotFoundException,
                                                IOException
    {
        InputStream stream = new FileInputStream(filename);
        return this.process(stream);
    }
  
    public PGF process(InputStream inStream) throws IOException {
            DataInputStream is = new DataInputStream(inStream);
            //BufferedInputStream is = new DataInputStream(inStream);
            //InputStreamReader is = new InputStreamReader(inStream,"UTF-8");
            int ii[]=new int[4];
            for(int i=0;i<4;i++)
                ii[i]=is.read(); 
            
            Map<String,Literal> flags = getListFlag(is);
            Abstract abs = getAbstract(is);
            Concrete[] concretes = getListConcretes(is);
            PGF pgf = new PGF(makeInt16(ii[0],ii[1]), 
                              makeInt16(ii[2],ii[3]), 
                              flags, abs, concretes);
            log.finest("The resulting PGF is : "+ pgf.toString());
            log.fine("the end");
            is.close();
            return pgf;
    }

  
  
  /**
   * This simple main program looks after filenames and opening files and such
   * like for you.
   */
  public static void main(String[] av) 
  {
   NewReader o = new NewReader();
   try {
	   o.process(new FileInputStream("..\\testsPGF\\Foods.pgf"));
       } catch (Exception e) {
          System.err.println(e);
        }
    }
  

  
// protected Flag getFlag(DataInputStream is) throws IOException
// { String ss = getString(is);
//   Literal lit = getLiteral(is);
//   Flag ff = new Flag(ss,lit);
//   return ff;
// }
  
    protected Map<String,Literal> getListFlag(DataInputStream is) 
        throws IOException
    {
        int npoz = getInteger(is);
        Map<String,Literal> flags = new HashMap<String,Literal>();
        if (npoz == 0) 
            return flags;
        for (int i=0; i<npoz; i++) {
            String ss = getString(is);
            Literal lit = getLiteral(is);
            flags.put(ss, lit);
        }
        return flags;
    }
 
 protected String getString(DataInputStream is) throws IOException
 { int npoz = getInteger(is);
   byte[] bytes;
   int r ;
   String letter="";
   int lg = 0; 
   StringBuffer bf = new StringBuffer();
   for (int i=0; i<npoz; i++)
   {r = is.read();
    if(r <= 0x7f) lg = 0;
    else if ((r >= 0xc0) && (r <= 0xdf)) 
                 lg = 1;
    	 /*      
    {bytes = new byte[2];
           bytes[0] = (byte) r; 
           bytes[1] = (byte) is.read();
           letter = new String(bytes, "UTF-8");
           } */
    else if ((r >= 0xe0) && (r <= 0xef))
    	  lg = 2;
    else if ((r >= 0xf0) && (r <= 0xf4))
    	  lg = 3;
    else if ((r >= 0xf8) && (r <= 0xfb))
    	  lg = 4;
    else if ((r >= 0xfc) && (r <= 0xfd))
          lg =5;    	
    else throw new IOException("Undefined for now !!! ");
    bytes = new byte[1 + lg];
    bytes[0] = (byte) r;
    for(int j=1; j<=lg; j++)
    	bytes[j] = (byte) is.read();
    letter = new String(bytes,"UTF-8");
    bf.append(letter);
   }
   return new String(bf.toString().getBytes(),"UTF-8");
 }
 
 protected Literal getLiteral(DataInputStream is) throws IOException
 {int sel = is.read();
Literal ss = null;
switch (sel) 
{case 0 : String str = getString(is);
          ss = new StringLiteral(str); 
          break;
 case 1 : int i = getInteger(is);
          ss = new IntLiteral(i);
          break;
 case 2 : double d = is.readDouble();
          ss = new FloatLiteral(d);
          break;
 default : throw new IOException("Incorrect literal tag "+sel); 
 }
ss.sel=sel;
return ss;
}

 
 protected int getInteger(DataInputStream is) throws IOException
 {/*
	 int res = 0;
	 int x = 0;
	 int i = 0;
	 do {
		 x = is.read();
		 res |= x << (i++)*7;
	 } while (x > 0x7F);
	 
	 return res; */
	long rez = (long) is.read();
	if (rez <= 0x7f) return (int)rez;
	else { int ii = getInteger(is);
		   rez = (ii <<7) | (rez & 0x7f);
	       return (int) rez;   
	   }
	   
 }
 
protected Type getType(DataInputStream is) throws IOException
{
 Hypo[] hypos = getListHypo(is);
 String s = getString(is);
 Expr[] exprs = getListExpr(is);
 return new Type(hypos, s,exprs);

}
 
protected Hypo getHypo(DataInputStream is) throws IOException	 
{ int btype = is.read();
  boolean b = btype == 0 ? false : true;
  String s = getString(is);
  Type t = getType(is);
  Hypo hh = new Hypo (b,s,t);
  return hh;
}
 
protected Hypo[] getListHypo(DataInputStream is) throws IOException
{int npoz = getInteger(is);
 Hypo[] hypos = new Hypo[npoz]; 
 for (int i=0; i<npoz; i++)
	 hypos[i]=getHypo(is);
return hypos;	
}
    
protected Expr[] getListExpr(DataInputStream is) throws IOException
{int npoz = getInteger(is);
 Expr[] exprs = new Expr[npoz]; 
 for(int i=0; i<npoz; i++)
	 exprs[i]=getExpr(is);
 return exprs;
 }
	 
protected Expr getExpr(DataInputStream is) throws IOException
{int sel = is.read();
 Expr expr = null;
 switch (sel) {
 case 0 : //lambda abstraction
           int bt = is.read(); 
	       boolean btype = bt == 0 ? false : true ;
           String str = getString(is);
           Expr e1 = getExpr(is);
           expr = new LambdaExp(btype,str,e1);
           break;
 case 1 : //expression application
           Expr e11 = getExpr(is);
           Expr e2 = getExpr(is);
           expr = new AppExp(e11,e2);
           break;
 case 2 : //literal expression
           Literal lit = getLiteral(is);
           expr = new LiteralExp(lit);
           break;
 case 3 : //meta variable
	       int id = getInteger(is);
	       expr = new MetaExp(id);
	       break;
 case 4 : //abstract function name
	       String s = getString(is);
	       expr = new AbsNameExp(s);
	       break;
 case 5 : //variable	       
          int v = getInteger(is);
          expr = new VarExp(v);
          break;
 case 6 : //type annotated expression
	      Expr e = getExpr(is);
	      Type t = getType(is);
	      expr = new TypedExp(e,t);
	      break;
 case 7 : //implicit argument
	      Expr ee = getExpr(is);
          expr = new ImplExp(ee);
          break;
 default : throw new IOException("invalid tag for expressions : "+sel);
 }
 expr.sel=sel;        
 return expr;
	}


protected Eq[] getListEq(DataInputStream is) throws IOException
{int npoz = getInteger(is);
 Eq[] eqs = new Eq[npoz];
 for (int i=0; i<npoz;i++)
	   eqs[i]=getEq(is);
 return eqs;
 }

protected Pattern getPattern(DataInputStream is) throws IOException
{int sel = is.read();
Pattern patt = null;
switch (sel) {
case 0 : //application pattern
          String str = getString(is);
          Pattern[] patts = getListPattern(is);
          patt = new AppPattern(str,patts);
          break;
case 1 : //variable pattern
          String s = getString(is);
          patt = new VarPattern(s);
          break;
case 2 : //variable as pattern 
          String sp = getString(is);
          Pattern p = getPattern(is);
          patt = new VarAsPattern(sp,p);
          break;
case 3 : //wild card pattern
	      patt = new WildCardPattern();
	      break;
case 4 : //literal pattern
	      Literal lit = getLiteral(is);
	      patt = new LiteralPattern(lit);
	      break;
case 5 : //implicit argument
          Pattern pp = getPattern(is);     
          patt = new ImpArgPattern(pp);
case 6 : //inaccessible pattern
	      Expr e = getExpr(is);
	      patt = new InaccPattern(e);
	      break;
default : throw new IOException("invalid tag for patterns : "+sel);
}
patt.sel = sel;
return patt;
}

protected Pattern[] getListPattern(DataInputStream is) throws IOException
{int npoz = getInteger(is); 
 Pattern[] patts = new Pattern[npoz];
 for(int i=0; i<npoz; i++)
	  patts[i]=getPattern(is);
return patts;	
}

protected Eq getEq(DataInputStream is) throws IOException
{Pattern[] patts = getListPattern(is);
 Expr exp = getExpr(is);
 return new Eq(patts,exp);
}



protected AbsFun getAbsFun(DataInputStream is) throws IOException
{String s = getString(is);
 Type t = getType(is);
 int i = getInteger(is);
 int maybe = is.read();
 if (maybe == 0) return new AbsFun(s,t,i,new Eq[0]);
 Eq[] eqs = getListEq(is);
 return new AbsFun(s,t,i,eqs);
}

protected AbsCat getAbsCat(DataInputStream is) throws IOException
{
String s = getString(is);
Hypo[] hypos = getListHypo(is);
String[] strs = getListString(is);
AbsCat abcC = new AbsCat(s,hypos, strs);
return abcC;
}

protected String[] getListString(DataInputStream is) throws IOException
{int npoz = getInteger(is);
String[] strs = new String[npoz];
 if(npoz == 0)
        return strs;
 else {for (int i=0; i<npoz; i++)
	   strs[i] = getString(is);
	   }	
  return strs;
}


protected AbsFun[] getListAbsFun(DataInputStream is) throws IOException
{int npoz = getInteger(is);
 AbsFun[] absFuns = new AbsFun[npoz];
 
 if(npoz == 0) 
    return absFuns;
    
else for (int i=0; i<npoz; i++)
      absFuns[i] = getAbsFun(is);
     
return absFuns;
}

protected AbsCat[] getListAbsCat(DataInputStream is) throws IOException
{int npoz = getInteger(is);
AbsCat[] absCats = new AbsCat[npoz];
if(npoz == 0) 
    return absCats;
else  
	for (int i=0; i<npoz; i++)
      absCats[i] = getAbsCat(is);
     
 return absCats;
}

protected Concrete[] getListConcretes(DataInputStream is) throws IOException
{int npoz = getInteger(is);
 Concrete[] concretes = new Concrete[npoz];
 if(npoz == 0) return concretes;
 else 
	  for (int i=0; i<npoz; i++)
	   concretes[i] = getConcrete(is);
 return concretes;    
}


    protected Abstract getAbstract(DataInputStream is) throws IOException
    {
        String s = getString(is);
        Map<String,Literal> flags = getListFlag(is);
        AbsFun[] absFuns = getListAbsFun(is);
        AbsCat[] absCats = getListAbsCat(is);
        return new Abstract(s,flags,absFuns,absCats);
    }


    protected Concrete getConcrete(DataInputStream is) throws IOException
    {
        String name = getString(is);
        Map<String,Literal> flags = getListFlag(is);
        PrintName[] pnames = getListPrintName(is); 	
        Sequence[] seqs = getListSequence(is); 	
        CncFun[] cncFuns = getListCncFun(is, seqs);
        ProductionSet[] prods = getListProductionSet(is, cncFuns); 	
        CncCat[] cncCats = getListCncCat(is);
        int i = getInteger(is);
        return new Concrete(name,flags,pnames,seqs,cncFuns,prods,cncCats,i);
    }

protected PrintName getPrintName(DataInputStream is) throws IOException
{String absName = getString(is);
 String printName = getString(is);
 return new PrintName(absName, printName);
 
}

protected PrintName[] getListPrintName(DataInputStream is) throws IOException
{int npoz = getInteger(is);
PrintName[] pnames = new PrintName[npoz];
if(npoz == 0) return pnames;
else 
	  for (int i=0; i<npoz; i++)
        pnames[i] = getPrintName(is);	  
return pnames;    
}

protected Sequence getSequence(DataInputStream is) throws IOException
{Symbol[] symbols = getListSymbol(is);
 return new Sequence(symbols);
}

protected Sequence[] getListSequence(DataInputStream is) throws IOException
{int npoz = getInteger(is);
Sequence[] seqs = new Sequence[npoz];
for(int i=0; i<npoz; i++)
	seqs[i]=getSequence(is);
return seqs;	
}

protected Symbol[] getListSymbol(DataInputStream is) throws IOException
{int npoz = getInteger(is);
Symbol[] symbols = new Symbol[npoz];
for(int i=0; i<npoz; i++)
  symbols[i]=getSymbol(is);
return symbols;}
 
    /** **********************************************************************
     * Concrete functions (Cncfun)
     */   
    protected CncFun[] getListCncFun(DataInputStream is, Sequence[] sequences) 
        throws IOException
    {
        int npoz = getInteger(is);
        CncFun[] cncFuns = new CncFun[npoz];
        for(int i=0; i<npoz; i++)
            cncFuns[i]=getCncFun(is, sequences);
        return cncFuns;
    }
    
    /** **********************************************************************
     * Productions and production sets
     */
    /**
     * Read a production set
     * @param is is the input stream to read from
     * @param cncFuns is the list of concrete function
     */
    protected ProductionSet getProductionSet(DataInputStream is, 
					     CncFun[] cncFuns) 
	throws IOException 
    {
	int id = getInteger(is);
	Production[] prods = getListProduction(is, id, cncFuns);
	ProductionSet ps = new ProductionSet(id,prods);
	return ps;
    }

    /**
     * Read a list of production set
     * @param is is the input stream to read from
     * @param cncFuns is the list of concrete function
     */
    protected ProductionSet[] getListProductionSet(DataInputStream is, 
						   CncFun[] cncFuns) 
	throws IOException
    {
	int npoz = getInteger(is);
	ProductionSet[] prods = new ProductionSet[npoz];
	for(int i=0; i<npoz; i++)
	    prods[i]= getProductionSet(is, cncFuns);
	return prods;
    }

    /**
     * Read a list of production
     * @param is is the input stream to read from
     * @param leftCat is the left hand side category of this production (
     * read only once for the whole production set)
     * @param cncFuns is the list of concrete function
     */
    protected Production[] getListProduction(DataInputStream is,
					     int leftCat,
					     CncFun[] cncFuns) 
	throws IOException
    {
	int npoz = getInteger(is);
	Production[] prods = new Production[npoz];
	for(int i=0; i<npoz; i++)
	    prods[i]=getProduction(is, leftCat, cncFuns);
	return prods;
    }

    /**
     * Read a list of production
     * @param is is the input stream to read from
     * @param leftCat is the left hand side category of this production (
     * read only once for the whole production set)
     * @param cncFuns is the list of concrete function, used here to set the
     * function of the production (only given by its index in the list)
     */
    protected Production getProduction(DataInputStream is,
				       int leftCat,
				       CncFun[] cncFuns) throws IOException
    {
	int sel = is.read();
	Production prod = null;
	switch (sel) {
	case 0 : //application 
	    int i = getInteger(is);
	    int[] iis = getListInteger(is);
	    prod = new ApplProduction(leftCat, cncFuns[i],iis);
	    break;
	case 1 : //coercion
	    int id = getInteger(is);
	    prod = new CoerceProduction(leftCat, id);
	    break;
	default : throw new IOException("invalid tag for productions : "+sel);
	}
	return prod;
    }
    
    
protected CncCat[] getListCncCat(DataInputStream is) throws IOException
{int npoz = getInteger(is);
CncCat[] cncCats = new CncCat[npoz];
for(int i=0; i<npoz; i++)
	cncCats[i]=getCncCat(is);
return cncCats;
}

protected CncCat getCncCat(DataInputStream is) throws IOException
{String sname = getString(is);
 int firstFId = getInteger(is);
 int lastFId = getInteger(is);
 String[] ss = getListString(is);
 return new CncCat(sname,firstFId,lastFId,ss);
}


    protected CncFun getCncFun(DataInputStream is, Sequence[] sequences) 
        throws IOException
    {
        String name = getString(is);
        int[] sIndices = getListInteger(is);
        int l = sIndices.length;
        Sequence[] seqs = new Sequence[l];
        for (int i = 0 ; i < l ; i++)
            seqs[i] = sequences[sIndices[i]];
        return new CncFun(name,seqs);
    }

protected Symbol getSymbol(DataInputStream is) throws IOException
{int sel = is.read();
Symbol symb = null;
switch (sel) {
case 0 : //constituent argument 
          int i1 = getInteger(is);
          int i2 = getInteger(is);
          symb = new ArgConstSymbol(i1,i2);
          break;
case 1 : //constituent argument -- what is the difference ?
		  int i11 = getInteger(is);
		  int i12 = getInteger(is);
		  symb = new ArgConstSymbol(i11,i12);
		  break;
case 2 : //sequence of tokens
		  String[] strs = getListString(is);
		  symb = new ToksSymbol(strs);
		  break;
case 3 : //alternative tokens
	      String[] altstrs = getListString(is);
	      Alternative[] as = getListAlternative(is);
          symb = new AlternToksSymbol(altstrs,as);
          break;
default : throw new IOException("invalid tag for symbols : "+sel);
}
symb.sel = sel;
return symb;
}

protected Alternative[] getListAlternative(DataInputStream is) throws IOException
{int npoz = getInteger(is);
Alternative[] alts = new Alternative[npoz];
for(int i=0;i<npoz;i++)
   alts[i] = getAlternative(is);
return alts;
}

protected Alternative getAlternative(DataInputStream is) throws IOException
{String[] s1 = getListString(is);
String[] s2 = getListString(is);
return new Alternative(s1,s2);
}

protected int[] getListInteger(DataInputStream is) throws IOException
{int npoz = getInteger(is);
int[] vec = new int[npoz];
for(int i=0; i<npoz; i++)
  vec[i] = getInteger(is);
return vec;
}






}





