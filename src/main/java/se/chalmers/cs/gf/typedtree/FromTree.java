/*
 * FromTree.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.typedtree;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.abssyn.*;

/**
 *  Base class for generated classes for converting from untyped to
 *  typed trees.
 */
public abstract class FromTree {

        public String toString(Tree t) {
                if (!(t instanceof StringLiteral))
                        throw new GFException(t + " is not a string");
                return ((StringLiteral)t).getValue();
        }

        public Integer toInteger(Tree t) {
                if (!(t instanceof IntLiteral))
                        throw new GFException(t + " is not an integer");
                return new Integer(((IntLiteral)t).getValue());
        }

}
