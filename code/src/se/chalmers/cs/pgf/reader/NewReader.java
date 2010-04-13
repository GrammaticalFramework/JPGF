package reader;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class NewReader {

  protected Vector toks = new Vector ();
  
  protected int makeInt16(byte[] b)
  {int i = 0;
   i |= b[0] & 0xFF;
   i <<= 8;
   i |= b[1] & 0xFF;
   return i;  }
  
  protected void process(String fileName, InputStream inStream) {
   try {
      BufferedInputStream is = new BufferedInputStream(inStream);
      getVersion(is);
      Flag[] flags = getFlags(is);
      Abstract abs = getAbstract(is);
      is.close();
        } 
    catch (IOException e) {
      System.out.println("IOException: " + e);
       }
  }

  
  
  /**
   * This simple main program looks after filenames and opening files and such
   * like for you.
   */
  public static void main(String[] av) 
  {
   NewReader o = new NewReader();
   try {
	   o.process("Foods.pgf", new FileInputStream("Foods.pgf"));
       } catch (Exception e) {
          System.err.println(e);
        }
    }
  

  /** Output a match. Made a separate method for use by subclassers. */
  protected void report(String fName, StringBuffer theString) {
    System.out.println(theString);
  }
  
  
protected void getVersion(BufferedInputStream is) throws IOException
{ 
 byte[] i1 = new byte[2];
 is.read(i1, 0, 2);
 byte[] i2 = new byte[2];
 is.read(i2,0,2);
 System.out.println("Major version : "+ makeInt16(i1));
 System.out.println("Minor version : "+ makeInt16(i2));	
}

protected Flag getFlag(BufferedInputStream is) throws IOException
{ String ss = getString(is);
  Literal lit = getLiteral(is);
  Flag ff = new Flag(ss,lit);
  return ff;
}
  
 protected Flag[] getFlags(BufferedInputStream is) throws IOException
 {int npoz = is.read();
    Flag[] vec = new Flag[npoz];
    if (npoz == 0) {System.out.println("No flags");
                   return vec;}
    for (int i=0; i<npoz; i++)
      vec[i] = getFlag(is);
  return vec;   
 }
 
 protected String getString(BufferedInputStream is) throws IOException
 { int npoz = is.read();
   char ch;
   StringBuffer bf = new StringBuffer();
   for (int i=0; i<npoz; i++)
   {ch = (char) is.read();     
    bf.append(ch);
   }
 return bf.toString();
 }
 
 protected Literal getLiteral(BufferedInputStream is) throws IOException
 {int sel = is.read();
Literal ss = null;
 if (sel == 0)
             {//System.out.print("String Literal ");
              String str = getString(is);
              ss = new StringLiteral(str); 
              ss.sel = sel;
              //return ;
              }
 if (sel == 1)
             {//System.out.print("Integer Literal ");
              int i = getInt(is);
              ss = new IntLiteral(i);
              ss.sel = sel;
              //return;
             }
 if (sel == 2)
             {System.out.print("Float Literal "); //////not yet implemented !!!!
              ss.sel = sel;
             //return;
             }

System.out.println(ss);
return ss;
//System.out.println("Incorrect literal tag "+sel);
//throw new IOException();
 }
  
protected int getInt(BufferedInputStream is) throws IOException
{byte[] i1 = new byte[2];
is.read(i1, 0, 2);
return makeInt16(i1);
	}
 
 protected int getInteger(BufferedInputStream is) throws IOException
 {int rez = is.read();
  int ii = 0;
  if (rez <= 0x7f) ; 
 else {while(true)
          {ii = is.read();
           rez = (ii << 7) | (rez & 0x7f);
           if(ii <= 0x7f) break; 	 
          }
       }
return rez;
 }

protected Type getType(BufferedInputStream is) throws IOException
{
 Hypo[] hypos = getListHypo(is);
 String s = getString(is);
 Expr[] exprs = getListExpr(is);
 return new Type(hypos, s,exprs);

}
 
protected Hypo getHypo(BufferedInputStream is) throws IOException	 
{ int btype = is.read();
  boolean b = btype == 0 ? false : true;
  String s = getString(is);
  Type t = getType(is);
  return new Hypo (b,s,t);
}
 
protected Hypo[] getListHypo(BufferedInputStream is) throws IOException
{int npoz = is.read();
 Hypo[] hypos = new Hypo[npoz]; 
 if (npoz == 0) {
	             System.out.println("No hypothesis"); 
                 return hypos;
                 }
 else {for (int i=0; i<npoz; i++)
	    hypos[i] = getHypo(is);}
return hypos;	
}
    
protected Expr[] getListExpr(BufferedInputStream is) throws IOException
{int npoz = is.read();
 Expr[] exprs = new Expr[npoz]; 
if (npoz == 0) {
	             System.out.println("No expressions");
	             return exprs;
                }
 else {System.out.println("Not implemented for more expressions !");
       throw new IOException();}
 }
	 

protected Eq[] getListEq(BufferedInputStream is) throws IOException
{int npoz = is.read();
 Eq[] eqs = new Eq[npoz];
 if (npoz == 0) {
	             System.out.println("No equations");
	             return eqs; 
                 }
 else {System.out.println("Not implemented for more expressions !");
 throw new IOException();}
}

protected AbsFun getAbsFun(BufferedInputStream is) throws IOException
{String s = getString(is);
 Type t = getType(is);
 int i = getInt(is);
 Eq[] eqs = getListEq(is);
 return new AbsFun(s,t,i,eqs);
}

protected AbsCat getAbsCat(BufferedInputStream is) throws IOException
{
String s = getString(is);
Hypo[] hypos = getListHypo(is);
String[] strs = new String[0]; // for the moment !
//getListString(is);
return new AbsCat(s,hypos, strs);
}

protected String[] getListString(BufferedInputStream is) throws IOException
{int npoz = is.read();
String[] strs = new String[npoz];
 if(npoz == 0) {
	            System.out.println("No strings");
                return strs;
                }
 else {for (int i=0; i<npoz; i++)
	   strs[i] = getString(is);
	   }	
  return strs;
}


protected AbsFun[] getListAbsFun(BufferedInputStream is) throws IOException
{int npoz = is.read();
 AbsFun[] absFuns = new AbsFun[npoz];
if(npoz == 0) {
    System.out.println("No strings");
    return absFuns;
    }
else {for (int i=0; i<npoz; i++)
      absFuns[i] = getAbsFun(is);
     }
return absFuns;
}

protected AbsCat[] getListAbsCat(BufferedInputStream is) throws IOException
{int npoz = is.read();
AbsCat[] absCats = new AbsCat[npoz];
if(npoz == 0) {
    System.out.println("No strings");
    return absCats;
    }
else { //System.out.println("Number of elements : "+npoz);
	for (int i=0; i<npoz; i++)
      absCats[i] = getAbsCat(is);
     }
 return absCats;
}

protected Abstract getAbstract(BufferedInputStream is) throws IOException
{
 String s = getString(is);
 Flag[] flags = getFlags(is);
 AbsFun[] absFuns = getListAbsFun(is);
 AbsCat[] absCats = getListAbsCat(is);
 return new Abstract(s,flags,absFuns,absCats);
}



}





