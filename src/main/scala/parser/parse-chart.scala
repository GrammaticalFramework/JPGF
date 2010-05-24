package org.grammaticalframework.parser

//import scala.collection.jcl._
import scala.collection.mutable._
import org.grammaticalframework.reader.{
  ApplProduction => Production,
  CncFun,
  CoerceProduction => Coercion,
  Production => AnyProduction }
import java.util.logging._;

class Chart(var nextCat:Int, val length:Int) {

  val log = Logger.getLogger("PGF.Parsing")


  /** **********************************************************************
   * Handling Active Items (the S_k's)
   * */
  // FIXME: array is almost twice as big as a it needs to be.
  //        could fix this with a non-square array
  val active = new Array[ActiveSet](length + 1)
  for (i <- active.indices)
    active(i) = new ActiveSet

  /** **********************************************************************
   * Handling Productions
   * */
  private val productionSets : MultiMap[Int,AnyProduction] =
    new HashMap[Int, Set[AnyProduction]] with MultiMap[Int,AnyProduction]

  def addProduction(p:AnyProduction):Boolean = {
    if (productionSets.entryExists(p.getCategory(), p.==))
      return false
    else {
      log.finest("Adding production " + p + " in chart.")
      productionSets.add(p.getCategory(), p)
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


class ActiveSet {

  val log = Logger.getLogger("PGF.Parsing")

  val store = new HashMap[Int, MultiMap[Int, (ActiveItem,Int)]]

  def add(cat:Int, cons:Int, item:ActiveItem, cons2:Int):Boolean =
    this.store.get(cat) match {
      case None => {
        val newMap = new HashMap[Int, Set[(ActiveItem,Int)]]
                              with MultiMap[Int, (ActiveItem,Int)]
        newMap.add(cons,(item,cons2))
        this.store.update(cat, newMap)
        return true
      }
      case Some(map) =>
        if (map.entryExists(cons, (item,cons2) ==))
          return false
        else {
          map.add(cons, (item,cons2))
          log.finest("Adding " + (cat,cons) + " -> " + (item,cons2) + " to ActiveSet")
          return true
        }
    }

  def get(cat:Int):Iterator[(ActiveItem, Int, Int)] =
    this.store.get(cat) match {
      case None => return Iterator.empty
      case Some(map) => {
        for(k <- map.keys ;
            (item,d) <- map(k).elements)
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
