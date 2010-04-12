package se.chalmers.cs.gf.linearize.gfvalue;

/**
 *  String value.
 */
public class Str extends Value {

        private String str;

        public Str(String str) {
                this.str = str;
        }

        public String toString() {
                return str;
        }

        public int hashCode() {
                return str.hashCode();
        }

        public boolean equals(Object o) {
                return (o instanceof Str) && equals((Str)o);
        }

        public boolean equals(Str r) {
                return str.equals(r.str);
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
