package pgf.parse.pmcfg;

import java.util.Arrays;

/** We call production a rule like A -> f[B].
 * 	Production can come from the grammar or be generated at run time.
 * 
 * In our case, the right hand side is 
 * 
 * @author gdetrez
 *
 */
public class Production {
    public int resultCategory;
    public int[] domain;
    public RHSItem[][] rhs;
    
    public Production(int l, int[] domain, RHSItem[][] r)
    {
	this.resultCategory = l;
	this.domain = Arrays.copyOf(domain, domain.length);
	this.rhs = r;
	// FIXME : does this really copy the sub arrays ? 
	// do I really need that ?
	//this.rhs = Arrays.copyOf(r, r.length);
    }
    
    public Production(Production pr) {
	this(pr.resultCategory, pr.domain, pr.rhs);
    }
    
    public RHSItem[][] getRightHandSide() {
	return rhs;
    }
    
    /** This will duplicate the production p but change the left hand side to c
     * 
     * @param c
     * @param p
     */
    public Production(int c, Production p) {
	this(c, p.domain, p.rhs);
    }
    
    /**
     *  Get the left hand side of the rule.
     */
    public int getCategory() {
	return resultCategory;
    }
    
    /**
     *  Get the number of terminals or non-terminal on the RHS.
     */ 
    public int getSize(int d) {
	return this.rhs[d].length;
    }
    
    public String toString()
    {
	String domain = "";
	boolean first = true;
	for (int c : this.domain)
	    if (first)
		domain += c;
	    else
		domain += ", " + c;
	return this.resultCategory + " -> f[" + domain + "]";
    }

	public RHSItem getPosition(int l, int i) {
		return this.rhs[l][i];
	}

	public int getDomain(int i) {
		assert i < domain.length;
		return this.domain[i];
	}

	public void setDomain(int d, int n) {
		this.domain[d] = n;
	}

	public boolean equals(Object o) {
		return o instanceof Production && equals((Production)o);
	}

	public boolean equals(Production e) {
		if (this.domain.length != e.domain.length) return false;
		for (int i = 0 ; i < this.domain.length ; i++)
		    if (!(this.domain[i] == e.domain[i])) return false;
		return this.resultCategory == e.resultCategory
				&& this.rhs.equals(e.rhs);
	}
	
    public int hashCode() {
	int hash = this.resultCategory ;
	for (int c: this.domain)
	    hash += c;
	return hash + this.rhs.hashCode();
    }
}
