package pgf.parse.pmcfg

import  scala.collection.mutable.HashMap 


class FreshCategories(var nextCat:Int) {
  private val keeper: HashMap[(Int, Int, Int, Int), Int] 
  = new HashMap[(Int, Int, Int, Int), Int]()
  
  def getFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int = 
    keeper.get((oldCat, l, j, k)) match {
      case None => this.createFreshCategory(oldCat, l, j, k)
      case Some(c) => c
    }

  def createFreshCategory(oldCat:Int, l:Int, j:Int, k:Int):Int = {
    val cat = this.nextCat
    this.nextCat += 1
    keeper.update((oldCat, l, j, k), cat)
    return cat
  }
}
