/*
 * ApplyTransfer.java
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

import se.chalmers.cs.gf.abssyn.Tree;
import se.chalmers.cs.gf.Core.Absyn.*;

public class ApplyTransfer {

	public static Tree applyTransfer(Exp transfer, Tree tree, Env env) {
		Value f = InterpretCore.eval(transfer, env);
		Value tv = TreeToValue.toValue(tree);
		Value v = InterpretCore.apply(f,tv);
		Tree t = ValueToTree.toTree(v);
		return t;
	}

}
