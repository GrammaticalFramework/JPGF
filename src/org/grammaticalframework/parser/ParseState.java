/* ParseState.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework.parser;


import org.grammaticalframework.reader.*;
import org.grammaticalframework.Trees.Absyn.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.Collection;
import java.util.Stack;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.Option;

public class ParseState {

    final private Concrete grammar;
    final private CncCat startcat;
    final private Chart chart = new Chart(100); // TODO 100 is a bad value...
    private ParseTrie trie = new ParseTrie();
    private Stack<ActiveItem> agenda = new Stack();
    private int position = 0;
    // 'active' is the set of all the S_k's, holding the active items which are not
    // on the agenda.
    private Vector<ActiveSet> active = new Vector();

    public ParseState(final Concrete grammar, final String abstractStartcat)
	throws ParseError
    {
	this.grammar = grammar;
	try {
	    this.startcat = grammar.concreteCats(abstractStartcat);
	} catch (Concrete.UnknownCategory e) {
	    throw new ParseError("Invalid start category " + abstractStartcat);
	}
	// Adding all grammar productions in the chart
	for (Production p: grammar.productions())
	    chart.addProduction(p);

	// We create an initial agenda of all prodution with the
	// start category as left-hand-side.
	for (int id = startcat.firstID() ; id <= startcat.lastID() ; id++)
	    for (ApplProduction prod: chart.getProductions(id)) {
		ActiveItem it = new ActiveItem(0, id, prod.function, prod.domain, 0, 0);
		agenda.push(it);
	    }

	// Finally we process this agenda
	compute();
    }

    private void compute() {
	// We have to create a new set S_k where k is the current position 
	active.add(new ActiveSet());
	while (!agenda.isEmpty()) {
	    processActiveItem(agenda.pop());
	}
    }

    private void processActiveItem(ActiveItem item) {
	int j = item.begin();
	int A = item.category();
	CncFun f = item.function();
	int[] B = item.domain();
	int l = item.constituent();
	int p = item.position();
	
	

	if (!item.hasNextSymbol()) {
	    // ------------------------- at the end --------------------------
	    //log.finest("Case at the end")
	    if (chart.hasCategory(A, l, j, this.position)) {
		int n = chart.getCategory(A, l, j, this.position).get().intValue();
		scala.collection.Iterator<scala.Tuple3<ActiveItem,Integer,Integer>> oldactive = active.get(this.position).get(n);
		while(oldactive.hasNext()) {
		    scala.Tuple3<ActiveItem, Integer, Integer> t = oldactive.next();
		    int r = t._3().intValue();
		    // (xprime, dprime, r)
		    ActiveItem i = new ActiveItem(new Integer(this.position),
						  new Integer(n), f, B, r,
						  new Integer(0));
		    //log.finest("Adding "+ i + " to the agenda")
		    agenda.push(i);
		}
		chart.addProduction(n, f, B);
	    } else {
		int N = chart.generateFreshCategory(A, l, j, this.position);
		scala.collection.Iterator<scala.Tuple2<ActiveItem,Integer>> oldactive = active.get(j).get(A,l).iterator();
		while (oldactive.hasNext()) {
		    scala.Tuple2<ActiveItem,Integer> t = oldactive.next();
		    ActiveItem ip = t._1();
		    int d = t._2().intValue();
		    //log.finest("combine with " + ip + " (" + d + ")")
		    int[] domain = ip.domain().clone();
		    domain[d] = N;
		    ActiveItem i = new ActiveItem(ip.begin(), ip.category(),
		    				  ip.function(), domain, 
		    				  ip.constituent(), ip.position() + 1);
		    //log.finest("Adding " + i + " to the agenda")
		    agenda.push(i);
		}
		chart.addProduction(N, f, B);
	    }
	} else {
	    Symbol next = item.nextSymbol2();
	    if (next instanceof ToksSymbol) {
		// ------------------------- before s∈ T -------------------------
		ToksSymbol tok = (ToksSymbol)next;
		//log.fine("Case before s∈ T")
		String[] tokens = tok.tokens();
		ActiveItem i = new ActiveItem(j, A, f, B, l, p + 1);
		// SCAN
		// this.trie.add(tokens, i)
		Option<Stack<ActiveItem>> option = this.trie.lookup(tokens);
		if (option.isEmpty()) {
		    Stack<ActiveItem> a = new Stack();
		    a.push(i);
		    this.trie.add(tokens,a);
		} else {
		    option.get().push(i);
		}
	    } else {
		// ------------------------- before <d,r> -----------------------
		ArgConstSymbol arg = (ArgConstSymbol)next;
		//log.finest("Case before <d,r>")
		int d = arg.arg();
		int r = arg.cons();
		int Bd = item.domain()[d];
		if (this.active.get(this.position).add(Bd,r,item,d)) {
		    for (ApplProduction prod: chart.getProductions(Bd)) {
			ActiveItem it = new ActiveItem(this.position, Bd, prod.function,
						       prod.domain, r, 0);
			    agenda.push(it);
		    }
		}
		Option<Integer> oCat = chart.getCategory(Bd,r, this.position, this.position);
		if (!oCat.isEmpty()) {
		    int catN = oCat.get().intValue();
		    int[] newDomain = B.clone();
		    newDomain[d] = catN;
		    ActiveItem it = new ActiveItem(j, A, f, newDomain, l, p + 1);
		    //log.finest("Adding " + it + " to the agenda");
		    agenda.push(it);
		}
	    }
	}
    }

    /**
     * returns the set of possible next words
     * */
    public String[] predict() {
	return this.trie.predict();
    }

    public Tree[] getTrees() {
	scala.collection.Iterator<org.grammaticalframework.intermediateTrees.Tree> parseTrees = TreeBuilder.buildTrees(chart, startcat, position).iterator();
	Vector<Tree> v = new Vector();
	while (parseTrees.hasNext())
	    v.add(TreeConverter.intermediate2abstract(parseTrees.next()));
	return v.toArray(new Tree[]{});
    }

    public boolean scan(String token) {
	scala.Option<ParseTrie> option = this.trie.getSubTrie(token);
	if (option.isEmpty()) 
	    return false;
	else {
	    ParseTrie newTrie = option.get();
	    List nil = Nil$.MODULE$;
	    scala.Option<Stack<ActiveItem>> option2 = newTrie.lookup(nil);
	    if (option2.isEmpty())
		return false;
	    else {
		Stack<ActiveItem> agenda = option2.get();
		this.trie = newTrie;
		this.position += 1;
		this.agenda = agenda;
		this.compute();
		//log.finer(this.trie.toString)
		return true;
	    }
	}
    }

    public String toString() {
	return "= ParseState =\n" +
	    "== Chart ==\n" +
	    this.chart.toString() +
	    "== Trie ==\n" +
	    this.trie.toString();
    }
}
