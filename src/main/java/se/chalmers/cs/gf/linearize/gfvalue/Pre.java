package se.chalmers.cs.gf.linearize.gfvalue;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.PrefixMap;
import se.chalmers.cs.gf.util.Pair;

import java.util.List;

/**
 *  Prefix-dependent choice.
 */
public class Pre extends Value {

        private PrefixMap<Value> map;

        public Pre(Value def, List<Pair<Value,List<String>>> vs) {
                if (def == null)
                        throw new GFException("No default value for prefix-dependent rule.");

                map = new PrefixMap<Value>();
                map.put("", def);
                for (Pair<Value,List<String>> p : vs) {
                        Value v = p.fst;
                        for (String s : p.snd) {
                                map.put(s, v);
                        }
                }
        }

        public Pre(PrefixMap<Value> m) {
		this.map = map;
        }


	/** Do not modify the returned map. */
	public PrefixMap<Value> getMap() {
		return map;
	}

	public Value getValue(String next) {
		return map.get(next);
	}

        public String toString() {
                return map.toString();
        }

        public boolean equals(Object o) {
                return o instanceof Pre && equals((Pre)o);
        }

        public boolean equals(Pre p) {
                return map.equals(p.map);
        }

	public <R,A> R accept(ValueVisitor<R,A> v, A arg) { 
		return v.visit(this, arg); 
	}

}
