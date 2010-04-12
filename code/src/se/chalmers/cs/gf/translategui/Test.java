package se.chalmers.cs.gf.translategui;

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.translate.*;

import java.util.*;
import java.io.*;

public class Test {

        public static void main(String[] args) throws IOException {
                if (args.length != 3) {
                        System.err.println("Usage: java " + Test.class.getName()
                                           + " <properties resource> <from lang> <to lang>");
                        System.exit(2);
                }

                Translator t = TranslatorFactory.createTranslator(args[0]);
                String fromLang = args[1];
                String toLang = args[2];

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = in.readLine()) != null) {
                        System.err.println("Input: " + line);
                        Collection<Tree> ts = t.parse(fromLang, line);
                        for (Tree tree : ts) {
                                debug("Tree", tree.toString());
                                debug("Tree (with ranges)",
				      tree.toStringWithRanges());
                                print("Output", t.linearize(toLang, tree));
/*
                                debug("Output (with ranges)", 
				      t.linearizeWithRanges(toLang, tree));
*/
                        }
                        System.err.println("---");
                }
        }

	private static void print(String n, String v) {
		System.err.println(n+":");
		System.out.println(v);
	}

	private static void debug(String n, String v) {
		System.err.println(n+":");
		System.err.println(v);
	}


}
