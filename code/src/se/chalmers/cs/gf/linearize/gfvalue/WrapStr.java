/**
 * WrapStr.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.linearize.gfvalue;

import se.chalmers.cs.gf.util.PrefixMap;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.util.Pair.pair;

import java.util.*;

public class WrapStr {

	public static Value wrap(Value v, String before, String after) {
		return v.accept(new WrapVisitor(), pair(before,after));
	}

	private static class WrapVisitor
		implements ValueVisitor<Value,Pair<String,String>> {

		public Value visit(Concat v, Pair<String,String> arg) {
			return new Concat(v.getFirst().accept(this, pair(arg.fst,"")), 
					  v.getSecond().accept(this, pair("",arg.snd)));
		}

		public Value visit(Meta v, Pair<String,String> arg) {
			return defaultVisit(v, arg);
		}

		public Value visit(Param v, Pair<String,String> arg) {
			return v;
		}

		public Value visit(Pre v, Pair<String,String> arg) {
			PrefixMap<Value> map = new PrefixMap<Value>();
			for (Map.Entry<String,Value> e : v.getMap().entrySet()) {
				map.put(e.getKey(), e.getValue().accept(this,arg));
			}
			return new Pre(map);
		}

		public Value visit(Record v, Pair<String,String> arg) {
			Map<String,Value> fs = new TreeMap<String,Value>();
			for (Map.Entry<String,Value> e : v.getFields().entrySet()) {
				fs.put(e.getKey(), e.getValue().accept(this,arg));
			}
			return new Record(fs);
		}

		public Value visit(Str v, Pair<String,String> arg) {
			return defaultVisit(v, arg);
		}

		public Value visit(Table v, Pair<String,String> arg) {
			List<Pair<Value,Value>> cs = new ArrayList<Pair<Value,Value>>(v.getCases().size());
			for (Pair<Value,Value> p : v.getCases()) {
				cs.add(pair(p.fst, p.snd.accept(this,arg)));
			}
			return new Table(cs);
		}

		public Value defaultVisit(Value v, Pair<String,String> arg) {
			v = arg.snd.length() == 0 ? v : new Concat(v, new Str(arg.snd));
			v = arg.fst.length() == 0 ? v : new Concat(new Str(arg.fst), v);
			return v;
		}

	}
}
