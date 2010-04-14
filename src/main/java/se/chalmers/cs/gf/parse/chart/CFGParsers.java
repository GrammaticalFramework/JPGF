/*
 * CFGParsers.java
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
package se.chalmers.cs.gf.parse.chart;

import se.chalmers.cs.gf.parse.lex.*;
import se.chalmers.cs.gf.parse.Parsers;
import static se.chalmers.cs.gf.parse.ParseLogger.*;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 *  A collection of chart parsers for a collection of CF grammars.
 */
public class CFGParsers extends Parsers {

        public CFGParsers(CFGGrammars grammars, Lexers lexers, boolean robust) {
                for (String n : grammars.listGrammars()) {
                        Lexer lexer = lexers.get(n);
                        CFGrammar g = grammars.getGrammar(n);
                        ChartParser p = new ChartParser(lexer, g, robust);
                        addParser(p);
                }
        }

        public static CFGParsers readParsers(Reader in, Lexers lexers, boolean robust) 
                throws IOException {
                return new CFGParsers(CFGGrammars.readCFGM(in), lexers, robust);
        }

        public static CFGParsers readParsers(String file, Lexers lexers, boolean robust) 
                throws IOException {
                return new CFGParsers(CFGGrammars.readCFGM(file), lexers, robust);
        }

}
