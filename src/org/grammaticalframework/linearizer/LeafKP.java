package org.grammaticalframework.linearizer;

import org.grammaticalframework.reader.Alternative;

/**
 * This class represent a 'pre' object.
 * that is either an alternative between multiple lists of tokens
 * with condition on the following words and a default alternative.
 *
 * Example: pre( "parce que", "parce qu'"/"il", "parce qu'"/"on")
 * will be represented by a LeafKP with
 *   defaultTokens = ["parce","que"]
 *   alternatives = [ (["parce", "qu'"], ["il"])
 *                  , (["parce", "qu'"], ["on"]) ]
 **/
public class LeafKP extends BracketedTokn{
    public final String[] tokens;
    public final Alternative[] alternatives ;

    public LeafKP(String[] _strs, Alternative[] _alts) {
        this.tokens = _strs;
        this.alternatives = _alts;
    }

    public Alternative[] getAlts() {return alternatives;}
    public String toString() {
        String rez = "string names : [";
        for(int i=0;i< tokens.length;i++)
            rez+= " " + tokens[i];
        rez+= "] , Alternatives : [";
        for(int i=0; i<alternatives.length;i++)
            rez+= " " + alternatives[i].toString();
        rez+="]";
        return rez;
    }
}
