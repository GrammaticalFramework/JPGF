/*
 * Linearizers.java
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
package se.chalmers.cs.gf.linearize;

import se.chalmers.cs.gf.GFException;

import java.util.*;
import java.io.Serializable;

/**
 *  A container for a number of linearizers.
 */
public class Linearizers implements Serializable {

        /**
         *  Maps concrete grammar names (language names) to linearizers.
         */
        private Map<String,Linearizer> map;

        public Linearizers() {
                this.map = new TreeMap<String,Linearizer>();
        }

        /**
         *  Get a linearizer by (language) name.
         */
        public Linearizer getLinearizer(String lang) {
                Linearizer l = map.get(lang);

                if (l == null) 
                        throw new GFException("There is no linearizer for " + lang);

                return l;
        }

        /**
         *  List the (language) names of all the linearizers.
         */
        public Set<String> listLinearizers() {
                return map.keySet();
        }

        public void addLinearizer(Linearizer l) {
                String lang = l.getName();

                if (map.containsKey(lang))
                        throw new GFException("There is already a linearizer for " + lang);

                map.put(lang, l);
        }

}
