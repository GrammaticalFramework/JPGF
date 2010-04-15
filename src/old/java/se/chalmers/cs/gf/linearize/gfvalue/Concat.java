package se.chalmers.cs.gf.linearize.gfvalue;

import java.util.List;
import java.util.Iterator;

/**
 *  The result of concatenating two values. This is a separate class
 *  (instead of just concatenting the string representations of the values
 *  to make a new stirng value) to support prefix-dependent choice, unlexing
 *  and better efficiency.
 */
public class Concat extends Value {

        private final Value s1, s2;

        public Concat(Value s1, Value s2) {
                this.s1 = s1;
                this.s2 = s2;
        }

	public Value getFirst() {
		return s1;
	}

	public Value getSecond() {
		return s2;
	}

        public String toString() {
                String l1 = s1.toString();
                String l2 = s2.toString();
		return l1 + " " + l2;
        }

        // FIXME: should probably be right-associative
        public static Value concat(List<String> ss) {
                if (ss.size() == 0)
                        return new Str("");

                Iterator<String> it = ss.iterator();
                Value v = new Str(it.next());
                while (it.hasNext())
                        v = new Concat(v, new Str(it.next()));
                return v;
        }


        public boolean equals(Object o) {
                return o instanceof Concat && equals((Concat)o);
        }

        public boolean equals(Concat c) {
                return s1.equals(c.s1) && s2.equals(c.s2);
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
