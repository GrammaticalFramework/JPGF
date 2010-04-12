/*
 * RegexLexer.java
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
import static se.chalmers.cs.gf.parse.ParseLogger.*;

import java.util.*;
import java.util.regex.*;

/**
 *  Uses a single regular expression for lexing.
 */
public abstract class RegexLexer implements Lexer {

        private Pattern tokenPatt;

        public RegexLexer(String regex) {
                this.tokenPatt = Pattern.compile(regex);
        }

        // FIXME: discards input that it doesn't understand
        public List<Token> lex(CharSequence input) {
                List<Token> ts = new ArrayList<Token>();

                int lastEnd = 0;
                Matcher m = tokenPatt.matcher(input);
                while (m.find()) {
                        String s = m.group();
                        ts.add(new Token(s));
                        int start = m.start();
                        discard(input, lastEnd, start);
                        lastEnd = start + s.length();
                }
                discard(input, lastEnd, input.length());

                return ts;
        }

        private void discard(CharSequence s, int start, int end) {
                boolean allSpace = true;
                for (int i = start; i < end; i++) {
                        if (s.charAt(i) != ' ') {
                                allSpace = false;
                                break;
                        }
                }
                if (allSpace)
                        return;
                CharSequence t = s.subSequence(start, end);
                log.fine("Discarded unrecognized input: \"" + t + "\"");  
        }

}
