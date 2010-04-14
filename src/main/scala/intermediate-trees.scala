package se.chalmers.cs.pgf.parse.trees


/**
 * /!\ OBS
 * This is not exactly concrete trees, more like an intermediate tree...
 * */

import se.chalmers.cs.pgf.`abstract`.{
    Tree => ETree
  , Lambda => ELambda
  , Application => EApplication
  , Literal => ELiteral
  , MetaVariable => EMetaVariable
  , Function => EFunction
  , Variable => EVariable
  , TypeSignature => ETypeSignature
  , ImpliciteArgument => EImpliciterArgument
  }

class Tree
// lambda abstraction. The list of variables is non-empty
case class Lambda(vars : List[LambdaVar], body : Tree) extends Tree
// Variable
case class Variable (cid : String) extends Tree
// Function application
case class Application(fun : String, args : List[Tree]) extends Tree
case class Literal(value : String) extends Tree
//class Meta(id:  Int) extends Tree
case class MetaVariable(id : Int) extends Tree

case class LambdaVar(name : String, bindType : Boolean)

object TreeConversion {
  def concrete2astract (t : Tree): ETree = {
    def c2a(t : Tree): ETree = t match {
      // Here we have to convert sugarized λ-abstraction (λx,y,z → ...) 
      // to canonical ones (λx→λy→λz→...)
      case Lambda(vars, body) => vars.foldRight(c2a(body))(mkELambda)
      // Here variables are index by name but abstract 
      // syntax uses de Bruijn indices
      case Variable(x) => new ETree
      // Here we have to desugarized applicaton : 
      // f a b c becomes (((f a) b) c)
      case Application(fun,args) => args.map(c2a).foldLeft(new EFunction(fun):ETree)(mkEApp)
      case Literal(value) => new ELiteral(value)
      case MetaVariable(id) => new EMetaVariable(id)
    }
    c2a(t)
  }

  def mkEApp( f : ETree, x : ETree):ETree = new EApplication(f,x)
  def mkELambda(v :LambdaVar , body:ETree ):ETree = v match {
    case LambdaVar(name, bindType) => new ELambda(bindType, name, body)
  }

//    tree2expr ys (Fun x ts) = foldl EApp (EFun x) (List.map (tree2expr ys) ts) 
//    tree2expr ys (Abs xs t) = foldr (\(b,x) e -> EAbs b x e) (tree2expr (List.map snd (reverse xs)++ys) t) xs
//    tree2expr ys (Var x)    = case List.lookup x (zip ys [0..]) of
//                                Just i  -> EVar i
//                                Nothing -> error "unknown variable"

}
