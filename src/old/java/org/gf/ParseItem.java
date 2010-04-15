package org.gf;

import se.chalmers.cs.pgf.parse.pmcfg.RHSItem;

class ParseItem {
	
	public int start_position;			// j
	public int constituent_index;		// l
	public Production rule;			// A -> f[|B>]
	public int dot_position;			// p
	
	public ParseItem(int j, Production p, int l, int pp)
	{
		start_position = j;
		rule = p;
		constituent_index = l;
		dot_position = pp;
	}
	
	public RHSItem getWhatIsAfterTheDot()
	{
		// TODO
		return null;
	}

}
