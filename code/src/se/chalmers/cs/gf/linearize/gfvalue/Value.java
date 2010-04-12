package se.chalmers.cs.gf.linearize.gfvalue;

/**
 *  Base class for GF values.
 */
public abstract class Value implements Comparable<Value> {

        /** 
         *  Default implementation compares the toString() values.
         */
        public int compareTo(Value v) {
                return toString().compareTo(v.toString());
        }

        /**
         * Check if this value matches the given pattern.
         * The default implementation compares the toString() value.
         */
        public boolean match(Value p) {
                return toString().equals(p.toString());
        }

	public abstract <R,A> R accept(ValueVisitor<R,A> v, A arg);
}
