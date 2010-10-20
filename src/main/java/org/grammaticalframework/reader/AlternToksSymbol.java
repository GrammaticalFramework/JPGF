package org.grammaticalframework.reader;

public class AlternToksSymbol extends ToksSymbol {
    Alternative[] alts;

    public AlternToksSymbol(String[] toks, Alternative[] _alts) {
        super(toks);
        alts = _alts;
    }

    public boolean isTerminal() { return true; }

    public String toString()
    {
	StringBuffer sb = new StringBuffer("pre { ");
	for (String s : toks) {
	    sb.append(s);
	    sb.append(" "); }
	sb.append("; ");
	for (Alternative a : alts) {
	    sb.append(a);
	    sb.append("; "); }
	sb.append("}");
        return sb.toString();
    };
    public String[] getToks(){return toks;}
    public Alternative[] getAlternatives(){return alts;}

		}


