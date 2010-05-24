package org.grammaticalframework.reader;
import java.io.*;



public class Reader {

static int readPGFInt() throws IOException
{ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.pgf"));
int rez = ois.read();
int ii = 0;
if (rez <= 0x7f) ; 
else {while(true)
         {ii = ois.read();
          rez = (ii << 7) | (rez & 0x7f);
          if(ii <= 0x7f) return rez; 	 
         }

      }
return rez;	
}

static int readSimplePGF() throws IOException
{FileInputStream ois = new FileInputStream("Foods.pgf");
byte buf[] = new byte[ois.available()];
ois.read(buf);
int rez = 1;
for (int i = 0; i< buf.length; i++)
if (buf[i] <= 0x7f) System.out.print(" "+buf[i]);
return 1;
}

public static void main(String[] args)
{/*try{
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.pgf"));
oos.write(149);
oos.write(25);
oos.close();
}
catch(IOException e)
{System.out.println("Eroare la scriere"+e);}
*/
try{
int rez = readSimplePGF();
System.out.println("Rez este : "+ rez);
      
}
catch(IOException e)
{System.out.println("IO exception!"+e);}
	}
}
