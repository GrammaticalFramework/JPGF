package org.grammaticalframework.linearizer;

import org.grammaticalframework.Utils;

/**
 * This class is the mots basic bracketed token class : just a list of tokens
 **/
public class LeafKS extends BracketedTokn{
    public final String[] tokens;

    public LeafKS(String[] tokens) {
        this.tokens = tokens;
    }

    public String toString() {
	return "\"" + Utils.concat(tokens) + "\"";
    }
}
