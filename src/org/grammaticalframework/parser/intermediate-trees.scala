/* intermediate-trees.scala
 * Copyright (C) 2012 Grégoire Détrez
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
package org.grammaticalframework.intermediateTrees
/**
 * /!\ OBS
 * This is not exactly concrete trees, more like an intermediate tree...
 * */

sealed abstract class Tree
// lambda abstraction. The list of variables is non-empty
case class Lambda(vars : List[(Boolean, String)], body : Tree) extends Tree
// Variable
case class Variable (cid : String) extends Tree
// Function application
case class Application(fun : String, args : List[Tree]) extends Tree
case class Literal(value : String) extends Tree
//class Meta(id:  Int) extends Tree
case class MetaVariable(id : Int) extends Tree

//case class LambdaVar(name : String, bindType : Boolean)
