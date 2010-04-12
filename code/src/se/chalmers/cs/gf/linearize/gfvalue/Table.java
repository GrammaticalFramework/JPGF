package se.chalmers.cs.gf.linearize.gfvalue;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.linearize.LinearizeLogger.*;

import java.util.*;

/**
 *  A table.
 */
public class Table extends Value {

        // FIXME: needs more efficient represenatation

        private List<Pair<Value,Value>> cases;

        public Table(List<Pair<Value,Value>> cases) {
                this.cases = cases;
        }

	/** Do not modify the returned list. */
	public List<Pair<Value,Value>> getCases() {
		return cases;
	}

        public Value firstValue() {
                return cases.get(0).snd;
        }

        public Value select(Value value) {
                for (Pair<Value,Value> c : cases) {
                        if (value.match(c.fst)) {
                                return c.snd;
                        }
                }
                throw new GFException("No value for <" + value + "> in " + this);
        }

        public String toString() {
                StringBuilder sb = new StringBuilder("table {");
                for (Pair<Value,Value> p : cases)
                        sb.append("<").append(p.fst).append("> => ").append(p.snd).append(" ");
                sb.append("}");
                return sb.toString();
        }

        public boolean equals(Object o) {
                return o instanceof Table && equals((Table)o);
        }

        public boolean equals(Table t) {
                return cases.equals(t.cases);
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
