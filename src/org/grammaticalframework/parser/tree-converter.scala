/* tree-converter.scala
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
package org.grammaticalframework.parser
/**
 * This convert abstract trees to intermediate trees and vice-versa
 * */

import org.grammaticalframework.intermediateTrees._
// import pgf.abstractTrees.{
//     Tree => ETree
//   , Lambda => ELambda
//   , Application => EApplication
//   , Literal => ELiteral
//   , MetaVariable => EMetaVariable
//   , Function => EFunction
//   , Variable => EVariable
//   , TypeSignature => ETypeSignature
//   , ImpliciteArgument => EImpliciterArgument
//   }
import org.grammaticalframework.Trees.Absyn.{
     Tree => ETree
   , Lambda => ELambda
   , Application => EApplication
   , Literal => ELiteral
   , MetaVariable => EMetaVariable
   , Function => EFunction
   , Variable => EVariable
  , StringLiteral
//   , TypeSignature => ETypeSignature
//   , ImpliciteArgument => EImpliciterArgument
}

object TreeConverter {
  def intermediate2abstract (t : Tree): ETree = {
    def c2a(t : Tree, vars:List[String]): ETree = t match {
      // Here we have to convert sugarized λ-abstraction (λx,y,z → ...)
      // to canonical ones (λx→λy→λz→...)
      case Lambda(lvars, body) =>
        lvars.foldRight(c2a(body, lvars.map(_._2).reverse ++ vars))(mkELambda)
      // Here variables are index by name but abstract
      // syntax uses de Bruijn indices
      case Variable(x) => new EVariable(vars.indexOf(x))
      // Here we have to desugarized applicaton :
      // f a b c becomes (((f a) b) c)
      case Application(fun,args) =>
        args.map(c2a(_, vars)).foldLeft(new EFunction(fun):ETree)(mkEApp)
      case Literal(value) => new ELiteral(new StringLiteral(value))
      case MetaVariable(id) => new EMetaVariable(id)
    }
    def mkEApp(f : ETree, x : ETree):ETree = new EApplication(f,x)
    def mkELambda(v :(Boolean, String) , body:ETree ):ETree = v match {
      case (bindType, name) => new ELambda(name, body)
    }
    c2a(t, Nil)
  }


//    tree2expr ys (Fun x ts) = foldl EApp (EFun x) (List.map (tree2expr ys) ts)
//    tree2expr ys (Abs xs t) = foldr (\(b,x) e -> EAbs b x e) (tree2expr (List.map snd (reverse xs)++ys) t) xs
//    tree2expr ys (Var x)    = case List.lookup x (zip ys [0..]) of
//                                Just i  -> EVar i
//                                Nothing -> error "unknown variable"







  // class IllegalLiteralOfFunctionType extends Exception
  // class IllegalBetaRedex extends Exception
  // class IllegalFunctionMetaVariable extends Exception

  // def abstract2intermediate(t : ETree):Tree = {
  //   /** Here we have to transform single argument
  //    * λ-abstraction into sugarized ones (λx→λy→... to λx,y→...)
  //    * To do that, we accumulate the variables in bindings
  //    * Applied to an expression that is not a λ-abstraction
  //    * it just calls mkTree on it */
  //   def mkLambda(t : ETree, bindings:List[(Boolean, String)], vars:List[String]):Tree =
  //     t match {
  //       case ELambda(bindType, cid, body) => {val bindings1 = (bindType, cid)::bindings
  //       				      mkLambda(body, bindings1, vars)}
  //       // We don't care about local types...
  //       case ETypeSignature(t, _) => mkLambda(t, bindings, vars)
  //       // for any other expression :
  //       case e => bindings match {
  //         case Nil => mkTree(e, Nil, vars)
  //         case _ => new Lambda (bindings.reverse, mkTree(e, Nil , vars))
  //       }
  //     }
  //   /** take an abstract tree e and turn it into an
  //    * intermediate tree.
  //    * @param args is the list of arguments e is applied to
  //    * @param vars is the list of binded variable
  //    * */
  //   def mkTree(e : ETree, args : List[Tree], vars:List[String] ):Tree = e match {
  //     case EApplication(e1,e2) => {
  //       /** here we call mkLambda on e2, and not mkTree,
  //        * because e2 could be a λ-abstraction */
  //       val args1 = mkLambda(e2,Nil,vars)::args
  //       mkTree(e1, args1, vars)
  //     }
  //     case ELiteral(l) => args match {
  //       case Nil => new Literal(l)
  //       case _ => throw new IllegalLiteralOfFunctionType
  //     }
  //     case EMetaVariable(n) => args match {
  //       case Nil => new MetaVariable(n)
  //       case _ => throw new IllegalFunctionMetaVariable
  //     }
  //     case ELambda(_, x, e) => throw new IllegalBetaRedex
  //     case EVariable(i) => new Variable(vars(i))
  //     case EFunction(f) => new Application(f, args)
  //     case ETypeSignature(e,_) => mkTree(e, args, vars)
  //     case e => {
  //       println(e)
  //       throw new Exception(e.toString)
  //     }
  //   }
  //   mkLambda(t, Nil, Nil)
  // }
}
