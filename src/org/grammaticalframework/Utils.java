/* Utils.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
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
package org.grammaticalframework;

import java.util.*;

public class Utils {

    public static String concat(String[] tokens) {
	return concat(tokens, " ");
    }

    public static String concat(String[] tokens, String sep) {
	if (tokens.length < 1)
	    return "";
	StringBuffer sb = new StringBuffer(tokens[0]);
	for (int i = 1; i < tokens.length; i++) {
	    sb.append(" ");
	    sb.append(tokens[i]);
	}
	return sb.toString();
    }

    /** Functions that makes every possible combination of items from
     * an array of collection while respecting the order of the collections.
     */
    static public <V> Collection<List<V>> combine(final Collection<V>[] c) {
	Collection<List<V>> result = new Vector();
	Vector<V> combination = new Vector(c.length);

	/* We initialise the state with iterators from all collections 
	 * and the combination with the initial value from all iterators */
	Iterator<V>[] state = new Iterator[c.length];
	for (int i = 0; i < c.length; i++) {
	    state[i] = c[i].iterator();
	    if (state[i].hasNext())
		combination.add(state[i].next());
	    else
		return result;
	}
	result.add((List<V>)combination.clone());

	int pos = c.length - 1;
	while (pos >= 0) {
	    if (state[pos].hasNext()) {
		combination.set(pos, state[pos].next());
		if (pos < c.length - 1)
		    pos++;
	    } else {
		state[pos] = c[pos].iterator();
		pos--;
	    }
	    result.add((List<V>)combination.clone());
	}
	return result;
    }
}
