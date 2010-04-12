/*
 * Parser.java
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

import java.util.Collection;

/**
 *  Interface for all parsers from text to abstract syntax.
 */
public interface Parser {

        /**
         *  Parse a string to zero or more abstract syntax trees the
         *  parsers default category.
         */
        public Collection<Tree> parse(String str);

        /**
         *  Parse a string to zero or more abstract syntax trees in
         *  the given category.
         *
         *  @param cat The category to parse in. Note that the category
         *  name given here might not be identical to the one in the GF 
         *  grammar, as the conversion to a context-free grammar can change
         *  the category name. Typically, for non-dependent categories,
         *  the GF source category "C" is translated to "C{}.s".
         */
        public Collection<Tree> parse(String str, String cat);

        /**
         *  Get the name of this parser.
         */
        public String getName();

        /**
         *  Whether this parser should be used when trying to parse a 
         *  string with different parsers. This can be used to exclude
         *  special parsers from such attempts.
         */ 
        public boolean tryThisOne();

}
