package org.grammaticalframework;

import org.grammaticalframework.parser.*;
import java.util.Arrays;
import java.io.IOException;

public class FoodsPredictTest extends PGFTestCase
{

    public FoodsPredictTest (String name) {
	super(name);
    }

    PGF pgf;

    public void setUp() throws IOException {
	pgf = getPGF("Foods.pgf");
    }

    public void testFoodsEng()
	throws UnknownLanguageException, ParseError
    {
	Parser parser = new Parser(pgf, "FoodsEng");
	String[] words = new String [] {"that", "these", "this", "those"};
	String[] predictions = parser.parse().predict();
	Arrays.sort(predictions);
	assertEquals(words.length, predictions.length);
	for (int i = 0 ; i < words.length ; i++)
	    assertEquals(words[i], predictions[i]);
    }

    public void testFoodsSwe()
	throws UnknownLanguageException, ParseError
    {
	Parser parser = new Parser(pgf, "FoodsSwe");
	String[] words = new String [] {"de", "den", "det"};
	String[] predictions = parser.parse().predict();
	Arrays.sort(predictions);
	assertEquals(words.length, predictions.length);
	for (int i = 0 ; i < words.length ; i++)
	    assertEquals(words[i], predictions[i]);
    }

    public void testFoodsIta()
	throws UnknownLanguageException, ParseError
    {
	Parser parser = new Parser(pgf, "FoodsIta");

	String[] words = new String [] {"quei", "quel",	"quella", "quelle",
					"questa", "queste", "questi", "questo"};

	String[] predictions = parser.parse().predict();
	Arrays.sort(predictions);
	assertEquals(words.length, predictions.length);
	for (int i = 0 ; i < words.length ; i++)
	    assertEquals(words[i], predictions[i]);
    }


    public void tearDown() {
	pgf = null;
    }
}
