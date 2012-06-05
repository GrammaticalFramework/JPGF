package org.grammaticalframework;

import junit.framework.TestCase;
import java.io.IOException;
import java.util.*;


public class IndexedPGFTest extends PGFTestCase {

     public IndexedPGFTest(String name) {
	 super(name);
     }

    public void testIndexedPhrasebookSelect()
	throws java.io.IOException, UnknownLanguageException
    {
	String filename =
	    this.getClass().getResource("PhrasebookIndexed.pgf").getFile();
	PGF pgf = PGFBuilder.fromFile(filename, new String[] {"PhrasebookEng", "PhrasebookFre"});
	assertTrue(pgf.hasConcrete("PhrasebookEng"));
	assertTrue(pgf.hasConcrete("PhrasebookFre"));
	assertFalse(pgf.hasConcrete("PhrasebookIta"));
	
	HashSet<String> gold = new HashSet();
	gold.add("PhrasebookEng");
	gold.add("PhrasebookFre");
	assertEquals(gold, pgf.concreteNames());
    }

    public void testIndexedPhrasebookAll()
	throws java.io.IOException
    {
	PGF pgf = getPGF("PhrasebookIndexed.pgf");
	assertTrue(pgf.hasConcrete("PhrasebookEng"));
	assertTrue(pgf.hasConcrete("PhrasebookFre"));
	assertTrue(pgf.hasConcrete("PhrasebookIta"));
	
	HashSet<String> gold = new HashSet();
	gold.add("DisambPhrasebookEng");
	gold.add("PhrasebookBul");
	gold.add("PhrasebookCat");
	gold.add("PhrasebookDan");
	gold.add("PhrasebookDut");
	gold.add("PhrasebookEng");
	gold.add("PhrasebookFre");
	gold.add("PhrasebookGer");
	gold.add("PhrasebookIta");
	gold.add("PhrasebookNor");
	gold.add("PhrasebookPol");
	gold.add("PhrasebookRon");
	gold.add("PhrasebookSpa");
	gold.add("PhrasebookSwe");
	assertEquals(gold, pgf.concreteNames());
    }

    public void testUnknownLanguage()
	throws java.io.IOException
    {
	String filename =
	    this.getClass().getResource("Phrasebook.pgf").getFile();
	try {
	    PGF pgf = PGFBuilder.fromFile(filename, new String[] {"PhrasebookEng", "PhrasebookBORK"});
	    fail("PGFBuilder failed to raise an exception when an unknown language is selected.");
	} catch(UnknownLanguageException e) {}
    }

    public void testUninexedFoodsSelect()
	throws java.io.IOException, UnknownLanguageException
    {
	String filename =
	    this.getClass().getResource("Foods.pgf").getFile();
	PGF pgf = PGFBuilder.fromFile(filename, new String[] {"FoodsIta"});
	assertTrue(pgf.hasConcrete("FoodsIta"));
	assertFalse(pgf.hasConcrete("FoodsFre"));	
    }

    public void testUninexedFoodsAll() 
	throws java.io.IOException
    {
	PGF pgf = getPGF("Foods.pgf");
	assertTrue(pgf.hasConcrete("FoodsIta"));

	HashSet<String> gold = new HashSet();
	gold.add("FoodsAfr");
	gold.add("FoodsAmh");
	gold.add("FoodsBul");
	gold.add("FoodsCat");
	gold.add("FoodsDut");
	gold.add("FoodsEng");
	gold.add("FoodsEpo");
	gold.add("FoodsFas");
	gold.add("FoodsFin");
	gold.add("FoodsGer");
	gold.add("FoodsHeb");
	gold.add("FoodsHin");
	gold.add("FoodsIce");
	gold.add("FoodsIta");
	gold.add("FoodsJpn");
	gold.add("FoodsLav");
	gold.add("FoodsMlt");
	gold.add("FoodsMon");
	gold.add("FoodsPor");
	gold.add("FoodsRon");
	gold.add("FoodsSwe");
	gold.add("FoodsTsn");
	gold.add("FoodsTur");
	gold.add("FoodsUrd");
	assertEquals(gold, pgf.concreteNames());
    }
}