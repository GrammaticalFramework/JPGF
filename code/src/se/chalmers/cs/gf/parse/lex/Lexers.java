/*
 * Lexers.java
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

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.ClassUtil;
import static se.chalmers.cs.gf.parse.ParseLogger.*;

import java.util.*;

/**
 *  Maps language names to lexers.
 */
public class Lexers {

        private Lexer defaultLexer;

        private Map<String,Lexer> lexers;

        private Lexers(Lexer defaultLexer) {
                this.defaultLexer = defaultLexer;
                this.lexers = new HashMap<String,Lexer>();
        }

        public void add(String language, Lexer lexer) {
                lexers.put(language, lexer);
        }

        public Lexer get(String language) {
                Lexer lexer = lexers.get(language);
                return (lexer == null) ? defaultLexer : lexer;
        }

        public static Lexers loadLexers(String defaultLexerName, 
                                        Map<String,String> lexerNames) {

                Lexer d = loadLexer(defaultLexerName);
                Lexers lexers = new Lexers(d);

                if (lexerNames != null) {
                        for (Map.Entry<String,String> e : lexerNames.entrySet()) {
                                String language = e.getKey();
                                String lexerName = e.getValue();
                                log.fine("Creating lexer for " + language 
                                         + ": " + lexerName);
                                Lexer lexer = loadLexer(lexerName);
                                lexers.add(language, lexer);
                        }
                }

                return lexers;
        }

        private static Lexer loadLexer(String lexerName) {
                try {
                        return (Lexer)ClassUtil.instantiate(lexerName);
                } catch (ClassCastException ex) {
                        throw new GFException(lexerName + " is not a lexer class.");
                }
        }

}
