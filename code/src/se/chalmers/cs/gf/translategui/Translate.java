/*
 * Translate.java
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
package se.chalmers.cs.gf.translategui;

import se.chalmers.cs.gf.translate.*;

import java.io.*;
import java.util.*;

public class Translate {

        public static void main(String[] args) throws Exception {
                if (args.length != 4) {
                        System.err.println("Usage: java Test <gfc grammar> <cfg grammar> <input language> <output language>");
                        System.exit(1);
                }
                        

                String gfcFile = args[0];
                String cfgFile = args[1];
                String inputLang = args[2];
                String outputLang = args[3];

                Translator t = TranslatorFactory.createTranslator("test", cfgFile, gfcFile, 
                                                                  new String[] { "se.chalmers.cs.gf.parse.ParseTree" },
                                                                  new String[] { "se.chalmers.cs.gf.linearize.LinearizeTree" } );

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = in.readLine()) != null) {
                        Set<String> ts = t.translate(inputLang, outputLang, line);
                        if (ts.size() == 0) {
                                System.out.println("<no translation>");
                        } else if (ts.size() == 1) {
                                String s = ts.iterator().next();
                                System.out.println(s);
                        } else {
                                System.out.println("<Ambiguous translation:>");
                                for (String s : ts) {
                                        System.out.println(s);
                                }
                        }
                }
        }

}
