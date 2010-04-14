/*
 * KilburyParser.java
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

import java.util.*;
import java.io.PrintWriter;

import se.chalmers.cs.gf.util.MultiMap;
import se.chalmers.cs.gf.parse.Token;
import static se.chalmers.cs.gf.parse.ParseLogger.*;


/**
 *  A bottom-up chart parser.
 */
public class KilburyParser {

        private CFGrammar grammar;

        /** Maps categories to the rules starting with each category. */
        private MultiMap<Category,NonTerminalRule> rulesByFirstCat;

        /** Cached empty edges. Since this set is identical for every node
         *  we keep the set around to avoid reconstructing it.
         */
        private Set<EmptyEdge> emptyEdges;

        public KilburyParser(CFGrammar grammar) {
                this.grammar = grammar;

                this.rulesByFirstCat = new MultiMap<Category,NonTerminalRule>();
                for (NonTerminalRule r : grammar.getNonTerminalRules())
                        rulesByFirstCat.put(r.getRhs().get(0), r);

                this.emptyEdges = new HashSet<EmptyEdge>();
                for (NonTerminalRule r : grammar.getEmptyRules())
                        emptyEdges.add(new EmptyEdge(r));
        }

        public Chart parse(List<Token> input) {
                int n = input.size();
                Chart chart = new Chart(n);

                // process first node without adding any tokens
                handleNode(chart, 0);

                int k = 0;
                for (Token t : input) {
                        k++;
                        // add terminal edges for token between k-1 and k
                        scan(chart, t, k);
                        // create all edges ending at k
                        handleNode(chart, k);
                }
                assert k == n : k + ", " + n;

                log.fine("Finished parsing. " + chart.countPassiveEdges() 
                         + " passive edges and " + chart.countActiveEdges() 
                         + " active edges in the chart.");


                chart.purgeActive();

                return chart;
        }

        /**
         *  Use the scan rule to add edges for the token between
         *  nodes k-1 and k.
         */
        private void scan(Chart chart, Token t, int k) {
                for (TerminalRule r : grammar.matchingTerminalRules(t))
                        chart.add(new TerminalEdge(r, t), k-1, k);
        }

        private void handleNode(Chart chart, int k) {
                // add edges for all empty rules at node k
                chart.addPassiveEdges(emptyEdges, k, k);
                // iterate predict and combine on all passive
                // edges ending at k to the least fixpoint.
                boolean addedNew;
                do {
                        addedNew = false;
                        for (int j = 0; j <= k; j++) {
                                // FIXME: hack to avoid concurrent modification in the predict rule
                                Edge[] s = dumpEdges(chart.getPassive(j,k));
                                for (Edge e : s) {
                                        if (predict(chart, e, j, k))
                                                addedNew = true;
                                        if (combine(chart, e, j, k))
                                                addedNew = true;
                                }
                        }
                } while (addedNew);
        }

        /**
         *  Use the predict rule on a passive edge from j to k.
         *  @return true if a new edge was added, false otherwise.
         */
        private boolean predict(Chart chart, Edge e, int j, int k) {
                boolean addedNew = false;
                for (NonTerminalRule r : rulesByFirstCat.get(e.getCategory())) {
                        NonTerminalEdge f = new NonTerminalEdge(r);
                        f.stepForward();
                        if (chart.add(f, j, k))
                                addedNew = true;
                }
                return addedNew;
        }

        /**
         *  Use the combine rule on a passive edge from j to k.
         *  The combine rule adds an edge from i to k if
         *  there is a passive edge in category C from j to k,
         *  and an active edge from i to j, which now needs 
         *  something in the category C.
         *  @return true if a new edge was added, false otherwise.
         */ 
        private boolean combine(Chart chart, Edge e, int j, int k) {
                boolean addedNew = false;
                for (int i = 0; i <= j; i++) { 
                        // FIXME: hack to avoid concurrent modification
                        Edge[] s = dumpEdges(chart.getActive(i,j));
                        for (Edge f : s) {
                                if (f.needs(e.getCategory())) {
                                        NonTerminalEdge g = new NonTerminalEdge((NonTerminalEdge)f);
                                        g.stepForward();
                                        if (chart.add(g, i, k))
                                                addedNew = true;
                                }
                        }
                }
                return addedNew;
        }

        private Edge[] dumpEdges(Collection<? extends Edge> es) {
                return es.toArray(new Edge[es.size()]);
        }

}
