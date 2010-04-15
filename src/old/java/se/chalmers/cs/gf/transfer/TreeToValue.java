/*
 * TreeToValue.java
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

import se.chalmers.cs.gf.abssyn.*;

import java.math.BigInteger;
import java.util.*;

public class TreeToValue {

	public static Value toValue(Tree t) {
		return t.accept(new ToValue(), null);
	}

	private static class ToValue implements TreeVisitor<Value,Object> {

		private BigInteger nextMeta = BigInteger.ZERO;

		public Value visit(Fun f, Object arg) {
			List<Value> args = new ArrayList<Value>(f.countChildren());
			for (Tree t : f.getChildren()) {
				args.add(t.accept(this, arg));
			}
			return new Value.VCons(f.getLabel(), args);
		}

		public Value visit(IntLiteral l, Object arg) {
			return new Value.VInt(l.getValue());
		}

		public Value visit(StringLiteral l, Object arg) {
			return new Value.VStr(l.getValue());
		}

		public Value visit(MetaVariable v, Object arg) {
			Value value = new Value.VMeta(nextMeta);
			nextMeta = nextMeta.add(BigInteger.ONE);
			return value;
		}

	}


}
