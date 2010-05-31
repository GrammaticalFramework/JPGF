package org.grammaticalframework.linearizer;

/**
 * This class is the mots basic bracketed token class : just a list of tokens
 **/
public class LeafKS extends BracketedTokn{
    private String[] tokens;

    public LeafKS(String[] tokens) {
        this.tokens = tokens;
    }

    public String[] getStrs() { return tokens; }

    public String toString() {
        String rez = "string names : [";
        for(int i=0; i < this.tokens.length; i++)
            rez+=(" " + this.tokens[i]);
        rez+="]";
        return rez;
    }
}
