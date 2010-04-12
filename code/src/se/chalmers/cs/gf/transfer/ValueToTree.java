/*
 * ValueToTree.java
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
import se.chalmers.cs.gf.abssyn.*;

public class ValueToTree {

	public static Tree toTree(Value v) {
		return v.accept(new ToTree(), null);
	}


	public static class ToTree implements Value.Visitor<Tree,Object> {
		public Tree visit(Value.VStr p, Object arg) {
			return new StringLiteral(p.getString());
		}
		public Tree visit(Value.VInt p, Object arg) {
			return new IntLiteral(p.getInteger());
		}
		public Tree visit(Value.VDbl p, Object arg) {// FIXME: implement
			return cantDoThat(p);
		}
		public Tree visit(Value.VType p, Object arg) { return cantDoThat(p); }
		public Tree visit(Value.VRec p, Object arg) { return cantDoThat(p); }
		public Tree visit(Value.VClos p, Object arg) { return cantDoThat(p); }
		public Tree visit(Value.VCons p, Object arg) {
			int n = p.numArgs();
			Tree[] cs = new Tree[n];
			for (int i = 0; i < n; i++) {
				cs[i] = p.getArg(i).accept(this, arg);
			}
			return new Fun(p.getCons(), cs);
		}
		public Tree visit(Value.VPrim p, Object arg) { return cantDoThat(p); }
		public Tree visit(Value.VMeta p, Object arg) { 
			return new MetaVariable();
		}
		private Tree cantDoThat(Value v) {
			throw new GFException("Can't convert " + v + " to a Tree.");
		}
	}

}
