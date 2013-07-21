/* FoodsLinearizeTest.java
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

public class FoodsLinearizeTest extends PGFTestCase
{

    public FoodsLinearizeTest (String name) {
	super(name);
    }

    PGF pgf;

    public void setUp() throws IOException {
	pgf = getPGF("Foods.pgf");
    }

    public void testFoodsEng()
	throws UnknownLanguageException, Linearizer.LinearizerException
    {
	Linearizer linearizer = new Linearizer(pgf, "FoodsEng");

	String ex1 = "this fresh pizza is Italian";
	Tree tree1 = parseTree("((Pred (This ((Mod Fresh) Pizza))) Italian)");
	String lin1 = linearizer.linearizeString(tree1);
	assertEquals(ex1,lin1);

	String ex2 = "those boring fish are expensive";
	Tree tree2=parseTree("((Pred (Those ((Mod Boring) Fish))) Expensive)");
	String lin2 = linearizer.linearizeString(tree2);
	assertEquals(ex2,lin2);
    }

    public void testFoodsSwe()
	throws UnknownLanguageException, Linearizer.LinearizerException
    {
	Linearizer linearizer = new Linearizer(pgf, "FoodsSwe");

	Tree tree1 = parseTree("((Pred (This ((Mod Delicious) Pizza))) Fresh)");
	String ex1 = "den här läckra pizzan är färsk";
	String lin1 = linearizer.linearizeString(tree1);
	assertEquals(ex1,lin1);
    }

    public void testFoodsIta()
	throws UnknownLanguageException, Linearizer.LinearizerException
    {
	Linearizer linearizer = new Linearizer(pgf, "FoodsIta");

	String ex1 = "questa pizza deliziosa è fresca";
	Tree tree1 = parseTree("((Pred (This ((Mod Delicious) Pizza))) Fresh)");
	String lin1 = linearizer.linearizeString(tree1);
	assertEquals(ex1,lin1);
    }


    public void tearDown() {
	pgf = null;
    }
}
