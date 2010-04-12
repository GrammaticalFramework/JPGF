/*
 * Tree.java
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
package se.chalmers.cs.pgf.abssyn;

import se.chalmers.cs.pgf.util.Pair;
import static se.chalmers.cs.pgf.util.Pair.pair;

import java.util.Collections;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

/**
 *  Interface implemented by all the abstract syntax classes.
 */
public abstract class Tree implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2231155215892088725L;
	private Set<Pair<Integer,Integer>> inputRanges;

        public Tree() {
		this(Collections.<Pair<Integer,Integer>>emptySet());
        }

        public Tree(int startIndex, int endIndex) {
		this(Collections.<Pair<Integer,Integer>>singleton(pair(startIndex, endIndex)));
        }

        public Tree(Set<Pair<Integer,Integer>> inputRanges) {
		this.inputRanges = inputRanges;
        }



        public abstract boolean isLiteral();

	public Set<Pair<Integer,Integer>> getInputRanges() {
		return inputRanges;
	}

	public String toStringWithRanges() {
		return toString() + " = " + inputRangesToString();
	}

	public String inputRangesToString() {
		StringBuilder sb = new StringBuilder("[");
		Iterator<Pair<Integer,Integer>> it = inputRanges.iterator();
		while (it.hasNext()) {
			Pair<Integer,Integer> p = it.next();
			sb.append(p.fst).append("-").append(p.snd);
			if (it.hasNext())
				sb.append(",");
		}
		return sb.append("]").toString();
	}


        public abstract <R,A> R accept(TreeVisitor<R,A> v, A arg);

	public static Set<Pair<Integer,Integer>> unifyInputRanges(Tree t1, Tree t2) {
		Set<Pair<Integer,Integer>> r = new HashSet<Pair<Integer,Integer>>();
		r.addAll(t1.getInputRanges());
		r.addAll(t2.getInputRanges());
		return r;
	}

}
