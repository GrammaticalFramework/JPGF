package se.chalmers.cs.pgf.parse.pmcfg;

/**
 * An active edge is what Krasimirs article calls an active item. i.e. a rule with a dot • somewhere.
 * In our case, since we are using PMCFG, the position of the dot is coded by two integer (instead of one
 * with CFGs)
 * @author gdetrez
 *
 */
public class ActiveEdge {
	
	/**
	 * l and pos code the position of the dot (l is the constituent index and pos 
	 * the position of the dot in the constituent)
	 * j is the start position where the rule is applied in the input string.
	 */
	private int pos, l, j;
	private Production rule;
	
	/** **********************************************************************
	 * constructors
	 */
	
	/**
	 * Create a new active edge
	 */
	public ActiveEdge(Production rule, int l, int pos)
	{
		this.pos = pos;
		this.l = l;
		this.rule = rule;
		assert this.rule != null;
	}
	
	/**
	 * Copy an existing active edge
	 * @param e
	 */
	public ActiveEdge(ActiveEdge e)
	{
		this(e.rule, e.l, e.pos);
	}
	
	/**
	 * Copying an existing edge, replacing the category at position d by N in the rule domain
	 * @param e
	 * @param d
	 * @param N
	 */
	public ActiveEdge(ActiveEdge e, int d, Category N)
	{
		this(e);
		Production newrule = new Production(e.rule);
		newrule.setDomain(d, N);
		this.rule = newrule;
	}

	/** **********************************************************************
	 * Accessors
	 */
	public int getConstituentIndex() { return this.l; }
	
	public int getBegin() { return j; }
	
	public void setBegin(int j) { this.j = j; }
	
	public Production getRule() { return this.rule; }

	/** **********************************************************************
	 * Methods
	 */

	/** 
	 * Goes one step forward (move the dot to the right)
	 */
	public void stepForward()
	{
		if (this.isFinished())
			throw new IllegalStateException(
            	"Can't go forward in an already finished rule.");
		this.pos++;
	}
	
	/**
	 * @return true if the dot has reached the end of the constituent, false otherwise.
	 */
	public boolean isFinished()
	{
		return !(this.pos < this.rule.getSize(this.l));
	}

	public boolean needs(PassiveEdge p) {
		if (this.isFinished()) return false;
		RHSItem i = this.getNext();
		if (i.isTerminal())
			return false;
		int[] jk = i.getNonTerminal();
		return this.rule.getDomain(jk[0]) == p.getCategory() 
				&& jk[1] == p.getContituentIndex();
	}

	public boolean needs(String t) {
		if (this.isFinished()) return false;
		RHSItem i = this.getNext();
		if (!i.isTerminal())
			return false;
		return i.getTerminal() == t;
	}
	
	public RHSItem getNext() {
		return this.rule.getPosition(this.l, this.pos);
	}
	
	/** **********************************************************************
	 * Overriding toString
	 */
	
	public String toString()
	{
		return "[" + this.rule + ", " + this.l + ", " + this.pos + "]";
	}

	/** **********************************************************************
	 * Overriding equals and hashCode
	 */
	
	public boolean equals(Object o) {
		return o instanceof ActiveEdge && equals((ActiveEdge)o);
	}

	public boolean equals(ActiveEdge e) {
		return this.l == e.l 
				&& this.pos == e.pos
				&& this.rule.equals(e.rule);
	}
	
	public int hashCode() {
		return this.rule.hashCode() + this.l + this.pos;
	}
}
