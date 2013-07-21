/* LeafKP.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
