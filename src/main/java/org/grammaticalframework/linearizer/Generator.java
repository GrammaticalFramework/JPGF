package org.grammaticalframework.linearizer;
import org.grammaticalframework.reader.*;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class Generator {

	private Random random;
	private PGF pgf;
	private HashMap<String,HashSet<String>> dirRules;
	private HashMap<String,HashSet<String>> indirRules;

	
public Generator(String file, PGF _pgf) throws Exception
{random = new Random();
pgf = _pgf;
dirRules = new HashMap<String,HashSet<String>>();
indirRules = new HashMap<String,HashSet<String>>();
AbsCat[] absCats = pgf.getAbstract().getAbsCats();
AbsFun[] absFuns = pgf.getAbstract().getAbsFuns();
HashSet<String> dirFuns = new HashSet<String>();
HashSet<String> indirFuns = new HashSet<String>();
for(int i=0;i<absCats.length; i++)
    {dirFuns = new HashSet<String>(); 
     indirFuns = new HashSet<String>();
     String[] strs = absCats[i].getFunctions();
     for(int j=0; j<strs.length; j++)
        for(int k=0; k<absFuns.length; k++)
           if(strs[j].equals(absFuns[k].getName()))
            	{if(absFuns[k].getType().getHypos().length == 0)
                       dirFuns.add(strs[j]);
          	  else indirFuns.add(strs[j]);
       		 break;}
     dirRules.put(absCats[i].getName(),dirFuns);
     indirRules.put(absCats[i].getName(), indirFuns);
}}
	
	
public Expr getDirect(String type, HashSet<String> dirFuns)
{Iterator<String> it = dirFuns.iterator();
Vector<String> vs = new Vector<String>();
while(it.hasNext())
    vs.add(it.next());
int rand = random.nextInt(vs.size()); 
return new AbsNameExp(vs.elementAt(rand));	     	
}

	
public Expr getIndirect(String type, HashSet<String> indirFuns) throws Exception
{Iterator<String> it = indirFuns.iterator();
Vector<String> vs = new Vector<String>();
while(it.hasNext())
      vs.add(it.next()); 
int rand = random.nextInt(vs.size());
String funcName = vs.elementAt(rand);
AbsFun[] absFuns = pgf.getAbstract().getAbsFuns();
for(int i=0; i<absFuns.length;i++)
      if(absFuns[i].getName().equals(funcName))
	    {Hypo[] hypos = absFuns[i].getType().getHypos();
	     String[] tempCats = new String[hypos.length];
	     Expr[] exps = new Expr[hypos.length];
	     for(int k=0; k<hypos.length;k++)
	    	   {tempCats[k]=hypos[k].getType().getName();
	    	    exps[k]=gen(tempCats[k]);
	    	    if(exps[k] == null) return null;}
	     Expr rez = new AbsNameExp(funcName);
	     for(int j=0;j<exps.length;j++)
	    	   rez = new AppExp (rez, exps[j]);
	     return rez;}
return null;
}

	
public Expr gen(String type) throws Exception
{if(type.equals("Integer"))   return new LiteralExp(new IntLiteral(generateInt()));
    else if(type.equals("Float"))   return new LiteralExp(new FloatLiteral(generateFloat()));
     else if(type.equals("String"))  return new LiteralExp(new StringLiteral(generateString()));
int depth = random.nextInt(5); //60% constants, 40% functions 
HashSet<String> dirFuns = dirRules.get(type);
HashSet<String> indirFuns = indirRules.get(type);
boolean isEmptyDir = dirFuns.isEmpty();
boolean isEmptyIndir = indirFuns.isEmpty();
if(isEmptyDir && isEmptyIndir) throw new Exception ("Cannot generate any expression of type "+type);	
if(isEmptyDir)   return getIndirect(type,indirFuns);
if(isEmptyIndir)    return getDirect(type,dirFuns);
if(depth <= 2 ) return getDirect(type,dirFuns);
return getIndirect(type,indirFuns);
}
	
	
public Vector<Expr> generateMany(String type, int count) throws Exception
{int contor = 0;
Vector<Expr> rez = new Vector<Expr>();
if(contor >= count) return rez;
HashSet<String> dirFuns = dirRules.get(type);
HashSet<String> indirFuns = indirRules.get(type);
Iterator<String> itDir = dirFuns.iterator();
while(itDir.hasNext())
   {Expr interm = getDirect(type,dirFuns);
    if(interm != null) 
        {contor ++;
         rez.add(interm);
         if(contor == count) return rez;}}
    itDir = indirFuns.iterator();
    while(itDir.hasNext())
	{Expr interm = getIndirect(type,indirFuns);
	 if(interm != null) 
             {contor ++;
              rez.add(interm);
              if(contor == count) return rez;}}
return rez;	
}

	
public String generateString() 
{String[] ss = { "x", "y", "foo", "bar" };
return ss[random.nextInt(ss.length)];}


public int generateInt() 
{return random.nextInt(100000);}


public double generateFloat()
{return random.nextDouble();}
   
	
	
/*
public static void main(String[] args)
{ try {
       Generator gg = new Generator("Phrasebook.pgf");
       Vector<Expr> rez = gg.generateMany("Greeting",9);
       if(rez != null)
	    { System.out.println("Rezultatul este "+rez.toString());
	    System.out.println("Dimensiunea este "+rez.size());}
	    else System.out.println("Rezultatul este nul");
	}
   catch(Exception e) {System.out.println("No such file"+e.toString());}
}
*/	
		
}
