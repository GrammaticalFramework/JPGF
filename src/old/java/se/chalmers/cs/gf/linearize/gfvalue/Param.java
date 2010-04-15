package se.chalmers.cs.gf.linearize.gfvalue;

/**
 *  A parameter.
 */
public class Param extends Value {

        // FIXME: needs more efficient representation

        private String str;
        private Value[] args;

        public Param(String str, Value[] args) {
                this.str = str;
                this.args = args;
        }

        public int hashCode() {
                return toString().hashCode();
        }

        public boolean equals(Object o) {
                return (o instanceof Param) && equals((Param)o);
        }

        public boolean equals(Param p) {
                return toString().equals(p.toString());
        }

        public boolean match(Value p) {
                if (!(p instanceof Param))
                        return false;
                Param pa = (Param)p;
                if (!str.equals(pa.str))
                        return false;
                if (args.length != pa.args.length)
                        return false;
                for (int i = 0; i < args.length; i++) {
                        if (!args[i].match(pa.args[i]))
                                return false;
                }
                return true;
        }

        public String toString() {
                StringBuilder sb = new StringBuilder(str);
                for (Value arg : args)
                        sb.append(' ').append(arg);
                return sb.toString();
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
