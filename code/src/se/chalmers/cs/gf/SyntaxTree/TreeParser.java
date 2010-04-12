/*
 * TreeParser.java
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
package se.chalmers.cs.gf.SyntaxTree;

import java_cup.runtime.*;
import se.chalmers.cs.gf.SyntaxTree.Absyn.*;
import java.io.*;

public class TreeParser
{

        public static Tr parseTree(Reader in) {
                Yylex l = new Yylex(in);
                parser p =  new parser(l);

                try {
                        se.chalmers.cs.gf.SyntaxTree.Absyn.Tr parse_tree = p.pTr();
                        return parse_tree;
                } catch(Throwable e) {
                        System.err.println("Error parsing tree: " 
                                           + e.toString() + " at line " 
                                           + l.line_num()
                                           + ", near \"" + l.buff() 
                                           + "\" :");
                        return null;
                }
        }

        public static Tr parseTree(String str) {
                return parseTree(new StringReader(str));
        }

}
