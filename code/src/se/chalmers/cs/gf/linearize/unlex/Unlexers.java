/*
 * Unlexers.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.linearize.unlex;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.ClassUtil;
import static se.chalmers.cs.gf.linearize.LinearizeLogger.*;

import java.util.*;

/**
 *  Maps language names to unlexers.
 */
public class Unlexers {

        private Unlexer defaultUnlexer;

        private Map<String,Unlexer> unlexers;

        private Unlexers(Unlexer defaultUnlexer) {
                this.defaultUnlexer = defaultUnlexer;
                this.unlexers = new HashMap<String,Unlexer>();
        }

        public void add(String language, Unlexer unlexer) {
                unlexers.put(language, unlexer);
        }

        public Unlexer get(String language) {
                Unlexer unlexer = unlexers.get(language);
                return (unlexer == null) ? defaultUnlexer : unlexer;
        }

        public static Unlexers loadUnlexers(String defaultUnlexerName, 
					    Map<String,String> unlexerNames) {

                Unlexer d = loadUnlexer(defaultUnlexerName);
                Unlexers unlexers = new Unlexers(d);

                if (unlexerNames != null) {
                        for (Map.Entry<String,String> e : unlexerNames.entrySet()) {
                                String language = e.getKey();
                                String unlexerName = e.getValue();
                                log.fine("Creating unlexer for " + language 
                                         + ": " + unlexerName);
                                Unlexer unlexer = loadUnlexer(unlexerName);
                                unlexers.add(language, unlexer);
                        }
                }

                return unlexers;
        }

        private static Unlexer loadUnlexer(String unlexerName) {
                try {
                        return (Unlexer)ClassUtil.instantiate(unlexerName);
                } catch (ClassCastException ex) {
                        throw new GFException(unlexerName + " is not an unlexer class.");
                }
        }

}
