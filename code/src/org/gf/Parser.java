package org.gf;

/* this is the parser class : 
 * A Private class that is responsible for parsing according to a grammar.
 * It holds a parse "state" 
 */


class Parser {
	
	//
	int nextFreshCategory;
	
	public Parser(Grammar g, Category s) {
		// TODO: initialize nextFreshCategory
	}
	
	// Dealing with fresh categories
	public int freshCategory() {
		int c = nextFreshCategory;
		nextFreshCategory++;
		return c;
	}
	
	public String[] getNextWords() {
		// TODO: 
		return null;
	}
	
	private void compute(int k) {}
}
