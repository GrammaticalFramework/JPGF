/*
 * Pair.java
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

/**
 * An immutable 2-tuple.
 *
 * Based on code from Higher-Order Java.
 *
 *  @param <A> The type of the first value.
 *  @param <B> The type of the second value.
 */
public class Pair <A,B> implements Comparable<Pair<A,B>> {

        /**
         * The first component.
         */
        public final A fst;
        
        /**
         * The second component.
         */
        public final B snd;
        
        /**
         * Creates a new pair.
         *
         * @param fst The first component.
         * @param snd The second component.
         */
        public Pair (A fst, B snd) {
                this.fst = fst;
                this.snd = snd;
        }
        
        /**
         * Compares this pair to another object for equality.
         *
         * @param o The object to compare this pair with.
         * @return true iff o is not null and o is a Pair and 
         * the components of this and o are pairwise equal or both null.
         */
        public boolean equals (Object o) {
                if (o != null && o instanceof Pair) {
                        Pair p = (Pair)o;
                        return (fst == null ? p.fst == null : fst.equals(p.fst)) 
                                && (snd == null ? p.snd == null : snd.equals(p.snd));
                }
                
                return false;
        }

        /**
         * Returns a hash code for this pair. The hash code is calculated from
         * the hash codes of the components.
         */
        public int hashCode () {
                return (fst == null ? 0 : fst.hashCode()) 
                        ^ (snd == null ? 0 : snd.hashCode());
        }
        
        public String toString() {
                return "Pair(" + fst + ", " + snd +  ")";
        }

	/**
	 *   FIXME: Fails if the element types are not comparable.
	 */
	public int compareTo(Pair<A,B> p) {
		int c = ((Comparable<A>)fst).compareTo(p.fst);
		if (c != 0) {
			return c;
		} else {
			return ((Comparable<B>)snd).compareTo(p.snd);
		}
	}
        
        /**
         * Gets an array representation of this pair.
         *
         * @return A 2-element array containing the elements of
         * this pair in order. 
         */
        public Object[] toArray () {
                return new Object[]{ fst, snd };
        }
        
        /**
         *  Construct a pair. Convenience method to allow type inference and
         */
        public static <A,B> Pair<A,B> pair(A a, B b) {
                return new Pair<A,B>(a,b);
        }

}
