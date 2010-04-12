/*
 * TreeBuilder.java
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

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.util.*;

import java.util.*;

/**
 *  Builds abstract syntax trees from a parsing chart.
 */
public class TreeBuilder {

        protected static GFLogger log = new GFLogger("se.chalmers.cs.gf.parse.TreeBuilder");

        /**
         *  Keeps edges by category to speed up iteration through all edges of
         *  one category.
         *  FIXME: hacky: this could use some generalisation of Chart
         */
        private MultiMap<Category,Edge>[][] chart;

        /**
         *  Trees which have already been built.
         */
        private Map<Category,Set<Tree>>[][] trees;

        /** The length of the input (size of the chart) */
        private int n;

        public TreeBuilder(Chart edgeChart) {
                n = edgeChart.getSize();

                chart = (MultiMap<Category,Edge>[][])new MultiMap<?,?>[n+1][n+1];
                for (int i = 0; i <= n; i++) {
                        for (int j = i; j <= n; j++) {
                                chart[i][j] = new MultiMap<Category,Edge>();
                                for (Edge e : edgeChart.getPassive(i,j))
                                        chart[i][j].put(e.getCategory(), e);
                        }
                }

                trees = (Map<Category,Set<Tree>>[][])new Map<?,?>[n+1][n+1];
                for (int i = 0; i <= n; i++)
                        for (int j = i; j <= n; j++)
                                trees[i][j] = new HashMap<Category,Set<Tree>>();
        }

        private Set<Edge> catEdgesBetween(Category c, int i, int j) {
		return chart[i][j].get(c);
        }

        /**
         *  Get all trees for the given category spanning the entire input.
         */
        public Set<Tree> buildTree(Category cat, boolean robust) {
                return memo_buildTree(cat, 0, n, Collections.<Rule>emptySet(), robust);
        }

        /** Dummy set which marks that the search for a particular category
         *  at some position is in progress.
         */
        private static final Set<Tree> in_progress 
          = Collections.unmodifiableSet(new HashSet<Tree>());

        /**
         *  Get all trees for the given category between nodes i and j.
         *  Memoizes the results of buildTree().
         */
        private Set<Tree> memo_buildTree(Category cat, int i, int j, Set<Rule> used, boolean robust) {
                Set<Tree> ts = trees[i][j].get(cat);
                if (ts == null) {
                        // this category and pos has not been asked for before
                        // mark this category and pos as in progress
                        trees[i][j].put(cat, in_progress);
                        // build all trees, with no forbidden rules
                        ts = buildTree(cat, i, j, Collections.<Rule>emptySet(), robust);
                        // remember result
                        trees[i][j].put(cat, ts);
                } else if (ts == in_progress) {
                        // we are in a recursive call of something with
                        // the same category and pos. build the set again,
                        // possibly with forbidden rules. we can't return the
                        // old results, since they are not computed yet.
                        ts = buildTree(cat, i, j, used, robust);
                } // else we already had the result

                return ts;
        }

        /**
         *  Get all trees for the given category between nodes i and j.
         *  @param used The set of all edges already used between nodes i and j.
         */
        private Set<Tree> buildTree(Category cat, int i, int j, Set<Rule> used, boolean robust) {
                assert i <= j : i + ", " + j;
                
                Set<Tree> ts = new HashSet<Tree>();

		Set<Edge> es = Collections.<Edge>emptySet();

		if (robust) {
			OUTER:
			for (int l = j - i; l >= 0; l--) {
				for (int s = j - l; s >= i; s--) {
					es = catEdgesBetween(cat,s,s+l);
					if (!es.isEmpty()) {
						i = s;
						j = s+l;
						break OUTER;
					}
				}
			}
		} else {
			es = catEdgesBetween(cat,i,j);	
		}

                for (Edge e : es) {
                        log.finest("Considering ("+i+","+j+") " + e);
                                
                        // FIXME: inelegant
                        if (e instanceof EmptyEdge) {
                                NonTerminalRule r = ((EmptyEdge)e).getRule();
                                Tree t = r.makeTree(new Tree[0], i, j);
                                if (t != null)
                                        ts.add(t);
                        } else if (e instanceof TerminalEdge) {
                                TerminalEdge te = (TerminalEdge)e;
                                TerminalRule r = te.getRule();
                                ts.add(r.makeTree(te.getToken(), i, j));
                        } else {
                                NonTerminalRule r = ((NonTerminalEdge)e).getRule();
                                if (!used.contains(r)) {
                                        Set<Rule> newUsed = new HashSet<Rule>(used);
                                        newUsed.add(r);
                                        Set<List<Tree>> css = buildTrees(r.getRhs(), i, j, newUsed);
                                        for (List<Tree> cs : css) {
                                                Tree[] children = keepNonTerminals(cs);
                                                Tree t = r.makeTree(children, i, j);
                                                if (t != null) {
                                                        boolean addedNew = ts.add(t);
                                                        if (!addedNew) 
                                                                log.finest(t + " has already been built");
                                                }
                                        }
                                } else {
                                        log.finest("We have seen " + e + " before");
                                }
                        }
                }


                if (!cat.getName().startsWith("$")) {
                        log.finest("Built " + ts.size() + " trees for category '" 
                                   + cat + "' between " + i + " and " + j);
                }

                return ts;
        }

