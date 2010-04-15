package pgf.parse.pmcfg;


public class Terminal extends RHSItem {
	private String terminal;
	public Terminal(String s)
	{
		terminal = s;
	}
	
	public boolean isTerminal()
	{
		return true;
	}
	
	public String getTerminal()
	{
		return terminal;
	}

	public int[] getNonTerminal() {
		return null;
	}
}
