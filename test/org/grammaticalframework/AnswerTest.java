/* AnswerTest.java
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
