package se.chalmers.cs.pgf.parse.pmcfg;

/**
 * A FreshCategory is a category created during parsing.
 * @author gdetrez
 *
 */
public class FreshCategory extends Category {

	private int j, l, k;
	private Category A;

	public FreshCategory(Category A, int l, int j, int k) 
	{
		this.A = A;
		this.j = j;
		this.k = k;
		this.l = l;
	}

	public boolean equals(Object o) {
		return o instanceof FreshCategory && equals((FreshCategory)o);
	}

	public boolean equals(FreshCategory c) {
		return super.equals(c)
				&& j == c.j
				&& k == c.k
				&& l == c.l;
	}
	
	public String toString()
	{
		return "("+this.A.toString() +","+l+","+j+","+k+")";
	}
	
	public int hashCode()
	{
		return this.A.hashCode() + this.l + this.j + this.k;
	}
}
