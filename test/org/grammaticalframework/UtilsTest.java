package org.grammaticalframework;

import junit.framework.TestCase;
import java.util.*;

public class UtilsTest extends TestCase {
    public UtilsTest (String name) {
	super(name);
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
	Set<List<String>> val = new HashSet(Utils.combine( arg ));
	assertEquals(gold, val);
    }
}
