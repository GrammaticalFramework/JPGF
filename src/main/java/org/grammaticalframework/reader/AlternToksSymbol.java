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
        String s = "Tokens : "+toks + " Alternatives : "+alts;
        return s;	   
    };
    public String[] getToks(){return toks;}
    public Alternative[] getAlternatives(){return alts;}

		}


