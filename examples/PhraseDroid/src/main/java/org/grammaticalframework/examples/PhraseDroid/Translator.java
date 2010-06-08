package org.grammaticalframework.examples.PhraseDroid;

import org.grammaticalframework.reader.PGF;
import org.grammaticalframework.parser.Parser;
import org.grammaticalframework.linearizer.Linearizer;
import org.grammaticalframework.Trees.Absyn.Tree;

import java.lang.RuntimeException;


class Translator {

   private PGF mPGF;          // The pgf object to use
   private Parser mParser;       // The Parser object to use
   private Linearizer mLinearizer;   // The Linearizer object to use

   Translator(PGF mPGF, String sLang, String tLang) {
      this.mPGF = mPGF;
      this.mParser = new Parser(mPGF.concrete(sLang));
      try {
         this.mLinearizer = new Linearizer(mPGF, mPGF.concrete(tLang));
      } catch (Exception e) {
         throw new RuntimeException("Cannot create the linearizer : " + e);
      }
   }
   
   public String translate(String txt) {
      this.mParser.parse(txt);
      Tree[] trees = (Tree[])this.mParser.getTrees().toArray().unbox(Tree.class);
      StringBuffer s = new StringBuffer();
      if (trees.length < 1)
         return "/!\\ No translation";
      try {
         return this.mLinearizer.renderLin(this.mLinearizer.linearize(trees[0])).toString();
      } catch (java.lang.Exception e) {
         return "Error during linearization";
      }
   }
}