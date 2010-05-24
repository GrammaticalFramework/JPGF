package org.grammaticalframework.reader;

// An alternative is like a token (TokSymbol) but restricted to the case
// where the folowing words match "suffix"

public class Alternative {
    private String[] tok;
    private String[] suffix;

    public Alternative(String[] _alt1, String[] _alt2) {
        alt1 = _alt1;
        alt2 = _alt2;
    }

    public String toString() {
        return "(Alternative 1 : "+ alt1 + " , Alternative2 : "+alt2+")";
    }

    public String[] getAlt1() {
        return alt1;
    }

    public String[] getAlt2() {
        return alt2;
    }
}
