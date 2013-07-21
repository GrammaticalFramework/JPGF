/* RReader.java
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
package org.grammaticalframework.reader;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;


public class RReader {

  protected Vector toks = new Vector ();

  protected boolean isStringChar(char ch) {
    if (ch >= 'a' && ch <= 'z')
      return true;
    if (ch >= 'A' && ch <= 'Z')
      return true;
    if (ch >= '0' && ch <= '9')
      return true;
    switch (ch) {
    case '/':
    case '-':
    case ':':
    case '.':
    case ',':
    case '_':
    case '$':
    case '%':
    case '\'':
    case '(':
    case ')':
    case '[':
    case ']':
    case '<':
    case '>':
      return true;
    }
    return false;
  }

  protected int makeInt16(byte[] b)
  {int i = 0;
   i |= b[0] & 0xFF;
   i <<= 8;
   i |= b[1] & 0xFF;
   return i;  }
  
  protected void process(String fileName, InputStream inStream) {
    try {
      int i;
      char ch;
      BufferedInputStream is = new BufferedInputStream(inStream);
      InputStreamReader isr = new InputStreamReader(inStream,"UTF-8");
      //isr.
      StringBuffer sb = new StringBuffer();
 
      getVersion(is);
      getFlags(is);
      getAbstract(is);
      is.close();
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }

  
  
  /**
   * This simple main program looks after filenames and opening files and such
   * like for you.
   */
  public static void main(String[] av) {
    RReader o = new RReader();
   try {
	   o.process("", new FileInputStream("Foods.pgf"));
      // o.processTokens();
       } catch (Exception e) {
          System.err.println(e);
        }
    }
  

  /** Output a match. Made a separate method for use by subclasses. */
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
  
 protected void getFlags(BufferedInputStream is) throws IOException
 {  int npoz = is.read();
    if (npoz == 0) {System.out.println("No flags");
                   return ;}
    for (int i=0; i<npoz; i++)
      {System.out.println("Flag : (");
       getString(is);
       System.out.println("--");
       getLiteral(is);
       System.out.println(")");
      }
 }
 
 protected void getString(BufferedInputStream is) throws IOException
 { int npoz = is.read();
   char ch;
   StringBuffer bf = new StringBuffer();
   for (int i=0; i<npoz; i++)
   {ch = (char) is.read();     
    bf.append(ch);
   }
  System.out.println("" +bf.toString());
 }
 
 protected void getLiteral(BufferedInputStream is) throws IOException
 {int sel = is.read();
 if (sel == 0)
             {System.out.print("String Literal ");
              getString(is);
              //ss = new StringLiteral(str); 
              return ;
              }
 if (sel == 1)
             {System.out.print("Integer Literal ");
              getInt(is);
              //ss = new IntLiteral(i);
              return;
             }
 if (sel == 2)
             {System.out.print("Float Literal "); //////not yet implemented !!!!
             throw new IOException("Float literal not yet defined ! ");
             }
else {System.out.println("Incorrect literal tag "+sel);
       throw new IOException();}
 }
  
protected void getInt(BufferedInputStream is) throws IOException
{byte[] i1 = new byte[2];
is.read(i1, 0, 2);
System.out.println(""+makeInt16(i1));
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

protected void getType(BufferedInputStream is) throws IOException
{System.out.println("Type : ");
 getListHypo(is);
 getString(is);
 getListExpr(is);
	}
 
protected void getHypo(BufferedInputStream is) throws IOException	 
{ int btype = is.read();
  System.out.println("Hypothesis : ( "+ btype);
  getString(is);
  getType(is);
  System.out.println(")");
}
 
protected void getListHypo(BufferedInputStream is) throws IOException
{int npoz = is.read();
 if (npoz == 0) {
	             System.out.println("No hypothesis"); 
                 return;
                 }
 else {for (int i=0; i<npoz; i++)
	     getHypo(is);}
	}
    
protected void getListExpr(BufferedInputStream is) throws IOException
{int npoz = is.read();
 if (npoz == 0) {
	             System.out.println("No expressions");
	             return ;
                }
 else {System.out.println("Not implemented for more expressions !");
       throw new IOException();}
 }
	 

protected void getListEq(BufferedInputStream is) throws IOException
{int npoz = is.read();
 if (npoz == 0) {
	             System.out.println("No equations");
	             return; 
                 }
 else {System.out.println("Not implemented for more expressions !");
 throw new IOException();}
}

protected void getAbsFun(BufferedInputStream is) throws IOException
{System.out.println("Abstract Function : (");
getString(is);
getType(is);
getInt(is);
getListEq(is);
System.out.println(")");
}

protected void getAbsCat(BufferedInputStream is) throws IOException
{System.out.println("Abstract Category : (");
getString(is);
getListHypo(is);
System.out.println("Strings associated : ");
getListString(is);
System.out.println(")");
}

protected void getListString(BufferedInputStream is) throws IOException
{int npoz = is.read();
 if(npoz == 0) {
	            System.out.println("No strings");
                return ;
                }
 else {for (int i=0; i<npoz; i++)
	    getString(is);
	   }	
}


protected void getListAbsFun(BufferedInputStream is) throws IOException
{int npoz = is.read();
if(npoz == 0) {
    System.out.println("No strings");
    return ;
    }
else {for (int i=0; i<npoz; i++)
      getAbsFun(is);
     }
}

protected void getListAbsCat(BufferedInputStream is) throws IOException
{int npoz = is.read();
if(npoz == 0) {
    System.out.println("No strings");
    return ;
    }
else { System.out.println("Number of elements : "+npoz);
	for (int i=0; i<npoz; i++)
      getAbsCat(is);
     }

}

protected void getAbstract(BufferedInputStream is) throws IOException
{System.out.println("Abstract :");
 getString(is);
 getFlags(is);
 getListAbsFun(is);
 getListAbsCat(is);
}



}





