package se.chalmers.cs.gf.linearize.gfvalue;

/**
 *  The value resulting from linearizing a metavariable.
 */
public class Meta extends Value {

        // FIXME: type annotation?

        public Meta() { }

        public String toString() {
                return "?";
        }

        public boolean match(Value p) {
                return true;
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
