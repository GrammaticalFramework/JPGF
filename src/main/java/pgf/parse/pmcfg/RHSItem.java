package pgf.parse.pmcfg;

public abstract class RHSItem {
	abstract boolean isTerminal();

	abstract String getTerminal();

	abstract int[] getNonTerminal();
}
