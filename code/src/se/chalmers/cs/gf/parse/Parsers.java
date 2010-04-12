/*
 * Parsers.java
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
import static se.chalmers.cs.gf.parse.ParseLogger.*;

import java.util.*;

/**
 *  A collection of parsers.
 */
public class Parsers {

        protected Map<String,Parser> map;

        public Parsers() {
                map = new TreeMap<String,Parser>();
        }

        /**
         *  Get a parser by name.
         */
        public Parser getParser(String lang) {
                Parser parser = map.get(lang);

                if (parser == null) 
                        throw new GFException("There is no parser for " + lang);

                return parser;
        }

        /**
         * 
         */
        public void addParser(Parser parser) {
                String lang = parser.getName();
                if (map.containsKey(lang)) 
                        throw new GFException("There is already a parser for " + lang);
                map.put(lang, parser);
        }

        public Set<String> listParsers() {
                return map.keySet();
        }

}
