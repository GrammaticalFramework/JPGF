/*
 * TranslatorFactory.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.translate;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.parse.*;
import se.chalmers.cs.gf.parse.chart.*;
import se.chalmers.cs.gf.parse.lex.*;
import se.chalmers.cs.gf.linearize.*;
import se.chalmers.cs.gf.linearize.gfcc.GFCCLinearizers;
import se.chalmers.cs.gf.linearize.gfcc.GFCCTypeAnnotator;
import se.chalmers.cs.gf.linearize.unlex.*;
import se.chalmers.cs.gf.util.ClassUtil;
import se.chalmers.cs.gf.util.FileUtil;
import se.chalmers.cs.gf.GFCC.GFCCParser;
import se.chalmers.cs.gf.GFCC.Absyn.Grammar;

import java.io.*;
import java.util.*;


/**
 *  Loads parsers, linearizers etc. and creates Translators from them.
 */
public class TranslatorFactory {

        /**
         *  Get all properties with a given suffix.
         *  @return A map of property names with the suffix removed
         *          to property values.
         */
        private static Map<String,String> getOpts(Properties props, 
						  String optSuffix) {
                Map<String,String> m = new HashMap<String,String>();
                for (Map.Entry<Object,Object> e : props.entrySet()) {
                        String key = (String)e.getKey();
                        String value = (String)e.getValue();
                        if (key.endsWith(optSuffix)) {
                                int newKeyLen = key.length()-optSuffix.length();
                                String newKey = key.substring(0, newKeyLen);
                                m.put(newKey,value);
                        }
                }
                return m;
        }

        /**
         *  Create a translator by reading properties from the given resource.
         */
        public static Translator createTranslator(String propResource) throws IOException {
                Properties prop = FileUtil.readPropertiesResource(propResource);
                return createTranslator(prop);
        }

        /**
         *  Create a translator from a set of properties.
         */
        public static Translator createTranslator(Properties prop) throws IOException {
                String name = FileUtil.getProp(prop, "name");
                String coding = prop.getProperty("coding");
                String cfgm = FileUtil.getProp(prop, "cfgm");
                String gfcm = FileUtil.getProp(prop, "gfcm");
                String[] xps = FileUtil.getListProp(prop, "extraparsers");
                String[] xls = FileUtil.getListProp(prop, "extralinearizers");
                String lexer = prop.getProperty("lexer");
                Map<String,String> lexers = getOpts(prop, ".lexer");
                String unlexer = prop.getProperty("unlexer");
                Map<String,String> unlexers = getOpts(prop, ".unlexer");
		boolean robust = Boolean.valueOf(prop.getProperty("robust"));

                return createTranslator(name, coding, cfgm, gfcm, xps, xls, 
					lexer, lexers, unlexer, unlexers, robust);
        }

        /**
         *  Create a translator by reading grammars from the given resources.
         *  @param name The name of the translator.
         *  @param cfgm Resource to read the CFGM (parsing) grammar from.
         *  @param gfcm Resource to read the GFCM (linearization) grammar from.
         */
        public static Translator createTranslator(String name, String cfgm, String gfcm) 
                throws IOException {

                return createTranslator(name, cfgm, gfcm, new String[0], new String[0]);
        }

        /**
         *  Create a translator by reading grammars from the given resources.
         *  @param name The name of the translator.
         *  @param cfgm Resource to read the CFGM (parsing) grammar from.
         *  @param gfcm Resource to read the GFCM (linearization) grammar from.
         *  @param extraparsers Class names of extra parsers to load.
         *  @param extralinearizers Class names of extra linearizers to load.
         */
        public static Translator createTranslator(String name, 
						      String cfgm, 
						      String gfcm, 
						      String[] extraparsers, 
						      String[] extralinearizers)
                throws IOException {
                return createTranslator(name, null, cfgm, gfcm, 
                                        extraparsers, extralinearizers, null, null);
        } 

