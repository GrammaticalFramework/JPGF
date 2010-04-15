/*
 * Record.java
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
package se.chalmers.cs.gf.parse.mcfg;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.Pair;

public class Record<A> extends TreeMap<String,A> {

        public Record() {

        }

        public Record(Pair<String,A>... ps) {
		for (Pair<String,A> p : ps) {
			put(p.fst, p.snd);
		}
        }

        public Record(Record<A> r) {
		super(r);
        }

	public Pair<String,A> removeFirst() {
		if (isEmpty())
			return null;
		Iterator<Map.Entry<String,A>> it = entrySet().iterator();
		Map.Entry<String,A> e = it.next();
		it.remove();
		return new Pair<String,A>(e.getKey(), e.getValue());
	}

	public Record<A> copy() {
		return new Record<A>(this);
	}

	public A lookup(String l) {
		A x = get(l);
		if (x == null)
			throw new GFException("No field " + l + " in record " + this);
		return x;
	}

	public static <A> Record<A> rec() {
		return new Record<A>();
	}

	public static <A> Record<A> rec(Pair<String,A>... ps) {
		return new Record<A>(ps);
	}

}
