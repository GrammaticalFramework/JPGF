package se.chalmers.cs.pgf.parse.pmcfg;


import java.util.Set;
import java.util.Stack;

import se.chalmers.cs.pgf.util.PGFLogger;

public class PMCFGParser {
    public static PGFLogger log = new PGFLogger("se.chalmers.cs.pgf.parse");

	private PMCFGGrammar grammar;


	public PMCFGParser(PMCFGGrammar grammar)
	{
		this.grammar = grammar;
	}
	/** Main parsing function
	 * 
	 * @param strings : list of tokens
	 * @param startCat : start category
	 * @return final parsing chart
	 */
	public Chart parse(String[] strings, Category startCat) {
		log.fine("Started parsing");
		int n = strings.length;
		int d = grammar.max_dimension();
		Chart chart = new Chart(n, d);
		// Add initial productions in the chart
		for (Production p : grammar.getRules())
			chart.add(p);
		
		// initialize the chart with top category...
		Set<Production> rules = chart.getRule(startCat);
		for (Production p: rules) 
		{
			ActiveEdge e = new ActiveEdge(p, 0, 0);
			chart.add(e, 0, 0);
		}
		

		// process first node without adding any tokens
		compute(chart, 0);

		int k = 0;
		for (String t : strings) {
			k++;
			// add terminal edges for token between k-1 and k
			scan(chart, t, k);
			// create all edges ending at k
			compute(chart, k);
		}
		assert k == n : k + ", " + n;

		log.fine("Finished parsing. " + chart.countPassiveEdges() 
                 + " passive edges and " + chart.countActiveEdges() 
                 + " active edges in the chart.");
		
		//chart.purgeActive();
		return chart;
	}
	
	public Chart parse(String[] input)
	{
		return parse(input, grammar.getStartCategory());
	}

	private void scan(Chart chart, String t, int k)
	{
		log.fine("SCAN \"" + t + "\"	");
		for (int j = 0; j <= k-1; j++)
			for (ActiveEdge e : chart.getActive(j,k-1))
				if (e.needs(t)) {
					ActiveEdge f = new ActiveEdge(e);
					f.stepForward();
					chart.add(f, j, k);
				}
	}
	
	private void compute(Chart chart, int k)
	{
		Stack<ActiveEdge> agenda = new Stack<ActiveEdge>();
		// initialize the agenda with every active edge from some j to the actual k
		for (int j = 0; j <= k ; j++)
			agenda.addAll(chart.getActive(j,k));
		while (!agenda.isEmpty()) {
			ActiveEdge e = agenda.pop();
			combine(chart, agenda, e, k);
			complete(chart, agenda, e, k);
			predict(chart, agenda, e, k);
		}
	}
	
	/** Used with an active item e (ActiveEdge), combine looks for every possible passive items
	 * that can be combine with e and add the result of the operation to the agenda.
	 */
	private void combine(Chart chart, Stack<ActiveEdge> agenda, ActiveEdge e, int k)
	{
		log.finer("COMBINE " + e);
		if (e.isFinished()) return;
		for (int j = 0; j <= k; j++)
			for (PassiveEdge p : chart.getPassive(j,k))
			{
				log.finest("trying to combine " + e + " and " + p);
				combine(chart, agenda, e, p, k);
			}
	}
	
	private void combine(Chart chart, Stack<ActiveEdge> agenda, ActiveEdge e, PassiveEdge p, int k)
	{
		if (e.needs(p)) {
			int d = ((NonTerminal)e.getNext()).getNonTerminal()[0];
			ActiveEdge f = new ActiveEdge(e, d, p.getFreshCategory());
			f.stepForward();
			if (chart.add(f, e.getBegin(), k))
				agenda.add(f);
		}
	}

	private void predict(Chart chart, Stack<ActiveEdge> agenda, ActiveEdge e, int k)
	{
		log.finer("PREDICT " + e);
		if (e.isFinished()) return;
		RHSItem i = e.getNext();
		if (i.isTerminal()) return;
		int[] jl = i.getNonTerminal();
		log.finest("Getting domain " + jl[0] + " of rule " + e.getRule());
		Category Bd = e.getRule().getDomain(jl[0]);
		int l = jl[1];
		for (Production r : chart.getRule(Bd)) {
			ActiveEdge f = new ActiveEdge(r, l, 0);
			if (chart.add(f, k, k))
				agenda.add(f);
		}
	}
	
	/** Used with an active item e (ActiveEdge), combine looks for every possible passive items
	 * that can be combine with e and add the result of the operation to the agenda.
	 */
	private void complete(Chart chart, Stack<ActiveEdge> agenda, ActiveEdge e, int k)
	{
		int j = e.getBegin();
		log.finer("COMPLETE " + e + " (" + j + ")");
		if (!e.isFinished()) return;
		PassiveEdge f = new PassiveEdge(e);
		if (chart.add(f, j, k))
			for (int i = 0 ; i <= j ; i++) {
				ActiveEdge[] es = new ActiveEdge[chart.getActive(i, j).size()];
				int l = 0;
				for (ActiveEdge e12 : chart.getActive(i, j)) {
					es[l] = e12;
					l++;
				}
				for (ActiveEdge e1 : es)
					combine(chart, agenda, e1, f, k);
			}
		Category N = chart.getCategory(f, j, k);
		Production p = new Production(N, e.getRule());
		chart.add(p);
	}

}
