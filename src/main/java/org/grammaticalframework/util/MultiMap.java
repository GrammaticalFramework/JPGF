/*
 * MultiMap.java
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
package org.grammaticalframework.util;

import java.util.*;

/**
 *  Maps keys to sets of values.
 *  @param <K> The type of the keys
 *  @param <V> The type of the values.
 */
public class MultiMap<K,V> {

	/**
	 *  Invariant: the sets have at least one element.
	 */
        private Map<K,Set<V>> map;

        public MultiMap() {
                this.map = new HashMap<K,Set<V>>();
        }

        public Set<V> get(K key) {
                Set<V> s = map.get(key);
                return s == null ? Collections.<V>emptySet() : s;
        }

        public boolean put(K key, V value) {
                Set<V> s = map.get(key);
                if (s == null) {
                        s = new HashSet<V>();
                        map.put(key, s);
                }
                return s.add(value);
        }

	public Set<K> keySet() {
		return map.keySet();
	}

	/** 
	 *  Checks whether there is at least one value for the 
	 *  given key.
	 */
	public boolean containsKey(K key) {
		return map.containsKey(key);		
	}

        public String toString() {
                return map.toString();
        }

        /**
         *  Returns the all the values in the map.
         *  FIXME: The result is not backed by the map.
         */
        public Collection<V> values() {
                List<V> l = new ArrayList<V>();
                for (Set<V> s : map.values())
                        for (V v : s)
                                l.add(v);
                return l;
        }

}
