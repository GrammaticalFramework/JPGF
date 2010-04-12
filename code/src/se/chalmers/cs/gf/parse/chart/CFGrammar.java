/*
 * CFGrammar.java
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

import static se.chalmers.cs.gf.parse.ParseLogger.*;
import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.parse.Token;
import se.chalmers.cs.gf.util.MultiMap;

import java.util.*;

/**
 *  A context-free grammar.
 */
public class CFGrammar {

        private String name;

        private Category start;

        private Set<NonTerminalRule> nonTerminalRules;

        private MultiMap<String,TerminalRule> terminalRules;

        private TerminalRule stringLiteralRule;

        private TerminalRule intLiteralRule;

        private Set<NonTerminalRule> emptyRules;

        private Set<String> knownWords;

        public CFGrammar(String name, Category start) {
                this.name = name;
                this.start = start;
                this.nonTerminalRules = new HashSet<NonTerminalRule>();
                this.terminalRules = new MultiMap<String,TerminalRule>();
                this.stringLiteralRule = new StringLiteralRule();
                this.intLiteralRule = new IntLiteralRule();
                this.emptyRules = new HashSet<NonTerminalRule>();
                this.knownWords = new HashSet<String>();
        }

        public String getName() {
                return name;
        }

        public Category getStart() {
                return start;
        }

        /**
         *  Get the rules with only non-terminals in the RHS.
         */
        public Collection<NonTerminalRule> getNonTerminalRules() {
                return nonTerminalRules;
        }

        /**
         *  Get the terminal rules which match a given token.
         */
        public Collection<TerminalRule> matchingTerminalRules(Token t) {
                Set<TerminalRule> rs = terminalRules.get(t.getValue());

                boolean isString = matchesStringLiteralRule(t);
                boolean isInt = matchesIntLiteralRule(t);
                if (isString || isInt) {
                        rs = new HashSet<TerminalRule>(rs);
                        if (isString)
                                rs.add(stringLiteralRule);
                        if (isInt)
                                rs.add(intLiteralRule);
                }

                return rs;
        }

        /**
         *  Get the set of words for which there are terminal
         *  rules in the grammar. This does not include integer or
         *  string literals.
         */
        public Set<String> getKnownWords() {
                return Collections.unmodifiableSet(knownWords);
        }

        /**
         *  A token matches a string literal rule if it is a string literal
         *  or an unknown word.
         */
        private boolean matchesStringLiteralRule(Token token) {
                return token.getType() == Token.Type.STRING
                        || (token.getType() == Token.Type.WORD
                            && !knownWords.contains(token.getValue()));
        }

        public boolean matchesIntLiteralRule(Token token) {
                return token.getType() == Token.Type.INT;
        }


        /**
         *  Get the empty rules.
         */
        public Collection<NonTerminalRule> getEmptyRules() {
                return emptyRules;
        }

        /**
         *  Add a rule to the grammar.
         */
        public void addRule(Category cat, List<Symbol> rhs, String fun, List<Profile> profile) {
                List<Category> rhsc = new LinkedList<Category>();
                
                for (Symbol s : rhs) {
                        if (s instanceof Terminal) {
                                Terminal t = (Terminal)s;
                                // FIXME: special case for rules that are already single-terminal rule?
                                Category c = addTerminal(t);
                                rhsc.add(c);
                        } else if (s instanceof Category) {
                                rhsc.add((Category)s);
                        } else 
                                assert false;
                }
                
                NonTerminalRule r = new NonTerminalRule(cat, rhsc, fun, profile);

                if (rhs.isEmpty()) { 
                        if (!emptyRules.add(r))
                                log.finest("In grammar " + name 
                                            + ": duplicate empty rule " + r);
                        log.finest("Added empty rule: " + r);
                } else {
                        if (!nonTerminalRules.add(r))
                                log.finest("In grammar " + name 
                                            + ": duplicate non-terminal rule " + r);
                        log.finest("Added non-terminal rule: " + r);
                }
        }

        /**
         *  Add a terminal rule.
         */
        private Category addTerminal(Terminal t) {
                Category cat = makeTerminalCategory(t);
                TerminalRule r = new TerminalRule(cat, t);
                terminalRules.put(t.getToken(), r);
                knownWords.add(t.getToken());
                log.finest("Added terminal rule: " + r);
                return cat;
        }

        private Category makeTerminalCategory(Terminal t) {
                return new Category("$" + t.toString()); //FIXME: might have to be more advanced
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("grammar ").append(name).append("\n");
                sb.append("startcat ").append(start).append("\n");
                for (NonTerminalRule r : nonTerminalRules) 
                        sb.append(r.toString()).append("\n");
                for (TerminalRule r : terminalRules.values()) 
                        sb.append(r.toString()).append("\n");
                sb.append(stringLiteralRule.toString()).append("\n");
                sb.append(intLiteralRule.toString()).append("\n");
                sb.append("end grammar\n");
                return sb.toString();
        }
}
