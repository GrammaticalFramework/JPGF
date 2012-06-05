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
    
    public void testCombine() {
	Set<String> s1 = new HashSet();
	s1.add("a");
	s1.add("b");
	Set<String> s2 = new HashSet();
	s2.add("c");
	s2.add("d");
	Set<String> s3 = new HashSet();
	s3.add("e");
	s3.add("f");

	Set<List<String>> gold = new HashSet();
	gold.add(Arrays.asList(new String[] {"a", "c", "e"}));
	gold.add(Arrays.asList(new String[] {"a", "c", "f"}));
	gold.add(Arrays.asList(new String[] {"a", "d", "e"}));
	gold.add(Arrays.asList(new String[] {"a", "d", "f"}));
	gold.add(Arrays.asList(new String[] {"b", "c", "e"}));
	gold.add(Arrays.asList(new String[] {"b", "c", "f"}));
	gold.add(Arrays.asList(new String[] {"b", "d", "e"}));
	gold.add(Arrays.asList(new String[] {"b", "d", "f"}));
	
	Set<String>[] arg = new Set[] {s1, s2, s3};
	Set<List<String>> val = new HashSet(GenerateTrees.combine( arg ));
	assertEquals(gold, val);
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