        /**
         *  Keep all trees that correspond to non-terminal productions.
         */
        private Tree[] keepNonTerminals(List<Tree> ts) {
                List<Tree> r = new ArrayList<Tree>(ts.size());
                for (Tree t : ts)
                        if (treeIsNonTerminal(t))
                                r.add(t);
                return r.toArray(new Tree[r.size()]);
        }

        /**
         *  Check if a tree corresponds to a non-terminal production.
         *  FIXME: hackish
         */
        private boolean treeIsNonTerminal(Tree t) {
                return t.isLiteral() ||
                        (t instanceof Fun && !((Fun)t).getLabel().startsWith("$"));
        }

        /**
         *  Get all tree lists for the given symbols between i and j.
         *  Each element in the result is one of the ambigous parses. Each such element
         *  is a list of trees that together cover the input from i to j and where
         *  each tree corresponds to one of the given symbols.
         *  @param ss A non-empty list of symbols to match.
         *  @param used The set of all edges already used between nodes i and j.
         */
        private Set<List<Tree>> buildTrees(List<Category> ss, int i, int j, Set<Rule> used) {
                assert !ss.isEmpty();
                assert i <= j : i + ", " + j;

                Set<List<Tree>> r = null;

                // head is the first category that we are looking for
                Category head = ss.get(0);
                // tail is the rest of the categories
                List<Category> tail = ss.subList(1, ss.size());

                if (tail.isEmpty()) {
                        // we are only looking for the head category,
                        // so for each parse, we get a singleton sequence
                        for (Tree h : memo_buildTree(head, i, j, used, false)) {
                                if (r == null)
                                        r = new HashSet<List<Tree>>();
                                r.add(Arrays.asList(h));
                        }
                } else {
                        // go through all possible first edges
                        // k is the end index of the first edge
                        for (int k = i; k <= j; k++) {
                                // build all possible trees for the head category between i and k
                                Set<Rule> usedH = (k == j) ? used : Collections.<Rule>emptySet();
				// only the top-level call to buildTree should use robustness,
				// otherwise we get strange spurious results
                                Set<Tree> hts = memo_buildTree(head, i, k, usedH, false);

                                // if there are no trees for the head category, there
                                // is no point in looking for the rest.
                                if (hts.isEmpty())
                                        continue;

                                // build all possible tree lists for the tail categories
                                Set<Rule> usedT = (k == i) ? used : Collections.<Rule>emptySet();
                                Set<List<Tree>> rss = buildTrees(tail, k, j, usedT);
                                // make all combinations of head tree and the rest of the trees.
                                for (Tree h : hts) {
                                        for (List<Tree> rs : rss) {
                                                List<Tree> ts = new LinkedList<Tree>(rs);
                                                ts.add(0, h);
                                                if (r == null)
                                                        r = new HashSet<List<Tree>>();
                                                boolean addedNew = r.add(ts);
                                                if (!addedNew) 
                                                        log.finest(ts + " has already been added");
                                        }
                                }
                        }
                }

                return r == null ? Collections.<List<Tree>>emptySet() : r;
        }
                        
}
