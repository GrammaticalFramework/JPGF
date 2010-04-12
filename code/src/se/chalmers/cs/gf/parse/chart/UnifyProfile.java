/*
 * UnifyProfile.java
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
package se.chalmers.cs.gf.parse.chart;

import se.chalmers.cs.gf.abssyn.*;

public class UnifyProfile extends Profile {

        private int[] profile;

        public UnifyProfile(int[] profile) {
                this.profile = profile;
        }

        public Tree buildChild(Tree[] children) {
                Tree t = new MetaVariable();
                for (int x : profile) {
                        t = unifyTrees(t, children[x]);
                        if (t == null)
                                return null;
                }

                return t;
        }

        public static Tree unifyTrees(Tree t1, Tree t2) {
                if (t1 == null || t2 == null)
                        throw new NullPointerException();
                if (t2 instanceof MetaVariable)
                        return t1;    // FIXME: hacky, would like multiple dispatch

                Tree t = t1.accept(new TreeUnifier(), t2);
//                 if (t == null)
//                         System.err.println("Can't unify " + t1 + " and " + t2);
//                 else
//                         System.err.println("unifyTrees(" + t1 + ", " + t2 + ") => " + t);

                return t;
        }

        // FIXME: add diagnostic messages when it fails
        private static class TreeUnifier implements TreeVisitor<Tree,Tree> {
                public Tree visit(Fun f, Tree t) {
                        if (!(t instanceof Fun))
                                return null;
                        Fun g = (Fun)t;
                        if (!f.getLabel().equals(g.getLabel()))
                                return null;
                        Tree[] cs1 = f.getChildren();
                        Tree[] cs2 = g.getChildren();
                        if (cs1.length != cs2.length)
                                return null;
                        Tree[] cs = new Tree[cs1.length];
                        for (int i = 0; i < cs.length; i++) {
                                Tree c = unifyTrees(cs1[i], cs2[i]);
                                if (c == null)
                                        return null;
                                cs[i] = c;
                        }
                        return new Fun(f.getLabel(), cs, Tree.unifyInputRanges(f,t));
                }
                public Tree visit(IntLiteral l, Tree t) {
                        if (l.equals(t)) {
				return new IntLiteral(l, Tree.unifyInputRanges(l,t));
			} else  {
				return null;
			}
                }
                public Tree visit(StringLiteral l, Tree t) {
                        if (l.equals(t)) {
				return new StringLiteral(l, Tree.unifyInputRanges(l,t));
			} else  {
				return null;
			}
                }
                public Tree visit(MetaVariable v, Tree t) {
                        return t;
                }
        }

        public String toString() {
                return java.util.Arrays.toString(profile);
        }

}
