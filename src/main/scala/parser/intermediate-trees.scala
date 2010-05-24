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
