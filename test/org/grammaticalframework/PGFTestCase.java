package org.grammaticalframework;

import junit.framework.TestCase;
import java.io.IOException;
import org.grammaticalframework.Trees.Yylex;
import org.grammaticalframework.Trees.parser;
import org.grammaticalframework.Trees.Absyn.Tree;
import java.io.StringReader;

 public abstract class PGFTestCase extends TestCase
 {
     public PGFTestCase(String name) {
	 super(name);
     }

     protected PGF getPGF(String filename) throws IOException {
	 String fullname =
	     this.getClass().getResource(filename).getFile();
	 PGF pgf = PGFBuilder.fromFile(fullname);
	 return pgf;
     }

     protected Tree parseTree(String s) {
	 Yylex l = new Yylex(new StringReader(s));
	 parser p = new parser(l);
	 try {
	     Tree parse_tree = p.pTree();
	     return parse_tree;
	 } catch(Exception e) {
	     return null;
	 }
     }
}
