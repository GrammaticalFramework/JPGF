/*
 * GFCMTranslator.java
 * Copyright (C) 2006, Bjorn Bringert (bringert@cs.chalmers.se)
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

import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.parse.*;
import se.chalmers.cs.gf.linearize.*;
import se.chalmers.cs.gf.linearize.unlex.Unlexers;

/**
 *  A Translator which also provides access to the set 
 *  of GF modules which it uses.
 */
public class GFCMTranslator extends Translator {

        private GFCMModules modules;

        /**
         *  @param name The name to use for the translator.
         *  @param parsers The parsers that this translator should use.
         *  @param annotator A type annotator.
         *  @param linearizers The linearizers that this translator should use.
         */
        public GFCMTranslator(String name, Parsers parsers, 
			      TypeAnnotator annotator, Linearizers linearizers,
			      Unlexers unlexers, GFCMModules modules) {
		super(name, parsers, annotator, linearizers, unlexers);
		this.modules = modules;
        }

        /**
         *  Gets the GF modules used by this translator.
         */
        public GFCMModules getModules() {
                return modules;
        }

}
