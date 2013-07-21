/* parse-chart.scala
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

import scala.collection.mutable.{ MultiMap, HashMap, Map, Set }
import org.grammaticalframework.reader.{
  ApplProduction => Production,
  CncFun,
  CoerceProduction => Coercion,
  Production => AnyProduction }

private class Chart(var nextCat:Int) {

  /** **********************************************************************
   * Handling Productions
   * */
  private val productionSets : MultiMap[Int,AnyProduction] =
    new HashMap[Int, Set[AnyProduction]] with MultiMap[Int,AnyProduction]

  def addProduction(p:AnyProduction):Boolean = {
    if (productionSets.entryExists(p.getCategory(), p.==))
      return false
    else {
      //log.finest("Adding production " + p + " in chart.")
      productionSets.addBinding(p.getCategory(), p)
      this.nextCat = this.nextCat.max(p.getCategory() + 1)
      return true
    }
  }

  def addProduction(cat:Int, fun: CncFun, domain:Array[Int]):Boolean =
    this.addProduction(new Production(cat, fun, domain))


  def getProductions(resultCat : Int):Array[Production] =
    productionSets.get(resultCat) match {
      case Some(ps) =>
        for ((anyP:AnyProduction) <- ps.toArray;
             prod <- this.uncoerce(anyP) )
        yield prod
      case None => new Array[Production](0)
    }

  private def uncoerce(p : AnyProduction):Array[Production] = p match {
    case (p:Production) => Array(p)
    case (c:Coercion) => for (prod <- this.getProductions(c.getInitId()) ;
                              a <- this.uncoerce(prod) )
                          yield a
  }


  /** **********************************************************************
   *  Handling fresh categories
   * */
  private val categoryBookKeeper: HashMap[(Int, Int, Int, Int), Int]
  = new HashMap[(Int, Int, Int, Int), Int]()

  def getFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int =
    categoryBookKeeper.get((oldCat, l, j, k)) match {
      case None => this.generateFreshCategory(oldCat, l, j, k)
      case Some(c) => c
    }

  def hasCategory(oldCat:Int, cons:Int, begin:Int, end:Int):Boolean =
    categoryBookKeeper.contains((oldCat, cons, begin, end))

  def getCategory(oldCat:Int, cons:Int, begin:Int, end:Int):Option[Int] =
    categoryBookKeeper.get((oldCat, cons, begin, end))

  def generateFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int = {
    val cat = this.nextCat
    this.nextCat += 1
    categoryBookKeeper.update((oldCat, l, j, k), cat)
    return cat
  }

  override def toString() = {
    var s = "=== Productions: ===\n"
    for(cat <- this.productionSets.keys ;
        prod <- this.productionSets(cat))
      s += prod.toString + "\n"
    s += "=== passive items: ===\n"
    for(fk <- this.categoryBookKeeper.keys)
      s += fk + " -> " + this.categoryBookKeeper(fk) + "\n"
    s
  }
}

/**
 * this is used to keed track of sets of active items (the S_k)
 * */
private class ActiveSet {

  //val log = Logger.getLogger("org.grammaticalframework.parser")

  val store = new HashMap[Int, MultiMap[Int, (ActiveItem,Int)]]

  def add(cat:Int, cons:Int, item:ActiveItem, cons2:Int):Boolean =
    this.store.get(cat) match {
      case None => {
        val newMap = new HashMap[Int, Set[(ActiveItem,Int)]]
                              with MultiMap[Int, (ActiveItem,Int)]
        newMap.addBinding(cons,(item,cons2))
        this.store.update(cat, newMap)
        return true
      }
      case Some(map) =>
        if (map.entryExists(cons, (item,cons2).equals))
          return false
        else {
          map.addBinding(cons, (item,cons2))
          return true
        }
    }

  def get(cat:Int):Iterator[(ActiveItem, Int, Int)] =
    this.store.get(cat) match {
      case None => return Iterator.empty
      case Some(amap) => {
        for( k <- amap.keys.iterator ;
             (item, d) <- amap(k).iterator)
            yield (item, d, k)
      }
    }

  def get(cat:Int, cons:Int):Seq[(ActiveItem,Int)] =
    this.store.get(cat) match {
      case None => return Nil
      case Some(map) => map.get(cons) match {
        case None => return Nil
        case Some(s) => return s.toSeq
      }
    }
}
