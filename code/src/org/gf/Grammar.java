package org.gf;

import java.util.*;

import se.chalmers.cs.pgf.parse.pmcfg.RHSItem;

public class Grammar {
	private Category startCategory;
	private Production[] productions;
	
	public Grammar( Category cat, Production[] prods )
	{
		startCategory = cat;
		productions = prods;
	}
	
//	public AbstractTree[] parse(String[] input, ParserState s, Category topCategory)

    public void compute(int k, HoldingSet S, ProductionSet P, ParseAgenda A)
	{
		// Creating locally used data structures
		MultiDictionary<String,ParseItem> C = new MultiDictionary<String,ParseItem>();
		CategoryCache F = new CategoryCache();

	    while (!A.isEmpty())
	      {
	    	 // poll returns and removes the first element
	         ParseItem x = A.poll();
	         
	         RHSItem afterTheDot = x.getWhatIsAfterTheDot();
	         if (afterTheDot == null) { // At the end
	        	 Category N = F.get(x.start_position,x.rule.getLeftHandSide(),x.constituent_index);
	        	 if (N == null) {
	        		 N = Category.generateFreshCategory();
	        		 F.add(x.start_position,x.rule.getLeftHandSide(),x.constituent_index,N);
	        		 Pair<ParseItem,Integer>[] items = S.getAll(x.start_position,x.rule.getLeftHandSide(),x.constituent_index);
	        		 for (int i = 0 ; i < items.length ; i++) {
	        			 ParseItem xprime = items[i].fst;
	        			 int d = items[i].snd;
	        			 Production ruleprime = new Production(xprime.rule);
	        			 ruleprime.domain[d] = N;
	        			 A.add(new ParseItem(xprime.start_position, 
	        					 			 ruleprime, 
	        					 			 xprime.constituent_index, 
	        					 			 xprime.dot_position + 1));
	        		 }
	        	 } else {
	        		 Triple<ParseItem,Integer, Integer>[] items = S.getAll(k, N);
	        		 for (int i = 0 ; i < items.length ; i++ ) {
	        			 ParseItem xprime = items[i].v1;
	        			 int d = items[i].v2.intValue();
	        			 int r = items[i].v3.intValue();
	        			 Production ruleprime = new Production(xprime.rule);
	        			 ruleprime.lhs = N;
	        			 A.add(new ParseItem(k, ruleprime, r, 0));
	        		 }
	        	 }
	         }
	         else if (afterTheDot.isTerminal())
	         // before s ∈ T
	        	 C.add(afterTheDot.getTerminal(), new ParseItem(x.start_position, x.rule, x.constituent_index, x.dot_position + 1));
	         else
	         {
	         // before <d;r>
	        	 int[] dr = afterTheDot.getNonTerminal();
	        	 int d = dr[0];
	        	 int r = dr[1];
	        	 Category Bd = x.rule.domain[d];
	        	 if (!S.has(Bd, r, x, d)) {
	        		 S.add(k, Bd, r, x, d);
	        		 Production[] items = P.getAll(Bd);
	        		 for (int i = 0 ; i < items.length ; i++) {
	        			 A.add(new ParseItem(k, items[i], r, 0));
	        		 }
	        	 }
	        	 Production[] items_ = F.getAll(k, Bd, r);
	        	 for (int i = 0 ; i < items_.length ; i++) {
	        		 A.add(new ParseItem(j, newprod, l, p+1));
	        	 }
	         }
	      }
	}
	
	public AbstractTree[] parse(String[] input, Category topCategory)
	{	
		Parser = new Parser(this, topCategory, input);
		return Parser.getTrees();
	}

	public AbstractTree[] parse(String[] input)
	{
		return parse (input, startCategory);
	}

}


// the base class for F
class CategoryCache extends Hashtable<Triple<Integer, Category, Integer>, Category> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Category get(int j, Category A, int l)
	{
		return get(new Triple<Integer, Category, Integer>(new Integer(j),A, new Integer(l)));
	}
	
	public Production[] getAll(int k, Category bd, int r) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(int j, Category A, int l, Category N)
	{
		put(new Triple<Integer, Category, Integer>(new Integer(j),A,new Integer(l)), N);
	}
}
