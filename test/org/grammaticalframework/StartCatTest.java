/* StartCatTest.java
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
import org.grammaticalframework.Trees.PrettyPrinter;
import org.grammaticalframework.parser.*;
import java.io.IOException;

/** This test the ability of the parser t use different start categories **/
public class StartCatTest extends PGFTestCase
{

    public StartCatTest (String name) {
	super(name);
    }

    PGF pgf;

    public void setUp() throws IOException {
	pgf = getPGF("Foods.pgf");
    }

    public void testComment() throws UnknownLanguageException, ParseError {
	Parser parser = new Parser(pgf, "FoodsEng");
	parser.setStartcat("Comment");
	String ex1 = "this fresh pizza is Italian";
	Tree tree1 = parseTree("((Pred (This ((Mod Fresh) Pizza))) Italian)");
	Tree[] trees1 = parser.parse(ex1).getTrees();
	assertTrue(trees1.length==1);
	assertEquals(trees1[0],tree1);

	String ex2 = "those boring fish are expensive";
	Tree tree2=parseTree("((Pred (Those ((Mod Boring) Fish))) Expensive)");
	Tree[] trees2 = parser.parse(ex2).getTrees();
	assertTrue(trees2.length==1);
	assertEquals(trees2[0],tree2);
    }

    public void testItem() throws UnknownLanguageException, ParseError {
	Parser parser = new Parser(pgf, "FoodsEng");
	parser.setStartcat("Item");
	String ex1 = "this fresh pizza";
	Tree tree1 = parseTree("(This ((Mod Fresh) Pizza))");
	Tree[] trees1 = parser.parse(ex1).getTrees();
	assertTrue(trees1.length==1);
	assertEquals(trees1[0],tree1);

	String ex2 = "those boring fish";
	Tree tree2=parseTree("(Those ((Mod Boring) Fish))");
	Tree[] trees2 = parser.parse(ex2).getTrees();
	assertTrue(trees2.length==1);
	assertEquals(trees2[0],tree2);
    }


    public void tearDown() {
	pgf = null;
    }
}
