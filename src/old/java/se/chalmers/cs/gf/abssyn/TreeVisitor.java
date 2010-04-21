/*
 * TreeVisitor.java
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

/**
 *  Visitor interface for visiting untyped abstract syntax trees.
 *
 *  @param <R> The return type of the visitor.
 *  @param <A> The type of the data argument.
 */
public interface TreeVisitor<R,A> {

        public R visit(Fun f, A arg);
        public R visit(IntLiteral l, A arg);
        public R visit(StringLiteral l, A arg);
        public R visit(MetaVariable v, A arg);

}