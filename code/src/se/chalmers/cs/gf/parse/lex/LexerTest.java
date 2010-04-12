/*
 * LexerTest.java
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

import java.io.*;

/**
 *  Test a lexer.
 */
public class LexerTest {

        private LexerTest() {}

        public static void testLexer(Class<? extends Lexer> lexerClass) throws IOException {
                Lexer l = (Lexer)ClassUtil.instantiate(lexerClass.getName());
                BufferedReader in 
                        = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = in.readLine()) != null)
                        System.out.println(l.lex(line));
        }

}
