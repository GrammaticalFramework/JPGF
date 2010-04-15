package pgf.abstractTrees

sealed abstract class Tree

// Lambda abstraction : (λcid.body)
case class Lambda(bindType : Boolean, cid : String, body : Tree) extends Tree {
  override def toString:String  = "(λ" + this.cid + "." + this.body + ")"
}

/**
 * Variable with de Bruijn index
 * de Bruijn indices are used istead of variable String name
 * to avoid the need of α-conversion
 * But we keep them in the lambda abstraction because we need them in linearization
 * (the fields $0 is the name of the variable.)
 * TODO: is that correct ????
 */
case class Variable(index : Int) extends Tree {
  override def toString = "$" + this.index
}

// Function application : fun x
case class Application(fun : Tree, x : Tree) extends Tree {
  override def toString = "(" + this.fun + " " + this.x + ")"
}

// Litteral : String, Int or Float
case class Literal(value : String) extends Tree {
  override def toString = this.value
}

case class MetaVariable( id : Int ) extends Tree {
  override def toString = "META_" + this.id
}

// Function of data constructor : fun f : /SomeType/
case class Function( cid : String ) extends Tree {
  override def toString = this.cid
}

// Local type signature : (x : t)
case class TypeSignature( x : Tree, t : Type) extends Tree

/**
 * Implicite argument in expression : ?????
 * TODO: What is it ?
 * */
case class ImpliciteArgument( e : Tree) extends Tree

class Type