        /**
         *  Create a translator by reading grammars from the given resources.
         *  @param name The name of the translator.
         *  @param coding Name of the character coding used for the GFCM and CFGM files, or
         *                null to use the default coding ("UTF-8")
         *  @param cfgm Resource to read the CFGM (parsing) grammar from.
         *  @param gfcm Resource to read the GFCM (linearization) grammar from.
         *  @param extraparsers Class names of extra parsers to load.
         *  @param extralinearizers Class names of extra linearizers to load.
         *  @param lexer Class name of lexer to use the default lexer. Pass null
	 *  to get the hard-coded default lexer.
         *  @param lexerMap Maps language names to lexer names. The lexer given in the lexer
         *         parameter will be used for languages without an entry in this map.
         */
        public static Translator createTranslator(String name, 
						      String coding,
						      String cfgm, 
						      String gfcm, 
						      String[] extraparsers, 
						      String[] extralinearizers,
						      String lexer,
						      Map<String,String> lexerMap) 
                throws IOException {
		return createTranslator(name, coding, cfgm, gfcm, 
					extraparsers, extralinearizers,
					lexer, lexerMap, null, null, false);
	}

        /**
         *  Create a translator by reading grammars from the given resources.
         *  @param name The name of the translator.
         *  @param coding Name of the character coding used for the GFCM and CFGM files, or
         *                null to use the default coding ("UTF-8")
         *  @param cfgm Resource to read the CFGM (parsing) grammar from.
         *  @param gfcm Resource to read the GFCM (linearization) grammar from.
         *  @param extraparsers Class names of extra parsers to load.
         *  @param extralinearizers Class names of extra linearizers to load.
         *  @param lexer Class name of lexer to use the default lexer. Pass null
	 *  to get the hard-coded default lexer.
         *  @param lexerMap Maps language names to lexer names. The lexer given in the lexer
         *         parameter will be used fro languages without an entry in this map.
         *  @param unlexer Class name of unlexer to use the default unlexer. Pass null
	 *  to get the hard-coded default unlexer.
         *  @param unlexerMap Maps language names to unlexer names. The unlexer given in 
	 *         the unlexer parameter will be used for languages without an entry in 
	 *         this map.
         */
        public static Translator createTranslator(String name, 
						      String coding,
						      String cfgm, 
						      String gfcm, 
						      String[] extraparsers, 
						      String[] extralinearizers,
						      String lexer,
						      Map<String,String> lexerMap,
						      String unlexer,
						      Map<String,String> unlexerMap,
						      boolean robust) 
                throws IOException {

                if (coding == null)
                        coding = "UTF-8";

		// Load all lexers
                if (lexer == null)
                        lexer = "se.chalmers.cs.gf.parse.lex.SimpleLexer";
                Lexers lexers = Lexers.loadLexers(lexer, lexerMap);

		// Load all unlexers
                if (unlexer == null)
                        unlexer = "se.chalmers.cs.gf.linearize.unlex.SimpleUnlexer";
                Unlexers unlexers = Unlexers.loadUnlexers(unlexer, unlexerMap);

		// Load all parsers
                Parsers ps = CFGParsers.readParsers(FileUtil.readResource(cfgm, coding), lexers, robust);
                for (Object o : ClassUtil.instantiateAll(extraparsers)) {
                        Parser p = (Parser)o;
                        ps.addParser(p);
                }

		// Load all linearizers
		Linearizers ls ;
		TypeAnnotator annotator;
		if (gfcm.endsWith(".gfcm")) {
			GFCMModules ms = GFCMModules.loadGFCM(FileUtil.readResource(gfcm, coding));
			ls = new GFCMLinearizers(ms);
			annotator = new GFCTypeAnnotator(ms.getMainAbstractModule());
		} else if (gfcm.endsWith(".gfcc")) {
			Reader in = FileUtil.readResource(gfcm, coding);
			Grammar gfcc = GFCCParser.parse(in);
			ls = new GFCCLinearizers(gfcc);
			annotator = new GFCCTypeAnnotator(gfcc);
		} else {
			throw new GFException("Unknown linearizer file format: " + gfcm);
		}
		for (Object o : ClassUtil.instantiateAll(extralinearizers)) {
			Linearizer l = (Linearizer)o;
			ls.addLinearizer(l);
		}

                return new Translator(name, ps, annotator, ls, unlexers);
        }

}
