/* FoodsPredictTest.java
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
