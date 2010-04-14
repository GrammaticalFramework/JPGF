package se.chalmers.cs.pgf.parse.pmcfg;

import se.chalmers.cs.pgf.abssyn.*;

import java.util.HashSet;
import java.util.Set;

/** This is the class responsible for building an abstract syntax tree from the chart.
 * 
 * @author gdetrez
 *
 */
public class TreeBuilder {
	static public Set<ASTree> buildTrees(Chart chart, Category startCat)
	{
		Set<ASTree> result = new HashSet<ASTree>();
		int n = chart.getSize();
		for( PassiveEdge e : chart.getPassive(0, n))
		{
			
		}
		return result;
	}
}
