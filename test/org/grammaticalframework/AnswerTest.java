package org.grammaticalframework;

import org.grammaticalframework.Trees.Absyn.Tree;

import java.io.IOException;

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
		
	String ex1 = "Take bus 10 from Valand Göteborg to Chalmers Göteborg at 1";
	Tree tree1 = parseTree("((((Routing Bus) St_1) St_0) N1)");
	String lin1 = linearizer.linearizeString(tree1);
	assertEquals(ex1,lin1);

	System.out.println(lin1);

    }

    public void tearDown() {
	pgf = null;
    }
}
