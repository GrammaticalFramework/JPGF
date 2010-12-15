package org.grammaticalframework;

import org.grammaticalframework.Trees.Absyn.Tree;
import org.grammaticalframework.Trees.PrettyPrinter;

import java.io.IOException;

public class FoodsParseTest extends PGFTestCase
{

    public FoodsParseTest (String name) {
	super(name);
    }

    PGF pgf;

    public void setUp() throws IOException {
	pgf = getPGF("Foods.pgf");
    }

    public void testFoodsEng() throws UnknownLanguageException {
	Parser parser = new Parser(pgf, "FoodsEng");

	String ex1 = "this fresh pizza is Italian";
	Tree tree1 = parseTree("((Pred (This ((Mod Fresh) Pizza))) Italian)");
	Tree[] trees1 = parser.parse(ex1).getTrees();
	assert(trees1.length==1);
	assertEquals(trees1[0],tree1);

	String ex2 = "those boring fish are expensive";
	Tree tree2=parseTree("((Pred (Those ((Mod Boring) Fish))) Expensive)");
	Tree[] trees2 = parser.parse(ex2).getTrees();
	assert(trees2.length==1);
	assertEquals(trees2[0],tree2);
    }

    public void testFoodsSwe() throws UnknownLanguageException {
	Parser parser = new Parser(pgf, "FoodsSwe");

	String ex1 = "den här läckra pizzan är färsk";
	Tree tree1 = parseTree("((Pred (This ((Mod Delicious) Pizza))) Fresh)");
	Tree[] trees1 = parser.parse(ex1).getTrees();
	assert(trees1.length==1);
	assertEquals(trees1[0],tree1);
    }

    public void testFoodsIta() throws UnknownLanguageException {
	Parser parser = new Parser(pgf, "FoodsIta");

	String ex1 = "questa pizza deliziosa è fresca";
	Tree tree1 = parseTree("((Pred (This ((Mod Delicious) Pizza))) Fresh)");
	Tree[] trees1 = parser.parse(ex1).getTrees();
	assert(trees1.length==1);
	assertEquals(trees1[0],tree1);
    }


    public void tearDown() {
	pgf = null;
    }
}
