/* AbstractTest.java
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
