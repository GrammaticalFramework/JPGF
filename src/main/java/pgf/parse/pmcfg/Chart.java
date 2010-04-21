/* Chart.java
 * Copyright (C) 20010, Grégoire Détrez <gdetrez@crans.org>
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
package pgf.parse.pmcfg;

import java.util.HashSet;
import java.util.Set;
import pgf.util.MultiMap;
import pgf.util.PGFLogger;

/** The chart is the main data structure used in parsing.
 * It keeps track of active items, passive items and productions created
 * during parsing. It is also the basis to build the abstract trees after
 * parsing.
 * @author gdetrez
 *
 */
public class Chart extends ScalaChart {
    public static PGFLogger log = new PGFLogger("se.chalmers.cs.pgf.parse");
    private int n;
    
    private Set<ActiveEdge>[][] active;
    private Set<PassiveEdge>[][] passive;
    private MultiMap<Integer,Production> productions;
    
    /** **********************************************************************
     * constructors
     */
    
    @SuppressWarnings("unchecked")
    public Chart(int n, int d)
    {
	// TODO: this is not supposed to be zero...
	super(0);
	log.fine("New chart with size : " + n);
	this.n = n;
	// causes an unchecked cast warning. The cast is necessary
	// since we cannot create arrays of parameterized types.
	// See section 7.3 of
	// http://java.sun.com/j2se/1.5/pdf/generics-tutorial.pdf
	// FIXME: array is almost twice as big as a it needs to be.
	//        could fix this with a non-square array
	this.active = (Set<ActiveEdge>[][])new Set<?>[n+1][n+1];
	for (int i = 0; i <= n; i++)
	    for (int j = i; j <= n; j++)
		this.active[i][j] = new HashSet<ActiveEdge>();
	this.passive = (Set<PassiveEdge>[][])new Set<?>[n+1][n+1];
	for (int i = 0; i <= n; i++)
	    for (int j = i; j <= n; j++)
		this.passive[i][j] = new HashSet<PassiveEdge>(); 
	this.productions = new MultiMap<Integer, Production>();
    }

    
    /** **********************************************************************
     * 	Handling Passive Edges
     */
	
    /** Adding a passive edge cause the chart to look in the passive edges 
     * to see if this one is not already included, if so, it is not 
     * included again otherwise it is.
     * @param p
     * @return true if the edge have been added, false otherwise
     */
    public boolean add(PassiveEdge p, int j, int k) {
	int l = p.constituentIndex();
	if (passive[j][k].add(p)) {
	    log.finer("Added passive edge to the chart : " + 
		      p + " at " + j + ", " + k);
	    return true;
	} 
	else
	    return false;
    }
    
    public Set<PassiveEdge> getPassive(int j, int k) {
	return this.passive[j][k];
	
    }
    
    public int getCategory(PassiveEdge e, int j, int k) {
	for (PassiveEdge f : passive[j][k])
	    {
		if (f.equals(e))
		    return f.freshCategory();
	    }
	throw new IllegalStateException("No category for passive edge");
    }
    
    public int countPassiveEdges() {
	int count = 0;
	for(Set<PassiveEdge>[] a : this.passive)
	    for (Set<PassiveEdge> s : a)
		if (s != null)
		    count += s.size();
	return count;
    }
    
    /** **********************************************************************
     * 	Handling Active Edges
     */
    public boolean add(ActiveEdge x, int j, int k) {
	x.setBegin(j);
	if (active[j][k].add(x))
	    {
		log.finer("Added active edge to the chart : " + 
			  x + " at " + j + ", " + k);
		return true;
	    } else
	    return false;
    }
    
    public Set<ActiveEdge> getActive(int j, int k) {
	return active[j][k];
    }
    
    public int countActiveEdges() {
	int count = 0;
	for(Set<ActiveEdge>[] a : this.active)
	    for (Set<ActiveEdge> s : a)
		if (s != null)
		    count += s.size();
	return count;
    }
    
    /** **********************************************************************
     * 	Handling Productions
     */
    // public boolean add(Production p) {
    // 	if (this.productions.put(p.getCategory(), p)) {
    // 		log.finest("Adding production " + 
    // 			   p.getCategory() + " -> " + p + " in chart.");
    // 		return true;
    // 	    } else return false;
    // }
    
    // public Set<Production> getRule(int leftCategory) {
    // 	return this.productions.get(new Integer(leftCategory));
    // }
    
    public int getSize() {
	return n;
    }
}
