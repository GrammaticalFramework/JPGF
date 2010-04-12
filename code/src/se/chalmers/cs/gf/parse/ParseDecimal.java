/*
 * ParseDecimal.java
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

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.GFException;

import java.util.*;

/**
 *  Parses a decimal number to the numerals abstract syntax.
 */
public class ParseDecimal implements Parser {

        public Collection<Tree> parse(String s) {
                try {
                        long l = Long.parseLong(s);
                        
                        if (l < 1)
                                throw new GFException("Numerals < 1 not supported.");
                        
                        Tree t = node("num", conv3(l));
                        return Arrays.asList(t);
                } catch (NumberFormatException ex) {
                        return new LinkedList<Tree>();
                }
        }

        public Collection<Tree> parse(String s, String cat) {
                if (!cat.equals("Numeral"))
                        throw new GFException("Unsupported category " + cat);

                return parse(s);
        }

        public String getName() {
                return "mydecimal";
        }

        public boolean tryThisOne() {
                return true;
        }

        private Tree convDigit(long l) {
                return leaf("n" + l);
        }

        private Tree conv0(long l) {
                if (l == 1) {
                        // pot01 : Sub10 ;  -- 1
                        return leaf("pot01");
                } else {
                        // pot0 : Digit -> Sub10 ;  -- d * 1
                        return node("pot0", convDigit(l));
                }
        }

        private Tree conv1(long l) {
                if (l == 10) {
                        // pot110 : Sub100 ;  -- 10
                        return leaf("pot110");
                } else if (l == 11) {
                        // pot111 : Sub100 ;  -- 11
                        return leaf("pot111");
                } else if (l > 11 && l < 20) { 
                        // pot1to19 : Digit -> Sub100 ;  -- 10 + d
                        return node("pot1to19", convDigit(l-10));
                } else if (l < 10) {
                        // pot0as1 : Sub10 -> Sub100 ; -- coercion of 1..9
                        return node("pot0as1", conv0(l));
                } else if (l % 10 == 0) {
                        // pot1 : Digit -> Sub100 ; -- d * 10
                        return node("pot1", convDigit(l/10));
                } else {
                        // pot1plus : Digit -> Sub10 -> Sub100 ; -- d * 10 + n
                        return node("pot1plus", convDigit(l/10), conv0(l%10));
                }
        }

        private Tree conv2(long l) {
                if (l < 100) {
                        // pot1as2 : Sub100 -> Sub1000 ;  -- coercion of 1..99
                        return node("pot1as2", conv1(l));
                } else if (l % 100 == 0) {
                        // pot2 : Sub10 -> Sub1000 ;  -- m * 100
                        return node("pot2", conv0(l/100));
                } else {
                        // pot2plus : Sub10 -> Sub100 -> Sub1000 ;  -- m * 100 + n
                        return node("pot2plus", conv0(l/100), conv1(l%100));
                }
        }

        private Tree conv3(long l) {
                if (l < 1000) {
                        // pot2as3 : Sub1000 -> Sub1000000 ;  -- coercion of 1..999
                        return node("pot2as3", conv2(l));
                } else if (l % 1000 == 0) {
                        // pot3 : Sub1000 -> Sub1000000 ; -- m * 1000
                        return node("pot3", conv2(l/1000));
                } else {
                        // pot3plus : Sub1000 -> Sub1000 -> Sub1000000 ; -- m * 1000 + n
                        return node("pot3plus", conv2(l/1000), conv2(l%1000));
                }
        }
        

        private Tree leaf(String label) {
                return new Fun(label);
        }

        private Tree node(String label, Tree... child) {
                return new Fun(label, child);
        }

}
