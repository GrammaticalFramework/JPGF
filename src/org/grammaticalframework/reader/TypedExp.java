/* TypedExp.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework.reader;

public class TypedExp extends Expr{
	private Expr exp ; 
	private Type t ;
	 
 public TypedExp(Expr _exp, Type _t) 
  {exp = _exp;
   t = _t ;}

 public String toString()
 {return "Typed Expression : [Expr : "+exp.toString()+" , Type : "+t.toString()+"]";}

 public Expr getExpr() {return exp;}
 public Type getType() {return t;}
 
}
