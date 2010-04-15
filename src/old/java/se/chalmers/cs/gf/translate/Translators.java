/*
 * Translators.java
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

import java.util.*;

/**
 *  A collection of translators.
 */
public class Translators {

        private Map<String,Translator> translators;

        public Translators() {
                this.translators = new HashMap<String,Translator>();
        }

        /**
         *  @return The translator with the given name, or null if there 
         *  is no such translator.
         */
        public Translator getTranslator(String name) {
                return translators.get(name);
        }

        public void addTranslator(Translator t) {
                translators.put(t.getName(), t);
        }

        /**
         *  Get the names of all the available translators.
         */
        public List<String> listTranslators() {
                return new ArrayList<String>(translators.keySet());
        }

}
