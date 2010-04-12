/*
 * Token.java
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
package se.chalmers.cs.gf.parse;

import se.chalmers.cs.gf.GFException;

public class Token {

        public enum Type { WORD, STRING, INT }

        private Type type;

        private String value;

        public Token(String value) {
                if (hasOnlyDigits(value)) {
                        this.type = Type.INT;
                        this.value = value;
                } else if (isQuoted(value)) {
                        this.type = Type.STRING;
                        this.value = unquote(value);
                } else {
                        this.type = Type.WORD;
                        this.value = unquote(value);
                }
        }

        /**
         *  Get the value of this token. If the token is a word,
         *  the value is the word itself. If the token is a string 
         *  literal backslashes and double
         *  quotes in the value are not escaped.
         */
        public String getValue() {
                return value;
        }

        /** Gets the type of this token. */
        public Type getType() {
                return type;
        }

	public boolean isWord() { return type == Type.WORD; }

        /**
         *  Get the integer value of this token.
         *  @throws GFException If this is not an integer token.
         */
        public int intValue() {
                if (type == Type.INT)
                        throw new GFException(value 
                                              + " is not an integer token.");
                return Integer.parseInt(value);
        }

        public boolean equals(Object o) {
                return o instanceof Token && equals((Token)o);
        }

        public boolean equals(Token t) {
                return type.equals(t.type) && value.equals(t.value);
        }

        public int hashCode() {
                return type.hashCode() + value.hashCode();
        }

        public String toString() {
                return type + ":" + value;
        }

        /**
         *  Checks if a string is quoted with double quotes.
         */
        private static boolean isQuoted(String s) {
                return s.length() >= 2 && s.startsWith("\"") 
                        && s.endsWith("\"");
        }

        /**
         *  Removes double quotes around a string, if there are any,
         *  and unescape backslashes and double quotes inside the
         *  string.
         */
        private static String unquote(String s) {
                StringBuilder sb = new StringBuilder(s);

                // remove surrounding quotes
                if (isQuoted(s)) {
                        sb.deleteCharAt(0);
                        sb.deleteCharAt(sb.length()-1);
                }
                
                // unescape backslashes and double quotes
                // we only go to the penultimate character,
                // since all escape sequences are two chars long.
                for (int i = 0; i < sb.length()-1; i++) {
                        if (sb.charAt(i) == '\\') {
                                sb.deleteCharAt(i);
                                i++; // Skip the now unescaped character,
                                     // as it may be a backslash but doesn't start
                                     // an escape sequence.
                        }
                }

                return sb.toString();
        }
        
        /** Checks if a string contains only decimal digits. */
        private static boolean hasOnlyDigits(String s) {
                for (int i = 0; i < s.length(); i++)
                        if (!Character.isDigit(s.charAt(i)))
                                return false;
                return true;
        }

}
