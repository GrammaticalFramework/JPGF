package org.grammaticalframework.examples.phrasedroid;

import org.grammaticalframework.PGF;
import org.grammaticalframework.Parser;
import org.grammaticalframework.Linearizer;
import org.grammaticalframework.Trees.Absyn.Tree;

import java.util.Vector;

import java.lang.RuntimeException;


class Translator {

   private PGF mPGF;                 // The pgf object to use
   private Parser mParser;           // The Parser object to use
   private Linearizer mLinearizer;   // The Linearizer object to use

   Translator(PGF mPGF, String sLang, String tLang) {
      this.mPGF = mPGF;
      try {
	  this.mParser = new Parser(mPGF, sLang);
	  try {
	      this.mLinearizer = new Linearizer(mPGF, mPGF.concrete(tLang));
	  } catch (Exception e) {
	      throw new RuntimeException("Cannot create the linearizer : " + e);
	  }
      } catch (PGF.UnknownLanguageException e) {
	  throw new RuntimeException("Cannot create the Parser : language not found " + sLang);
      }
   }
   
   public String translate(String[] txt) {
      ;
      Tree[] trees = (Tree[])this.mParser.parse(txt).getTrees().toArray().unbox(Tree.class);
      StringBuffer s = new StringBuffer();
      if (trees.length < 1)
         return "/!\\ No translation";
      try {
	  return this.mLinearizer.linearizeString(trees[0]);
      } catch (java.lang.Exception e) {
         return "Error during linearization";
      }
   }
}