package org.grammaticalframework.linearizer;
import java.io.FileInputStream;
import java.util.Vector;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.grammaticalframework.reader.*;

public class Linearizer {
	private PGF pgf; 
    private	String lang;
	private Concrete cnc ;
	private Expr expr;
	private HashMap<String,HashMap<Integer,HashSet<Production>>> lProd;

/** linearizes an expression to a bracketed token
 * and further on to a string
 * not implemented to dependent categories, implicit argument, and higher-order abstract syntax     
**/

	
public Linearizer(String file, String _lang, Expr _expr, PGF _pgf) throws Exception
 {
 pgf = _pgf;
 lang = _lang;
 expr = _expr;
 cnc = cnc();
 lProd = getLProductions(); }
	
public HashMap<String,HashMap<Integer,HashSet<Production>>> retLProd()
{return lProd;}

public Expr retExpr()
{return expr;}

public PGF retPGF()
{return pgf;}

public String retLang()
{return lang;}

public Concrete retConcrete()
{return cnc;}

/** returns a concrete syntax if the language was found in the PGF
**/
public Concrete cnc () throws NullPointerException
{HashMap<String, Concrete> concs = pgf.getConcretes();
 if (concs.get(lang) ==null) throw new NullPointerException("No such language "+lang+" !");
  return concs.get(lang);
 }


/** constructs the l-productions of the concrete syntax for a given language
 **/
public HashMap<String,HashMap<Integer,HashSet<Production>>> getLProductions() throws NullPointerException
{HashMap<Integer,HashSet<Production>> emptyMap = new HashMap<Integer,HashSet<Production>>();
	return linIndex(filterProductions(emptyMap, cnc.getSetOfProductions()));}


/** aligns the indexes for the l-productions
**/
public HashMap<String,HashMap<Integer,HashSet<Production>>> linIndex(HashMap<Integer,HashSet<Production>> productions)
{ 	HashMap<String,HashMap<Integer,HashSet<Production>>> vtemp = new HashMap<String,HashMap<Integer,HashSet<Production>>>();
	Iterator<Entry<Integer,HashSet<Production>>> i = productions.entrySet().iterator();
	 while(i.hasNext())
	 {Entry<Integer,HashSet<Production>> entr = (Entry<Integer,HashSet<Production>>) i.next();
	  Integer res = entr.getKey(); 
          Iterator<Production> prods = entr.getValue().iterator();
	  while(prods.hasNext())
	    { Production prod = (Production) prods.next();
		  Vector<String> vs = getFunctions(prod,productions);
	      if (vs != null)
	    	  for(int j=0; j<vs.size(); j++)
	    	  {HashMap<Integer,HashSet<Production>> htemp = new HashMap<Integer,HashSet<Production>>();
	    	   HashSet<Production> hSingleton = new HashSet<Production>();
	    	   hSingleton.add(prod);
	    	   String newl = vs.elementAt(j);
	    	   htemp.put(res, hSingleton);
	    	   if(vtemp.containsKey(newl))
	    		   {HashMap<Integer,HashSet<Production>> obj =vtemp.get(newl);
	    		    if(obj.containsKey(res)) 
	    		        {HashSet<Production> ttemp = obj.get(res);
	    		         ttemp.add(prod);
	    		         obj.remove(res);
	    		         obj.put(res,ttemp);
	    		         vtemp.remove(newl);
	    		         vtemp.put(newl,obj);
	    		        }
	    		    else {obj.put(res, hSingleton);
	    		          vtemp.remove(newl);
	    		          vtemp.put(newl, obj);}}
	    		   else  vtemp.put(newl, htemp);
	        }}}
return vtemp;
}
	
/** returns the abstract functions corresponding to a production
**/
public Vector<String> getFunctions(Production p, HashMap<Integer,HashSet<Production>> productions)
{Vector<String> rez = new Vector<String>();
	if (p instanceof ApplProduction)
		rez.add(((ApplProduction)p).getFunction().name()); 
	else // coercion
	   {Integer fid = new Integer(((CoerceProduction) p).getInitId());
		HashSet<Production> prods = productions.get(fid);
		if (prods == null) return null ;
		else {Iterator<Production> prod = prods.iterator();
		      while(prod.hasNext())
		    	  {Vector<String> vrez = getFunctions((Production)prod.next(),productions);
		    	   if (vrez != null) rez.addAll(vrez);
		    	  }}}
if(rez.isEmpty()) return null;
return rez;  	
}

/** checks if i is the index of a literal or a valid set of productions
**/
boolean conditionProd(int i, HashMap<Integer,HashSet<Production>> prods)
{if (isLiteral(i)) return true;
return prods.containsKey(new Integer(i));
}

/** filters a set of productions according to filterRule
**/
public HashSet<Production> filterProdSet1(HashMap<Integer,HashSet<Production>> prods0, HashSet<Production> set)
{HashSet<Production> set1 = new HashSet<Production>();
 Iterator<Production> it = set.iterator();
 while(it.hasNext()){
     Production pp = (Production)it.next();
     if(filterRule (prods0, pp)) set1.add(pp); }
 return set1;
}

/** filters an IntMap of productions according to filterProdsSet1
**/
public HashMap<Integer,HashSet<Production>> filterProductions(HashMap<Integer,HashSet<Production>> prods0, HashMap<Integer,HashSet<Production>> prods)
{Iterator<Entry<Integer,HashSet<Production>>> it = prods.entrySet().iterator();
HashMap<Integer,HashSet<Production>> tempRez = new HashMap<Integer,HashSet<Production>>();
boolean are_diff = false;
while(it.hasNext())
 {Entry<Integer,HashSet<Production>> entr = it.next();
  Integer index = entr.getKey();
  HashSet<Production> setProd = entr.getValue();
  HashSet<Production> intermRez = filterProdSet1(prods0,setProd);
  if(!intermRez.isEmpty())
             tempRez.put(index, intermRez);
 }
it = tempRez.entrySet().iterator();
HashMap<Integer,HashSet<Production>> prods1 = new HashMap<Integer,HashSet<Production>>();
prods1.putAll(prods0);
while(it.hasNext())
 {Entry<Integer,HashSet<Production>> entr = it.next();
  Integer index = entr.getKey();
  HashSet<Production> hp = entr.getValue();
  if(prods0.containsKey(index)) 
      {if(!prods0.get(index).equals(hp)) {
	                                   hp.addAll(prods0.get(index));
	                                   prods1.put(index,hp); 
	                                   are_diff = true; }
       }
 else {prods1.put(index, hp);
	   are_diff = true;
      }}
if(are_diff) return filterProductions(prods1,prods);
else return prods0;
}

/** checks if a production satisfies conditionProd recursively
**/
public boolean filterRule(HashMap<Integer,HashSet<Production>> prods, Production p)
{if (p instanceof ApplProduction) 
  {ApplProduction ap = (ApplProduction) p;
  for(int i=0; i<ap.getArgs().length; i++)
	 if(!conditionProd(ap.getArgs()[i], prods))  return false;
  return true;
  }
return conditionProd(((CoerceProduction) p).getInitId(),prods);
	}

/** checks if a production just has a variable argument 
**/
public boolean is_ho_prod(Production p)
{if (p instanceof ApplProduction)
   {int[] args = ((ApplProduction) p).getArgs();
    if(args.length == 1 && args[0] == -4) return true;}
 return false;
}


/** gets list of forest ids from the categories in ho_cats
**/
public HashSet<Integer> ho_fids()
{HashSet<Integer> rezTemp = new HashSet<Integer>();
Vector<String> ho_cats = ho_cats();
CncCat[] cncCats = cnc().getCncCat();
for(int i=0; i<ho_cats.size(); i++)
	for(int j=0; j<cncCats.length; j++)
		if (ho_cats.elementAt(i).equals(cncCats[j].getName()))
			for(int ind=cncCats[j].getFirstId(); ind <=cncCats[j].getLastId(); ind++)
				rezTemp.add(new Integer(ind));
return rezTemp;}


/**get all names of types from Concrete
**/
public Vector<String> ho_cats()
{Vector<String> rezTemp = new Vector<String>();
Abstract abstr = pgf.getAbstract();
AbsFun[] absFuns = abstr.getAbsFuns();
for(int i=0; i<absFuns.length; i++)
 {Hypo[] hypos = absFuns[i].getType().getHypos();
 for(int j=0; j<hypos.length; j++)
	 if(!rezTemp.contains(hypos[j].getType().getName()))
		 rezTemp.add(hypos[j].getType().getName());}
return rezTemp;}

/**gets the types from the hypotheses of a type
**/
public String[] hypoArgsOfType(Type t)
{Hypo[] hypos = t.getHypos();
String[] rez = new String[hypos.length];
for(int i=0; i<hypos.length; i++)
rez[i] = hypos[i].getType().getName();
return rez;
}

/** flattens a bracketed token
**/
public Vector<String> untokn(BracketedTokn bt)
{if (bt instanceof LeafKS) 
  {String[] d = ((LeafKS) bt).getStrs();
   Vector<String> rez = new Vector<String>();
   for(int i=0; i<d.length;i++)
	   rez.add(d[i]);
   return rez;}
else if (bt instanceof LeafKP) {
	String[] d = ((LeafKP) bt).getStrs();
	Vector<String> rez = new Vector<String>();
	for(int i = 0; i<d.length; i++)
		rez.add(d[i]);
	return rez;}
else {Vector<String> rez = new Vector<String>();
	 Vector<BracketedTokn> bs = ((Bracket) bt).getBracketedToks();
      for(int i=0; i<bs.size();i++)
    	rez.addAll(untokn(bs.elementAt(i)));
      return rez;}}

/** flattens the result of the linearization
**/
public Vector<String> renderLin(Vector<LinTriple> v)
{Vector<String> rez= new Vector<String>();
for(int i=0; i<v.size();i++)
{Vector<Vector<BracketedTokn>> vtemp = v.elementAt(i).getLinTable();
for(int j=0; j<vtemp.size(); j++)
  for(int k=0; k<vtemp.elementAt(j).size(); k++)
	rez.addAll(untokn(vtemp.elementAt(j).elementAt(k)));		
	}
return rez;}


/** main linearization function
**/
public Vector<LinTriple> lin0(Vector<String> xs, Vector<String> ys, CncType mb_cty, Integer mb_fid, Expr e) throws Exception
{
 if(e instanceof LambdaExp)
	{xs.add(((LambdaExp)e).getVarName());
	return lin0(xs,ys,mb_cty,mb_fid,((LambdaExp)e).getBody());}
 else if(e instanceof TypedExp) {throw new Exception("Not done yet!!");}
 else if(xs.isEmpty()) return lin(ys,mb_cty,mb_fid,e,new Vector<Expr>());
     else {xs.addAll(ys);
           Vector<Expr> exprs = new Vector<Expr>();
           exprs.add(e);
           for(int i=0; i<xs.size(); i++)
        	   exprs.add(new LiteralExp(new StringLiteral(xs.elementAt(i))));
           return apply(xs,mb_cty,mb_fid,"_B",exprs);} 
}


/** intermediate linearization for complex expressions
**/
public Vector<LinTriple> apply(Vector<String> xs, CncType mb_cty, Integer n_fid, String f, Vector<Expr> es) throws Exception
{HashMap<Integer,HashSet<Production>> prods = lProd.get(f);
if (prods == null)
	 {Vector<Expr> newes = new Vector<Expr>();
	  newes.add(new LiteralExp(new StringLiteral(f)));
	  System.out.println("Function "+f+" does not have a linearization !");
	  return apply(xs,mb_cty,n_fid,"_V",newes);
	 }
else {Vector<AppResult> vApp = getApps(prods,mb_cty,f);
      Vector<LinTriple> rez = new Vector<LinTriple>();
      for(int i=0; i<vApp.size();i++)
       {Vector<CncType> copy_ctys = vApp.elementAt(i).getCncTypes();
        Vector<CncType> ctys = new Vector<CncType>();
        for(int ind =copy_ctys.size()-1; ind >=0; ind--)
        	  ctys.add(copy_ctys.elementAt(ind));
    	if (es.size() != ctys.size()) throw new Exception("lengths of es and ctys don't match"+es.toString()+" -- "+ctys.toString());
    	Sequence[] lins = vApp.elementAt(i).getCncFun().sequences();
    	String cat = vApp.elementAt(i).getCncType().getCId();
    	Vector<Expr> copy_expr = new Vector<Expr>();
    	for(int ind = 0; ind<es.size();ind++)
    		copy_expr.add(es.elementAt(ind));
    	Vector<RezDesc> rezDesc = descend(n_fid,ctys,copy_expr,xs);
    	for(int k=0; k<rezDesc.size();k++)
    	{ RezDesc intRez = rezDesc.elementAt(k);
    	  Vector<Vector<BracketedTokn>> linTab = new Vector<Vector<BracketedTokn>>();
    	  for(int ind=0; ind<lins.length; ind++)
    	         linTab.add(computeSeq(lins[ind],intRez.getCncTypes(),intRez.getBracketedTokens()));	
    	   rez.add(new LinTriple(n_fid+1,new CncType(cat,n_fid),linTab)); }
       }
      return rez;
}}



public Vector<AppResult> getApps(HashMap<Integer,HashSet<Production>> prods, CncType mb_cty, String f ) throws Exception
{if (mb_cty == null)
	if (f.equals("_V") || f.equals("_B")) return new Vector<AppResult>();
         else 
	  {Vector<AppResult> rez = new Vector<AppResult>();
	   Iterator<Entry<Integer,HashSet<Production>>> it = prods.entrySet().iterator();
           while(it.hasNext())
	     {Entry<Integer,HashSet<Production>> en = it.next();
	      Integer fid = en.getKey();
	      Iterator<Production> ip = en.getValue().iterator();
              while(ip.hasNext())
                  {Production entr = ip.next();
                   Vector<AppResult> appR = toApp(new CncType("_",fid.intValue()),entr,f,prods);
    	           rez.addAll(appR);}}
              return rez;}
else {int fid = mb_cty.getFId();
      HashSet<Production> setProd = prods.get(new Integer(fid));
      Vector<AppResult> rez = new Vector<AppResult>();
      if (setProd == null) return new Vector<AppResult>();
      else {Iterator<Production> iter = setProd.iterator();
            while(iter.hasNext())
             rez.addAll(toApp(mb_cty,iter.next(),f,prods));
    	    return rez;}}
}


public Vector<AppResult> toApp(CncType cty, Production p, String f, HashMap<Integer,HashSet<Production>> prods) throws Exception
{Vector<AppResult> rez = new Vector<AppResult>();
 if(p instanceof ApplProduction)
     {int[] args = ((ApplProduction)p).getArgs();
      CncFun cncFun = ((ApplProduction)p).getFunction(); 
      Vector<CncType> vtype = new Vector<CncType>();
      if(f.equals("V")) 
    	  {for(int i=0; i<args.length; i++)
	          vtype.add(new CncType("__gfVar",args[i]));
	   rez.add(new AppResult(cncFun,cty,vtype));
	   return rez;}	
      if(f.equals("_B"))
	  {vtype.add(new CncType(cty.getCId(),args[0]));
	   for(int i=1; i<args.length;i++)
	          vtype.add(new CncType("__gfVar",args[i]));
	   rez.add(new AppResult(cncFun,cty,vtype));
	   return rez;}
      else {AbsFun[] absFuns = pgf.getAbstract().getAbsFuns();
	    Type t = null;
	    for(int i=0; i<absFuns.length;i++)
	     	 if(f.equals(absFuns[i].getName())) t = absFuns[i].getType(); 
	    if(t == null) throw new Exception(" f not found in the abstract syntax");
            Vector<String> catSkel = catSkeleton(t);
	    String res = catSkel.elementAt(0);
            for(int i=0; i<args.length;i++)
		 vtype.add(new CncType(catSkel.elementAt(i+1),args[i]));
	    rez.add(new AppResult(cncFun,new CncType(res,cty.getFId()),vtype));
	    return rez;}}
   else {
	 int fid = ((CoerceProduction) p).getInitId();
         HashSet<Production> setProds = prods.get(new Integer(fid));
         Iterator<Production> it = setProds.iterator();
         while(it.hasNext()) 
           rez.addAll(toApp(cty,it.next(),f,prods));
         return rez;}	     
}


/** computes the types of the arguments of a function type
**/	
public Vector<String> catSkeleton(Type t)
{Vector<String> rez = new Vector<String>();
rez.add(t.getName());
Hypo[] hypos = t.getHypos();
for(int i=0; i<hypos.length;i++)
	rez.add(hypos[i].getType().getName());
return rez;
}

/** intermediate linearization helper function
**/
public Vector<LinTriple> lin(Vector<String> xs, CncType mb_cty, Integer n_fid, Expr e, Vector<Expr> es) throws Exception
{Vector<LinTriple> rez = new Vector<LinTriple>();
if(e instanceof AppExp)
   {es.add(((AppExp)e).getRightExpr());
        return lin(xs,mb_cty,n_fid,((AppExp)e).getLeftExpr(),es);}
else if ((e instanceof LiteralExp) && (es.isEmpty()))
   {Literal ll = ((LiteralExp) e).getLiteral();
    if (ll instanceof StringLiteral)
	  rez.add(new LinTriple(n_fid+1, new CncType("String",n_fid),ss(((StringLiteral)ll).getValue())));
     else if (ll instanceof IntLiteral)
 	  rez.add(new LinTriple(n_fid+1, new CncType("Int",n_fid), ss(""+((IntLiteral)ll).getValue())));
      else rez.add(new LinTriple(n_fid+1, new CncType("Float",n_fid), ss(""+((FloatLiteral)ll).getValue())));
    return rez;}
else if (e instanceof MetaExp) throw new Exception("linearization for meta expressions is not implemented yet!");
else if (e instanceof AbsNameExp) return apply(xs, mb_cty,n_fid, ((AbsNameExp)e).getName(),es);
else if (e instanceof VarExp) throw new Exception("linearization for variable expressions is not implemented yet!");
else if (e instanceof TypedExp) throw new Exception("linearization for typed expressions is not implemented yet!");
else throw new Exception("linearization for expressions with implicit arguments is not implemented yet!");	
}
	
/** creates a simple vector of vectors of bracketed tokens containing a string value
**/
public Vector<Vector<BracketedTokn>> ss(String s)
{Vector<Vector<BracketedTokn>> bt = new Vector<Vector<BracketedTokn>>();
Vector<BracketedTokn> v = new Vector<BracketedTokn>();
String[] sts = new String[1];
sts[0]=s;
v.add(new LeafKS(sts));
bt.add(v);
return bt;	
}

/** computes the sequence of bracketed tokens associated to a symbol
**/
public Vector<BracketedTokn> compute(Symbol s, Vector<CncType> cncTypes, Vector<Vector<Vector<BracketedTokn>>> linTables)
{if(s instanceof ArgConstSymbol)
   {int arg = ((ArgConstSymbol) s).arg();
    int cons = ((ArgConstSymbol) s).cons();
    return getArg(arg,cons,cncTypes,linTables);}
else if(s instanceof AlternToksSymbol)
    {String[] toks = ((AlternToksSymbol) s).tokens();
      Alternative[] alts = ((AlternToksSymbol) s).getAlternatives();
	  Vector<BracketedTokn> v = new Vector<BracketedTokn>();
      v.add(new LeafKP(toks,alts));
      return v;} 
else {String[] toks = ((ToksSymbol)s).tokens();
      Vector<BracketedTokn> v = new Vector<BracketedTokn>();
      v.add(new LeafKS(toks));
      return v;}
}

/** retrieves a sequence of bracketed tokens from an intermediate result of the linearization
 * according to 2 indices from a production
**/
public Vector<BracketedTokn> getArg(int d, int r, Vector<CncType> cncTypes, Vector<Vector<Vector<BracketedTokn>>> linTables)
{if(cncTypes.size() <= d) return new Vector<BracketedTokn>();
CncType cncType = cncTypes.elementAt(d);
Vector<Vector<BracketedTokn>> lin = linTables.elementAt(d);
String cat = cncType.getCId();
int fid = cncType.getFId();
Vector<BracketedTokn> arg_lin = lin.elementAt(r);
if(arg_lin.isEmpty()) return arg_lin;
Vector<BracketedTokn> bt = new Vector<BracketedTokn>();
bt.add(new Bracket(cat,fid,r,arg_lin));
return bt;
}


/** computes a sequence of bracketed tokens from the sequence of symbols of a concrete function
**/
public Vector<BracketedTokn> computeSeq(Sequence seqId, Vector<CncType> cncTypes, Vector<Vector<Vector<BracketedTokn>>> linTables)
{Vector<BracketedTokn> bt = new Vector<BracketedTokn>();
Symbol[] symbs = seqId.getSymbols();
for(int j=0;j<symbs.length;j++)
	 bt.addAll(compute(symbs[j],cncTypes,linTables));
return bt;
}


/** shuffles the results of of the intermediate linearization, for generating all the possible combinations
 **/
public Vector<RezDesc> descend(int n_fid, Vector<CncType> cncTypes, Vector<Expr> exps, Vector<String> xs) throws Exception
{Vector<RezDesc> rez = new Vector<RezDesc>();
 if(exps.isEmpty()) {rez.add(new RezDesc(n_fid,new Vector<CncType>(), new Vector<Vector<Vector<BracketedTokn>>>()));
                     return rez;	 }
   else {CncType cncType = cncTypes.firstElement();
         cncTypes.remove(0);
         Expr exp = exps.firstElement();
         exps.remove(0);
         Vector<LinTriple> rezLin = lin0(new Vector<String>(), xs, cncType,n_fid,exp);
         Vector<RezDesc> rezDesc = descend(n_fid,cncTypes,exps,xs);
         for(int i =0; i<rezLin.size(); i++)
            for(int j=0; j<rezDesc.size();j++)
	    	  {CncType c = rezLin.elementAt(i).getCncType();
	    	   Vector<CncType> vcnc = rezDesc.elementAt(j).getCncTypes();
	    	   vcnc.add(c);
	    	   Vector<Vector<Vector<BracketedTokn>>> vbt = rezDesc.elementAt(j).getBracketedTokens();
	    	   Vector<Vector<BracketedTokn>> bt = rezLin.elementAt(i).getLinTable();
	    	   vbt.add(bt);
	    	   rez.add(new RezDesc(n_fid,vcnc,vbt));}
	 }
return rez;	
}

/**checks if a production is application production**/
public boolean isApp(Production p)
{return (p instanceof ApplProduction);}


/** checks if an integer is the index of an integer literal
**/
boolean isLiteralInt(int i)
{return i == -2;}

/** checks if an integer is the index of a string literal
**/
boolean isLiteralString(int i)
{return i == -1;}

/** checks if an integer is the index of a float literal
**/
boolean isLiteralFloat(int i)
{return i == -3;}

/** checks if an integer is the index of a variable literal
**/
boolean isLiteralVar(int i)
{return i == -4;}

/** checks if an integer is the index of a literal
**/
boolean isLiteral(int i)
{ if (isLiteralString(i) || isLiteralInt(i) || isLiteralFloat(i) || isLiteralVar(i)) return true;
return false;}

/*
public static void main(String[] args)
{ System.out.println("Main function");
	try {
    Generator gg = new Generator("Phrasebook.pgf");
    Expr rez = gg.gen("Greeting");
    Linearizer ll = new Linearizer("Phrasebook.pgf","PhrasebookFre",rez);
    //System.out.println("The l-productions are "+ll.getLProductions());
   // System.out.println("intermediate linearizing : "+ll.lin0(new Vector<String>(), new Vector<String>(), null, new Integer(0), rez));
    if(rez != null)
     System.out.println("the random expression is "+rez.toString());
    else System.out.println("the random expression is null");
    System.out.println("The resulting linearization is "+ll.renderLin(ll.lin0(new Vector<String>(), new Vector<String>(), null, new Integer(0), rez)));
	System.out.println("The end");
	}
 catch(Exception e) {System.out.println("Something went wrong : "+e.toString());}
 }*/

}



