package org.grammaticalframework;

import org.grammaticalframework.Trees.Absyn.Tree;

import java.io.IOException;
import java.util.*;

public class AnswerTest extends PGFTestCase
{

    public AnswerTest (String name) {
	super(name);
    }

    PGF pgf;

    public void setUp() throws IOException {
	pgf = getPGF("Answer.pgf");
    }

    public void testLinearization()
	throws UnknownLanguageException, Linearizer.LinearizerException
    {
	Linearizer linearizer = new Linearizer(pgf, "AnswerEng");
	
	//	Tree[] trees = trans.parse("AnswerEng", "Take bus 10 from Valand Göteborg to Chalmers Göteborg at 1");
	// System.out.println(trees[0]);
		
	// Since this is using (and testing) the implementation of variants,
	// there is everal different linearization possibilities
	Set<String> gold = new HashSet<String>();
	gold.add("Take bus 10 from Valand to Chalmers at 1");
	gold.add("Take bus 10 from Valand Göteborg to Chalmers at 1");
	gold.add("Take bus 10 from Valand to Chalmers Göteborg at 1");
	gold.add("Take bus 10 from Valand Göteborg to Chalmers Göteborg at 1");
	Tree tree1 = parseTree("((((Routing Bus) St_1) St_0) N1)");
	String lin1 = linearizer.linearizeString(tree1);
	assertTrue(gold.contains(lin1));

    }

    public void tearDown() {
	pgf = null;
    }
}
