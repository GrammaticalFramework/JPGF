package pgf.parse.pmcfg;

import java.util.Collection;
import java.util.Set;

public class PMCFGGrammar {
	
	private String name;
	private Production[] rules;
	private int startCat;

	public PMCFGGrammar(String name, int catS, Production[] productions) {
		this.name = name;
		this.startCat = catS;
		this.rules = productions;
	}

	public int max_dimension() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Production[] getRules()
	{
		return rules;
	}
	
	public int getStartCategory()
	{
		return this.startCat;
	}
	
	public String getName()
	{
		return this.name;
	}

}
