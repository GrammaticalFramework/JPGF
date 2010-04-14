/*
 * GFTreeToTree.java
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
package se.chalmers.cs.gf.abssyn;

import se.chalmers.cs.gf.SyntaxTree.*;
import se.chalmers.cs.gf.SyntaxTree.Absyn.*;

/**
 *  Builds an untyped GF abstract syntax tree from a tree
 *  in the BNFC-generated abstract syntax for trees.
 */
public class GFTreeToTree implements Tr.Visitor<Tree,Integer> {

        public static Tree toTree(Tr t) {
                return t.accept(new GFTreeToTree(), 0);
        }
        
        public Tree visit(TNode tnode, Integer arg) {
                Tree[] children = new Tree[tnode.listtr_.size()];
                int i = 0;
                for (Tr c : tnode.listtr_)
                        children[i++] = c.accept(this, arg);
                return new Fun(tnode.ident_, children);
        }

        public Tree visit(TAtom tatom, Integer arg) {
                return new Fun(tatom.ident_);
        }

        public Tree visit(TStr tstr, Integer arg) {
                return new StringLiteral(tstr.string_);
        }

        public Tree visit(TInt tint, Integer arg) {
                return new IntLiteral(tint.integer_);
        }

}
