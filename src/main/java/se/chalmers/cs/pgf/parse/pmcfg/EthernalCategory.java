package se.chalmers.cs.pgf.parse.pmcfg;

/**
 * An EthernalCategory is a Category that exists in the grammar, i.e. not created during parsing.
 * @author gdetrez
 *
 */
public class EthernalCategory extends Category {
	
	private String name;
	private int dimension;
	
	public EthernalCategory(String name, int dimension)
	{
		this.name = name;
		this.dimension = dimension;
	}
	
	public EthernalCategory(EthernalCategory c) {
		this(c.name, c.dimension);
	}

	public String getName()
	{
		return this.name;
	}

	/** **********************************************************************
	 * Overriding toString
	 */
	
   public String toString() 
    {
    	return this.name;
    }

    /** **********************************************************************
	 * Overriding equals and hashCode
	 */
	
    public boolean equals(Object o) {
        return o instanceof EthernalCategory && equals((EthernalCategory)o);
    }

    public boolean equals(EthernalCategory c) {
        return this.name.equals(c.name) && this.dimension == c.dimension;
    }
    
    public int hashCode()
    {
    	return this.name.hashCode() + this.dimension;
    }


}
