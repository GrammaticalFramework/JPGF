/* parser.scala 
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

import org.grammaticalframework.reader._
import org.grammaticalframework.intermediateTrees._
import org.grammaticalframework.Trees.Absyn.{ Tree => AbsSynTree }
import scala.collection.immutable.HashMap
//import scala.collection.mutable.Stack
import java.util.Stack


/* ************************************************************************* */
private class ActiveItem(val begin : Int,
                 val category:Int,
                 val function:CncFun,
                 val domain:Array[Int],
                 val constituent:Int,
                 val position:Int) {

  // get next symbol
  def nextSymbol():Option[Symbol] =
    if (position < function.sequence(constituent).length) {
      val symbol = function.sequence(constituent).symbol(position)
      return Some(symbol)
    }
    else
      return None

  // get next symbol
  def nextSymbol2():Symbol =
    if (position < function.sequence(constituent).length) {
      val symbol = function.sequence(constituent).symbol(position)
      return symbol
    }
    else
      return null

  def hasNextSymbol():Boolean =
    return position < function.sequence(constituent).length

  /* ************************************ *
   * Overrides
   * ************************************ */

  override def equals(o:Any):Boolean = o match {
      case (o:ActiveItem) => this.begin == o.begin &&
                             this.category == o.category &&
                             this.function == o.function && // CncFun,
                             this.domain.deep.equals(o.domain.deep) &&
                             this.constituent == o.constituent &&
                             this.position == o.position
      case _ => false
  }

  override def toString() =
    "[" + this.begin + ";" +
          this.category + "->" + this.function.name +
          "[" + this.domainToString + "];" + this.constituent + ";" +
          this.position + "]"
          
  def domainToString():String = {
    val buffer = new StringBuffer()
    for( d <- domain ) {
      buffer.append(d.toString)
    }
    buffer.toString
  }
}


/* ************************************************************************* */
/**
 * The ParseTries are used to keep track of the possible next symbols.
 * It is a trie where the symbol (edge labels) are string (words) and the values (node) are agendas
 * (stacks of ActiveItems)
 * The parse tries is used in the parsing algorithm when a dot is before a token. Then the dot is
 * moved after the tokens and the resulting active item is added to the trie (to the agenda indexed by
 * the words of the token.)
 * Then the scan operation is a simple lookup in the trie...
 * The trie is also used for predictions.
 * In gf, a token in a rule can consist of multiple words (separated by a whitespace), thus the trie is
 * needed and cannot be replaced by a simple map.
 *
 * @param value the value at this node.
 * */
private class ParseTrie(var value:Stack[ActiveItem]) {
  import scala.collection.mutable.HashMap

  val child = new HashMap[String,ParseTrie]

  def this() = this(new Stack)

  def add(key:Seq[String], value:Stack[ActiveItem]):Unit =
    this.add(key.toList, value)

  def add(key:Array[String], value:Stack[ActiveItem]):Unit =
    this.add(key.toList, value)

  def add(keys:List[String], value:Stack[ActiveItem]):Unit =
    keys match {
      case Nil => this.value = value
      case x::l => this.child.get(x) match {
        case None => {
          val newN = new ParseTrie
          newN.add(l,value)
          this.child.update(x, newN)
        }
        case Some(n) => n.add(l,value)
      }
    }

  def lookup(key:Seq[String]):Option[Stack[ActiveItem]] =
    this.lookup(key.toList)

  def lookup(key:Array[String]):Option[Stack[ActiveItem]] =
    this.lookup(key.toList)

  def lookup(key:List[String]):Option[Stack[ActiveItem]] =
    getSubTrie(key) match {
      case None => None
      case Some(t) => Some(t.value)
    }

  def lookup(key:String):Option[Stack[ActiveItem]] =
    this.lookup(key::Nil)

  def getSubTrie(key:List[String]):Option[ParseTrie] =
    key match {
      case Nil => Some(this)
      case x::l => this.child.get(x) match {
        case None => None
        case Some(n) => n.getSubTrie(l)
      }
    }

  def getSubTrie(key:String):Option[ParseTrie] =
    this.getSubTrie(key::Nil)

  def predict():Array[String] = this.child.keySet.toArray

  override def toString() = this.toStringWithPrefix("")

  def toStringWithPrefix(prefix:String):String = {
    prefix + "<" + this.value + ">" +
    this.child.keys.map(k =>
      prefix + k.toString + ":\n" +
      this.child(k).toStringWithPrefix(prefix + "  ")
    ).foldLeft("")((a:String,b:String) => a + "\n" + b)
  }
}
