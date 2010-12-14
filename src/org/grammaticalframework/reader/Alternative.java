package org.grammaticalframework.reader;

// An alternative is like a token (TokSymbol) but restricted to the case
// where the folowing words match "prefix"
// Todo: subclass TokSymbol ???

public class Alternative {
    private String[] tokens;
    private String[] prefix;

    public Alternative(String[] _alt1, String[] _alt2) {
        tokens = _alt1;
        prefix = _alt2;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	for (String t : tokens) {
	    sb.append(t);
	    sb.append(" "); }
	sb.append("/ ");
	for (String t : prefix) {
	    sb.append(t);
	    sb.append(" "); }
        return sb.toString();
    }

    public String[] getAlt1() {
        return tokens;
    }

    public String[] getAlt2() {
        return prefix;
    }
}
