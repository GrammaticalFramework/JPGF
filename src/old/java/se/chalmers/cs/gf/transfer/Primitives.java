/*
 * Primitives.java
 * Copyright (C) 2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.transfer;

import se.chalmers.cs.gf.GFException;

public class Primitives extends Env {

	public Primitives() {

		// Types

		add("Integer", Value.TYPE);
		add("Double", Value.TYPE);
		add("String", Value.TYPE);

		// Integer operations

		add(new PrimIntUn("neg") {
				public Value apply(Integer i) { 
					return new Value.VInt(-i);
				}
			});
		add(new PrimIntBin("add") {
				public Value apply(Integer i,Integer j) {
					return new Value.VInt(i+j);
				}
			});
		add(new PrimIntBin("sub") {
				public Value apply(Integer i,Integer j) {
					return new Value.VInt(i-j);
				}
			});
		add(new PrimIntBin("mul") {
				public Value apply(Integer i,Integer j) {
					return new Value.VInt(i*j); 
				}
			});
		add(new PrimIntBin("div") {
				public Value apply(Integer i,Integer j) {
					return new Value.VInt(i/j);
				}
			});
		add(new PrimIntBin("mod") {
				public Value apply(Integer i,Integer j) {
					return new Value.VInt(i%j);
				}
			});
		add(new PrimIntBin("eq") {
				public Value apply(Integer i,Integer j) { 
					return Value.toBool(i==j); 
				}
			});
		add(new PrimIntBin("cmp") {
				public Value apply(Integer i,Integer j) { 
					return Value.toOrdering(i.compareTo(j));
				}
			});
		add(new PrimIntUn("show") {
				public Value apply(Integer i) {
					return new Value.VStr(i.toString());
				}
			});

		// Double operations

		add(new PrimDblUn("neg") {
				public Value apply(Double i) { 
					return new Value.VDbl(-i);
				}
			});
		add(new PrimDblBin("add") {
				public Value apply(Double i,Double j) {
					return new Value.VDbl(i+j);
				}
			});
		add(new PrimDblBin("sub") {
				public Value apply(Double i,Double j) {
					return new Value.VDbl(i-j);
				}
			});
		add(new PrimDblBin("mul") {
				public Value apply(Double i,Double j) {
					return new Value.VDbl(i*j); 
				}
			});
		add(new PrimDblBin("div") {
				public Value apply(Double i,Double j) {
					return new Value.VDbl(i/j);
				}
			});
		add(new PrimDblBin("mod") {
				public Value apply(Double i,Double j) {
					return new Value.VDbl(0.0);
				}
			});
		add(new PrimDblBin("eq") {
				public Value apply(Double i,Double j) { 
					return Value.toBool(i==j); 
				}
			});
		add(new PrimDblBin("cmp") {
				public Value apply(Double i,Double j) { 
					return Value.toOrdering(i.compareTo(j));
				}
			});
		add(new PrimDblUn("show") {
				public Value apply(Double i) {
					return new Value.VStr(i.toString());
				}
			});

		// String operations

		add(new PrimStrBin("add") {
				public Value apply(String i,String j) {
					return new Value.VStr(i+j);
				}
			});
		add(new PrimStrBin("eq") {
				public Value apply(String i,String j) { 
					return Value.toBool(i==j); 
				}
			});
		add(new PrimStrBin("cmp") {
				public Value apply(String i,String j) { 
					return Value.toOrdering(i.compareTo(j));
				}
			});
		add(new PrimStrUn("show") {
				public Value apply(String i) {
					return new Value.VStr(i); // FIXME: quote?
				}
			});
	}

	public void add(Value.VPrim prim) {
		add(prim.getName(), prim);
	}

	private abstract static class PrimIntUn extends Value.VPrim {
		public PrimIntUn(String name) { super("prim_"+name+"_Integer"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VInt) {
				return apply(((Value.VInt)arg).getInteger());
			}
			throw new GFException(arg + " is not an integer.");
		}
		public abstract Value apply(Integer i);
	}

	private abstract static class PrimIntBin extends Value.VPrim {
		public PrimIntBin(String name) { super("prim_"+name+"_Integer"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VInt) {
				final Integer i = ((Value.VInt)arg).getInteger();
				return new PrimIntUn(getName()) {
						public Value apply(Integer j) {
							return PrimIntBin.this.apply(i,j);
						}
					};
			}
			throw new GFException(arg + " is not an integer.");
		}
		public abstract Value apply(Integer i, Integer j);
	}

	private abstract static class PrimDblUn extends Value.VPrim {
		public PrimDblUn(String name) { super("prim_"+name+"_Double"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VDbl) {
				return apply(((Value.VDbl)arg).getDouble());
			}
			throw new GFException(arg + " is not a Double.");
		}
		public abstract Value apply(Double i);
	}

	private abstract static class PrimDblBin extends Value.VPrim {
		public PrimDblBin(String name) { super("prim_"+name+"_Double"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VDbl) {
				final Double i = ((Value.VDbl)arg).getDouble();
				return new PrimDblUn(getName()) {
						public Value apply(Double j) {
							return PrimDblBin.this.apply(i,j);
						}
					};
			}
			throw new GFException(arg + " is not a Double.");
		}
		public abstract Value apply(Double i, Double j);
	}


	private abstract static class PrimStrUn extends Value.VPrim {
		public PrimStrUn(String name) { super("prim_"+name+"_String"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VStr) {
				return apply(((Value.VStr)arg).getString());
			}
			throw new GFException(arg + " is not a String.");
		}
		public abstract Value apply(String i);
	}

	private abstract static class PrimStrBin extends Value.VPrim {
		public PrimStrBin(String name) { super("prim_"+name+"_String"); }
		public Value apply(Value arg) {
			if (arg instanceof Value.VStr) {
				final String i = ((Value.VStr)arg).getString();
				return new PrimStrUn(getName()) {
						public Value apply(String j) {
							return PrimStrBin.this.apply(i,j);
						}
					};
			}
			throw new GFException(arg + " is not a String.");
		}
		public abstract Value apply(String i, String j);
	}


}
