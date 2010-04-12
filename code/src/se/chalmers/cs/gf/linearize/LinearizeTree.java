/*
 * LinearizeTree.java
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
package se.chalmers.cs.gf.linearize;

import se.chalmers.cs.gf.abssyn.Tree;
import se.chalmers.cs.gf.linearize.gfvalue.*;
import se.chalmers.cs.gf.parse.Token;

import java.util.Collections;
import java.util.List;

/**
 *  Linearize an abstract syntax tree to a string representation
 *  of the tree itself.
 */
public class LinearizeTree implements Linearizer {

        public LinearizeTree() { }

        public List<Token> linearize(Tree t) {
                return Collections.singletonList(new Token(t.toString()));
        }

        public List<Token> linearizeWithRanges(Tree t) {
                return Collections.singletonList(new Token(t.toStringWithRanges()));
        }

        public String getName() {
                return "tree";
        }

        public boolean tryThisOne() {
                return true;
        }

}
