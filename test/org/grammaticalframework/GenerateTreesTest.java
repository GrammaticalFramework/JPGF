package org.grammaticalframework;

import java.util.*;
import org.grammaticalframework.Trees.Absyn.Tree;
import java.io.IOException;

public class GenerateTreesTest extends PGFTestCase {
    
    private PGF pgf;
    
    public GenerateTreesTest (String name) {
	super(name);
    }

    public void setUp() throws IOException {
	pgf = getPGF("Foods.pgf");
    }
    
    public void testGenerateTrees() {
	GenerateTrees generator = new GenerateTrees(this.pgf);
	String[] gold_ = new String[] {
	    "(Very Boring)",
	    "(Very Delicious)",
	    "(Very Expensive)",
	    "(Very Fresh)",
	    "(Very Italian)",
	    "(Very Warm)",
	    "Boring",
	    "Delicious",
	    "Expensive",
	    "Fresh",
	    "Italian",
	    "Warm"
	};
	
	Set<Tree> gold = new HashSet();
	for (String t: gold_)
	    gold.add(parseTree(t));
	
	Set<Tree> val = new HashSet(generator.generateTrees("Quality", 1));
	assertEquals(gold, val);
    }

    public void tearDown() {
	pgf = null;
    }
}
