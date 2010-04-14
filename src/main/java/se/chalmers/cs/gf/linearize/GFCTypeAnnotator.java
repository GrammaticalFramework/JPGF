/*
 * GFCTypeAnnotator.java
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
package se.chalmers.cs.gf.linearize;

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.GFException;

/**
 *  Does type annotation of abstract syntax trees.
 */
public class GFCTypeAnnotator implements TypeAnnotator {

        private GFCModule module;

        /**
         *  @param module Abstract module used to get types of functions.
         */
        public GFCTypeAnnotator(GFCModule module) {
                this.module = module;
        }

        /**
         *  Type-annotate all meta-variables in the given tree.
         *  Returns the new tree.
         */
        public Tree annotate(Tree t) {
                String startcat = null; //FIXME: figure out startcat
                return t.accept(new Annotator(), startcat);
        }

        private class Annotator implements TreeVisitor<Tree,String> {
                public Tree visit(Fun f, String type) {
                        Function fun = module.findFunction(f.getLabel());

                        if (fun.getArity() != f.countChildren())
                                throw new GFException("TypeAnnotator: " + fun.getName() 
                                                      + " needs " + fun.getArity()
                                                      + " children, node " + f.getLabel() 
                                                      + " has " + f.countChildren());


                        int n = fun.getArity();
                        Tree[] children = new Tree[n];
                        for (int i = 0; i < n; i++) 
                                children[i] = f.getChild(i).accept(this, fun.getArgCat(i));
                        return new Fun(f.getLabel(), children, f.getInputRanges());
                }

                public Tree visit(IntLiteral l, String type) {
                        return l;
                }

                public Tree visit(StringLiteral l, String type) {
                        return l;
                }

                public Tree visit(MetaVariable v, String type) {
                        return new MetaVariable(type);
                }
        }

}
