/*
 * IgnoreLexer.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.parse.lex;

import se.chalmers.cs.gf.parse.Token;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 *  A lexer which takes the output from another lexer and 
 *  removes all WORD tokens not in a given set. String and integer
 *  literal tokens are retained.
 */
public class IgnoreLexer implements Lexer {

        private Lexer lexer;

        private Set<String> keepWords;

        public IgnoreLexer(Lexer lexer, Set<String> keepWords) {
                this.lexer = lexer;
                this.keepWords = keepWords;
        }

        public List<Token> lex(CharSequence input) {
                List<Token> in = lexer.lex(input);
                List<Token> out = new ArrayList<Token>();
                for (Token t : in) 
                        if (t.getType() != Token.Type.WORD 
                            || keepWords.contains(t.getValue()))
                                out.add(t);
                return out;
        }

}
