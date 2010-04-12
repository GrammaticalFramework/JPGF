/*
 * PrefixMap.java
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
package se.chalmers.cs.gf.util;

import java.util.*;

/**
 *  A trie with string keys.
 *
 *  @param <A> The type of the values.
 */
public class PrefixMap<A> {

        private A value;

        private Map<Character,PrefixMap<A>> map;

        public PrefixMap() {
                value = null;
                map = new HashMap<Character,PrefixMap<A>>();
        }
        
        public A put (String key, A newvalue) {
                if (key.length() == 0) {
                        A old = value;
                        value = newvalue;
                        return old;
                } else {
                        Character head = key.charAt(0);
                        // FIXME: inefficient
                        String tail = key.substring(1);

                        PrefixMap<A> m = map.get(head);
                        if (m == null) {
                                m = new PrefixMap<A>();
                                map.put(head, m);
                        }
                        return m.put(tail, newvalue);
                }
        }

        public A get (String key) {
                if (key.length() == 0)
                        return value;
                Character head = key.charAt(0);
                // FIXME: inefficient
                String tail = key.substring(1);
                PrefixMap<A> m = map.get(head);
                if (m == null)
                        return value;

                A v = m.get(tail);
                if (v == null)
                        return value;

                return v;
        }

	public Set<Map.Entry<String,A>> entrySet() {
		return entrySet(new HashSet<Map.Entry<String,A>>(), "");
	}

	private Set<Map.Entry<String,A>> entrySet(Set<Map.Entry<String,A>> s, String prefix) {
		s.add(new MapEntryPair<String,A>(prefix, value));
		for (Map.Entry<Character,PrefixMap<A>> e : map.entrySet()) {
			e.getValue().entrySet(s, e.getKey() + prefix);
		}
		return s;
	}

        public boolean equals(Object o) {
                return o instanceof PrefixMap && equals((PrefixMap)o);
        }

        public boolean equals(PrefixMap p) {
                return value.equals(p.value) && map.equals(p.map);
        }

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		Iterator<Map.Entry<String,A>> es = entrySet().iterator();
		while (es.hasNext()) {
			Map.Entry<String,A> e = es.next();
			sb.append(e.getKey()).append("=").append(e.getValue());			
			if (es.hasNext())
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}

}
