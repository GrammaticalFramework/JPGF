/* Eval.java
 * Copyright (C) 2010 Grégoire Détrez
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
import org.grammaticalframework.PGFBuilder;
import org.grammaticalframework.PGF;
import org.grammaticalframework.Parser;
import org.grammaticalframework.Linearizer;
import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.Trees.Absyn.Tree;
import java.io.*;

class Eval {

    private static final boolean DBG = false;

    public static void main(String[] args) throws Exception {
	if (args.length < 1) {
	    System.err.println("Argument is missing");
	    System.exit(1);
	} else if (args.length > 1) {
	    System.err.println("Too many arguments");
	    System.exit(1);
	}
	String[] splits = args[0].split(":");
	if (splits.length != 2) {
	    print_usage("Bad language reference");
	    System.exit(1);
	}

	String pgf_file = splits[0];
	String lang_name = splits[1];
	final long begin_time = System.currentTimeMillis();
	PGF pgf = PGFBuilder.fromFile(pgf_file);
	final long pgf_time = System.currentTimeMillis();
	translate(pgf, lang_name);
	final long end_time = System.currentTimeMillis();
	System.err.println("time: " + (pgf_time - begin_time) + ", " + (end_time - begin_time));
    }

    static void translate(PGF pgf, String language)
	throws PGF.UnknownLanguageException, Linearizer.LinearizerException
    {
	// init stats
	int success = 0;
	int error= 0;
	
	// init parser and linearizer
 	Parser mParser = new Parser(pgf, language);
	Linearizer mLinearizer = new Linearizer(pgf, language);

	// read stdin
	try {
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	    String str = "";
	    str = in.readLine();
	    while (str != null) {
		if (DBG) System.err.println("sentence: " + str);
		ParseState ps = mParser.parse(str.trim().split(" "));
		Tree[] trees = (Tree[])ps.getTrees();
		String translation;
		if (trees.length > 0)
		    try {
			translation = mLinearizer.linearizeString(trees[0]);
			success++;
		    } catch (java.lang.Exception e) {	
			if (DBG) System.err.println("No translation (Error during linearization) ");
			translation = "*** no translation ***";
			error++;
		    }
		else {
		    if (DBG) System.err.println("No translation (No tree found) ");
		    translation = "*** no translation ***";
		    error++;
		}
		System.out.println(translation);
		str = in.readLine();
	    }
	} catch (IOException e) {
	    System.err.println(e);
	}
	System.err.println("stats: " + success + " successes, " + error + " errors");

    }

    private static void print_usage(String errMsg) {
	if (errMsg != null)
	    System.err.println(errMsg);
	System.err.println("Usage:");
	System.err.println("\t program pgfFile:LanguageName");
	System.err.println("\n Exemple: java Eval Foods.pgf:FoodsEng");
    }

}

