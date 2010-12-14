package org.grammaticalframework;

import junit.framework.TestCase;
import java.io.IOException;

public class IndexedPGFTest extends TestCase {

     public IndexedPGFTest(String name) {
	 super(name);
     }

    public void testIndexedPhrasebookSelect()
	throws java.io.IOException, UnknownLanguageException
    {
	String filename =
	    this.getClass().getResource("PhrasebookIndexed.pgf").getFile();
	PGF pgf = PGFBuilder.fromFile(filename, new String[] {"PhrasebookEng", "PhrasebookFre"});
	assert(pgf.hasConcrete("PhrasebookEn"));
	assert(pgf.hasConcrete("PhrasebookFre"));
	assertFalse(pgf.hasConcrete("PhrasebookIta"));
    }

    public void testIndexedPhrasebookAll()
	throws java.io.IOException
    {
	String filename =
	    this.getClass().getResource("PhrasebookIndexed.pgf").getFile();
	PGF pgf = PGFBuilder.fromFile(filename);
	assert(pgf.hasConcrete("PhrasebookEn"));
	assert(pgf.hasConcrete("PhrasebookFre"));
	assert(pgf.hasConcrete("PhrasebookIta"));
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
	assert(pgf.hasConcrete("FoodsIta"));
	assertFalse(pgf.hasConcrete("FoodsFre"));	
    }

    public void testUninexedFoodsAll() 
	throws java.io.IOException
    {
	String filename =
	    this.getClass().getResource("Foods.pgf").getFile();
	PGF pgf = PGFBuilder.fromFile(filename);
	assert(pgf.hasConcrete("FoodsIta"));
	assert(pgf.hasConcrete("FoodsFre"));	
    }
}