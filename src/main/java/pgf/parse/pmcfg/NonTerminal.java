package pgf.parse.pmcfg;


public class NonTerminal extends RHSItem {
	int[] jk;

	public NonTerminal(int j, int k)
	{
		jk = new int[] {j, k};
	}
	
	public boolean isTerminal()
	{
		return false;
	}

	@Override
	String getTerminal() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getNonTerminal() {
		return jk;
	}
}
