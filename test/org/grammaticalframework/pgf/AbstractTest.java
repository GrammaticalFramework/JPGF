package org.grammaticalframework.pgf;

import junit.framework.TestCase;
import org.grammaticalframework.reader.*;
import java.util.*;

public class AbstractTest extends PGFTestCase {

    private Abstract mAbstract;

    public AbstractTest(String name) {
	super(name);
    }
    
    public void setUp() {
	mAbstract = new Abstract("Test", new HashMap(),
				 new AbsFun[] { 
				     mkFunction("f", new String[] {}, "S"),
				     mkFunction("g", new String[] {}, "S"),
				     mkFunction("h", new String[] {}, "T")
				 },
				 new AbsCat[] {
				     mkCategory("S", new String[] {"f", "g"}),
				     mkCategory("T", new String[] {"h"})
				 });
    }

    public void testFunctions() {
	Set<String> s1 = new HashSet();
	s1.add("f"); s1.add("g");
	Set<String> s2 = new HashSet();
	for (AbsFun f: mAbstract.functions("S"))
	    s2.add(f.name);
	assertEquals(s1,s2);
    }

    public void tearDown() {
	mAbstract = null;
    }
}
