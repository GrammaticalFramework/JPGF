/*
 * SimpleLexer.java
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

/**
 *  A simple lexer.
 */
public class SimpleLexer extends RegexLexer {

        public SimpleLexer() {
                super("(" +
                      // simple words are any non-empty sequence of 
                      // lower case letters, upper case letters, 
                      // title case letters, other letters,
                      // modifier letters, modifier symbols, 
                      // decimal digits, letter digits,
                      // other digits, connector punctuation
		      // and single quotes.
                      "[\\p{Ll}\\p{Lu}\\p{Lt}\\p{Lo}\\p{Lm}\\p{Sk}\\p{Nd}\\p{Nl}\\p{No}\\p{Pc}']+"
                      + "|" +
                      // quoted words are strings of any character non double quote
                      // enclosed in double quotes
                      // FIXME: allow escaped double quotes and backslashes
                      "\"(\\\\\"|[^\"])*\""
                      + "|" +
                      // punctuation is a single punctuation character
                      // punctuation characters are:
                      // - opening punctuation
                      // - clsoing punctuation 
                      // - dashes
                      // - other punctuation
                      // - currency symbols
                      // - mathematic symbols
                      "[\\p{Ps}\\p{Pe}\\p{Pd}\\p{Po}\\p{Sc}\\p{Sm}]"
                      + ")");
        }

        public static void main(String[] args) throws java.io.IOException {
                LexerTest.testLexer(SimpleLexer.class);
        }

}
