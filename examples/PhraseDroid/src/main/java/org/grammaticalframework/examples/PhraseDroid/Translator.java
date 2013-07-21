/* Translator.java
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
package org.grammaticalframework.examples.PhraseDroid;

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
      Tree[] trees = (Tree[])this.mParser.parse(txt).getTrees();
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
