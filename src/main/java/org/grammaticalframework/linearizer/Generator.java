package org.grammaticalframework.linearizer;

import org.grammaticalframework.Trees.Absyn.*;
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


/** generates a random expression of a given category
 * does not handle dependent categories or categories with implicit arguments
**/
	
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
     dirRules.put(absCats[i].name(),dirFuns);
     indirRules.put(absCats[i].name(), indirFuns);
}}
	
	
/** generates a category with a random direct rule
 * suitable for simple expressions
**/
public Tree getDirect(String type, HashSet<String> dirFuns)
{Iterator<String> it = dirFuns.iterator();
Vector<String> vs = new Vector<String>();
while(it.hasNext())
    vs.add(it.next());
int rand = random.nextInt(vs.size()); 
return new Function(vs.elementAt(rand));	     	
}

	
/** generates a category with a random indirect rule
 * creates more complex expressions
**/
public Tree getIndirect(String type, HashSet<String> indirFuns) throws Exception
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
	     Tree[] exps = new Tree[hypos.length];
	     for(int k=0; k<hypos.length;k++)
	    	   {tempCats[k]=hypos[k].getType().getName();
	    	    exps[k]=gen(tempCats[k]);
	    	    if(exps[k] == null) return null;}
	     Tree rez = new Function(funcName);
	     for(int j=0;j<exps.length;j++)
	    	   rez = new Application (rez, exps[j]);
	     return rez;}
return null;
}

	
/** generates a random expression of a given category
 * the empirical probability of using direct rules is 60%
 * this decreases the probability of having infinite trees for infinite grammars
**/
public Tree gen(String type) throws Exception
{if(type.equals("Integer"))   return new Literal(new IntLiteral(generateInt()));
    else if(type.equals("Float"))   return new Literal(new FloatLiteral(generateFloat()));
     else if(type.equals("String"))  return new Literal(new StringLiteral(generateString()));
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
	
	
/** generates a number of expressions of a given category
 * the expressions are independent
 * the probability of having simple expressions is higher
**/
public Vector<Tree> generateMany(String type, int count) throws Exception
{int contor = 0;
Vector<Tree> rez = new Vector<Tree>();
if(contor >= count) return rez;
HashSet<String> dirFuns = dirRules.get(type);
HashSet<String> indirFuns = indirRules.get(type);
Iterator<String> itDir = dirFuns.iterator();
while(itDir.hasNext())
   {Tree interm = getDirect(type,dirFuns);
    if(interm != null) 
        {contor ++;
         rez.add(interm);
         if(contor == count) return rez;}}
    itDir = indirFuns.iterator();
    while(itDir.hasNext())
	{Tree interm = getIndirect(type,indirFuns);
	 if(interm != null) 
             {contor ++;
              rez.add(interm);
              if(contor == count) return rez;}}
return rez;	
}

/** generates a random string
**/
	
public String generateString() 
{String[] ss = { "x", "y", "foo", "bar" };
return ss[random.nextInt(ss.length)];}


/** generates a random integer
**/
public int generateInt() 
{return random.nextInt(100000);}

/** generates a random float
**/
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
