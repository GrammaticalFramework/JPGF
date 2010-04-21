package pgf.parse.pmcfg

//import scala.collection.jcl._
import scala.collection.mutable._

class ScalaChart(var nextCat:Int) {

  /** **********************************************************************
   * Handling Productions
   * */
  private val productionSets : MultiMap[Int,Production] = 
    new HashMap[Int, Set[Production]] with MultiMap[Int,Production]

  def add(p:Production):Boolean = {
    if (productionSets.entryExists(p.getCategory(), p.==))
      return false
    else {
      //log.finest("Adding production " + 
      //	 p.getCategory() + " -> " + p + " in chart.")
      productionSets.add(p.getCategory(), p)
      return true
    }
  }
  
  def getProductions(resultCat : Int):Array[Production] =
    productionSets.get(resultCat) match {
      case Some(ps) => ps.toArray
      case None => new Array[Production](0)
    }
  /** **********************************************************************
   *  Handling fresh categories
   * */
  private val categoryBookKeeper: HashMap[(Int, Int, Int, Int), Int] 
  = new HashMap[(Int, Int, Int, Int), Int]()
  
  def getFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int = 
    categoryBookKeeper.get((oldCat, l, j, k)) match {
      case None => this.createFreshCategory(oldCat, l, j, k)
      case Some(c) => c
    }

  private def createFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int = {
    val cat = this.nextCat
    this.nextCat += 1
    categoryBookKeeper.update((oldCat, l, j, k), cat)
    return cat
  }
}
