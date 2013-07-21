/* GenerateTreesTest.java
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
