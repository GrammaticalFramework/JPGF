/*
 * Translator.java
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
package se.chalmers.cs.gf.translate;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.parse.*;

import se.chalmers.cs.gf.linearize.*;
import se.chalmers.cs.gf.linearize.gfvalue.*;
import se.chalmers.cs.gf.linearize.unlex.Unlexers;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.util.Pair.pair;

import java.util.*;

/**
 *  Translates between different concrete syntaces which use the
 *  same abstract syntax.
 */
public class Translator {

        private String name;

        private Parsers parsers;

        private TypeAnnotator annotator;

        private Linearizers linearizers;

	private Unlexers unlexers;

        /**
         *  @param name The name to use for the translator.
         *  @param parsers The parsers that this translator should use.
         *  @param annotator A type annotator.
         *  @param linearizers The linearizers that this translator should use.
         */
        public Translator(String name, Parsers parsers, 
                          TypeAnnotator annotator, Linearizers linearizers,
			  Unlexers unlexers) {
                this.name = name;
                this.parsers = parsers;
                this.annotator = annotator;
                this.linearizers = linearizers;
		this.unlexers = unlexers;
        }

        /**
         *  Gets the name of this translator.
         */
        public String getName() {
                return name;
        }

        /**
         *  Parse a string as a given language, using the default
	 *  start category for that language.
         */
        public Collection<Tree> parse(String lang, String text) {
                Parser p = getParser(lang);
                return p.parse(text);
        }

        /**
         *  Parse a string as a given language, in the given category.
         */
        public Collection<Tree> parse(String lang, String text, String cat) {
                Parser p = getParser(lang);
                return p.parse(text,cat);
        }

        /**
         *  Try parsing the input with all the available parsers.
         *
         *  @return A list of pairs (language, parse tree)
         */
        public Collection<Pair<String,Tree>> parseWithAll(String str) {
                ParseLogger.log.fine("Trying to parse '" + str + "' with all languages");

                Collection<Pair<String,Tree>> rs 
                        = new LinkedList<Pair<String,Tree>>();
                for (String l : parsers.listParsers()) {
                        Parser p = parsers.getParser(l);
                        if (p.tryThisOne()) {
                                try {
                                        ParseLogger.log.finer("Trying " + l);
                                        for (Tree t : p.parse(str)) {
                                                ParseLogger.log.fine(l + ": " + t);
                                                rs.add(pair(l, t));
                                        }
                                } catch (GFException ex) {
                                        ParseLogger.log.error(ex);
                                }
                        }
                }
                return rs;
        }

        /**
         *  Does type annotation and linearization.
         */
        public String linearize(String lang, Tree tree) {
                Tree annTree = annotator.annotate(tree);
                Linearizer l = getLinearizer(lang);
                return unlex(lang, l.linearize(annTree));
        }

        public String linearizeWithRanges(String lang, Tree tree) {
                Tree annTree = annotator.annotate(tree);
                Linearizer l = getLinearizer(lang);
                return unlex(lang, l.linearizeWithRanges(annTree));
        }


        /**
         *  Does type annotation and linearization with all available linearizers.
         */
        public List<Pair<String,String>> linearizeWithAll(Tree tree) {

                Tree annTree = annotator.annotate(tree);

                List<Pair<String,String>> rs = new LinkedList<Pair<String,String>>();
                for (String lang : linearizers.listLinearizers()) {
                        Linearizer l = linearizers.getLinearizer(lang);
                        if (l.tryThisOne()) {
                                rs.add(pair(lang, unlex(lang, l.linearize(annTree))));
                        }
                }
                return rs;
        }

	private String unlex(String toLang, List<Token> tokens) {
		return unlexers.get(toLang).unlex(tokens);
	}

        /**
         *  Translate a string from one language to another.
         */
        public Set<String> translate(String fromLang, String toLang, String text) {
                Set<String> rs = new TreeSet<String>();
                for (Tree tree : parse(fromLang, text)) {
                        try {
                                rs.add(linearize(toLang, tree));
                        } catch (GFException ex) {
                                ex.printStackTrace();
                        }
                }

                return rs;
        }

        public Set<Pair<String,String>> translateFromAll(String toLang, String text) {
                Set<Pair<String,String>> rs = new TreeSet<Pair<String,String>>();
                for (Pair<String,Tree> p : parseWithAll(text)) {
                        try {
                                rs.add(pair(p.fst, linearize(toLang, p.snd)));
                        } catch (GFException ex) {
                                ex.printStackTrace();
                        }
                }
                return rs;
	}

        public Set<Pair<String,String>> translateToAll(String fromLang, String text) {
		Set<Pair<String,String>> rs = new TreeSet<Pair<String,String>>();
		for (Tree tree : parse(fromLang, text)) {
			rs.addAll(linearizeWithAll(tree));
                }
		return rs;
	}

        public Set<Pair<Pair<String,String>,String>> translateFromAllToAll(String text) {
		Set<Pair<Pair<String,String>,String>> rs 
			= new TreeSet<Pair<Pair<String,String>,String>>();
		for (Pair<String,Tree> p : parseWithAll(text)) {
			for (Pair<String,String> l : linearizeWithAll(p.snd)) {
				rs.add(pair(pair(p.fst, l.fst), l.snd));
			}
		}
		return rs;
	}

        /**
         *  List all the available input languages.
         */
        public Set<String> listParsers() {
                return parsers.listParsers();
        }

        /**
         *  List all the available output languages.
         */
        public Set<String> listLinearizers() {
                return linearizers.listLinearizers();
        }

        /**
         *  Get the parser for the given language.
         */
        public Parser getParser(String lang) {
                return parsers.getParser(lang);
        }

        /**
         *  Get the linearizer for the given language.
         */
        public Linearizer getLinearizer(String lang) {
                return linearizers.getLinearizer(lang);
        }

}
