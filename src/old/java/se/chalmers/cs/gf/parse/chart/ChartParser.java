/*
 * ChartParser.java
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

import se.chalmers.cs.gf.abssyn.Tree;
import se.chalmers.cs.gf.parse.*;
import se.chalmers.cs.gf.parse.lex.Lexer;
import static se.chalmers.cs.gf.parse.ParseLogger.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 *  Combines lexing, kilbury parsing and tree building.
 */
public class ChartParser implements Parser {

        private Lexer lexer;

        private CFGrammar grammar;

        private KilburyParser parser;

	private boolean robust;

        public ChartParser(Lexer lexer, CFGrammar grammar, boolean robust) {
                this.lexer = lexer;
                this.grammar = grammar;
                this.parser = new KilburyParser(grammar);
		this.robust = robust;

                log.fine("Created chart parser for " + grammar.getName()
                         + " using lexer " + lexer.getClass().getName());
	       
        }

	/**
	 *  Parse the given string in the start category of
	 *  the grammar. Uses the lexer given to the constructor.
	 */
        public Collection<Tree> parse(String str) {
                return parse(str, grammar.getStart());
        }

	/**
	 *  Parse the given list of tokens in the start category of
	 *  the grammar. 
	 */
        public Collection<Tree> parse(List<Token> tokens) {
                return parse(tokens, grammar.getStart());
	}

	/**
	 *  Parse the given string in the given category. 
	 *  Uses the lexer given to the constructor.
	 */
        public Collection<Tree> parse(String str, String cat) {
                return parse(str, new Category(cat));
        }

	/**
	 *  Parse the given string in the given category. 
	 *  Uses the lexer given to the constructor.
	 */
        public Collection<Tree> parse(String str, Category cat) {
		List<Token> tokens = lexer.lex(str);

		if (robust)
			tokens = removeUnknown(tokens);

		return parse(tokens, cat);
        }

	private List<Token> removeUnknown(List<Token> tokens) {
		Set<String> known = grammar.getKnownWords();
		List<Token> tokens_ = new ArrayList<Token>();
		for (Token t : tokens)
			if (!t.isWord() || known.contains(t.getValue()))
				tokens_.add(t);
			else
				log.fine("Dropping unknown word: " + t);
		return tokens_;
	}

	/**
	 *  Parse the given list of tokens. This does not use the
	 *  lexer given to the constructor.
	 */
        public Collection<Tree> parse(List<Token> tokens, Category cat) {
                log.fine("Parsing in category " + cat 
                          + ", grammar " + grammar.getName() + ".");

                log.finer("Tokens: " + tokens);

                Chart chart = parser.parse(tokens);

		if (log.finestIsLoggable()) {
			log.finest(chart.toString());
		}

                log.fine("Building trees.");

                Collection<Tree> trees = new TreeBuilder(chart).buildTree(cat, robust);

                log.fine("Built " + trees.size() + " trees.");

                log.finer("Trees: " + trees);

                return trees;	
	}

        private static void ln(StringBuilder a, CharSequence s) {
                a.append(s).append('\n');
        }

        public String getName() {
                return grammar.getName();
        }

        public boolean tryThisOne() {
                return true;
        }

}
