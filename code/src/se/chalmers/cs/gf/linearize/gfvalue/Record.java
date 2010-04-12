package se.chalmers.cs.gf.linearize.gfvalue;

import se.chalmers.cs.gf.GFException;
import static se.chalmers.cs.gf.linearize.LinearizeLogger.*;

import java.util.*;


/**
 *  Record value.
 */
public class Record extends Value {

        private Map<String,Value> fields;

        public Record() {
                this.fields = new TreeMap<String,Value>();
        }

        public Record(Map<String,Value> fields) {
                this.fields = fields;
        }

	/** Do not modify the returned map. */
	public Map<String,Value> getFields() {
		return fields;
	}

        public boolean hasField(String name) {
                return fields.containsKey(name);
        }

        public Value get(String name) {
                Value v = fields.get(name);
                if (v == null)
                        throw new GFException("Field " + name + " does not exist in " + this);
                return v;
        }

        public void set(String name, Value value) {
                fields.put(name, value);
        }

        public int hashCode() {
                return toString().hashCode();
        }

        public boolean equals(Object o) {
                return (o instanceof Record) && equals((Record)o);
        }

        public boolean equals(Record r) {
                return fields.equals(r.fields);
        }

        public boolean match(Value p) {
                if (!(p instanceof Record))
                        return false;
                Record r = (Record)p;
                for (Map.Entry<String,Value> e : r.fields.entrySet()) {
                        Value v = fields.get(e.getKey());
                        if (!v.match(e.getValue())) {
                                return false;                                
                        }
                }
                return true;
        }

        public String toString() {
                StringBuilder sb = new StringBuilder("{");
                for (Map.Entry e : fields.entrySet()) {
                        sb.append(e.getKey());
                        sb.append(" = ");
                        sb.append(e.getValue());
                        sb.append(", ");
                }
                // hack, delete final comma
                if (!fields.isEmpty()) {
                        int n = sb.length();
                        sb.delete(n-2,n);
                }
                sb.append("}");
                return sb.toString();
        }

        public static Record ss(String s) {
                Record r = new Record();
                r.set("s", new Str(s));
                return r;
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
