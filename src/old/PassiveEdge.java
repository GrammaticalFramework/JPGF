package pgf.parse.pmcfg;

public class OldPassiveEdge{
	private int l;
	private int baseCategory;
	private int freshCategory;

	public PassiveEdge(ActiveEdge e)
	{
		if (!e.isFinished())
			throw new IllegalStateException(
			"Can't create a pasive edge from an unfinished active edge");
		this.l = e.getConstituentIndex();
		this.baseCategory = e.getRule().getCategory();
	}

	public int getFreshCategory() {
		if (this.freshCategory == null)
			throw new IllegalStateException(
					"Cant get fresh category of passive edge... try to add it to the chart before.");
		return this.freshCategory;
	}

	public int getContituentIndex() {
		return l;
	}

	public int getCategory() {
		return this.baseCategory;
	}
	
	public void setFreshCategory(int f) {
		this.freshCategory = f;
	}

	public String toString()
	{
		return "[" + this.baseCategory + ", " + this.l + ", " + this.freshCategory + "]";
	}

	public boolean equals(Object o) {
		return o instanceof PassiveEdge && equals((PassiveEdge)o);
	}

	public boolean equals(PassiveEdge e) {
		return this.l == e.l && this.baseCategory == e.baseCategory;
	}
	
	public int hashCode() {
		return this.baseCategory.hashCode() + this.l;
	}

}
