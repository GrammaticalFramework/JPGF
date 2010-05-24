package org.grammaticalframework.parser

import org.grammaticalframework.intermediateTrees._
import org.grammaticalframework.reader.{ApplProduction => Production, CncFun}

import java.util.logging._;


object TreeBuilder {

  val log = Logger.getLogger("org.grammaticalframework.parser.treebuilder")

  def buildTrees( chart:Chart, startCat:Int, length:Int ):List[Tree] = {
    log.fine("Building trees with start category " + (0, startCat, 0, length))
    chart.getCategory(startCat, 0, 0, length) match {
      case None => return Nil
      case Some(cat) => return mkTreesForCat(cat, chart)
    }
  }
  
  def mkTreesForCat(cat : Int, chart:Chart):List[Tree] = {
    log.fine("Making trees for category "+ cat)
    for {p <- chart.getProductions(cat).toList;
         t <- mkTreesForProduction(p, chart)}
    yield t
  }

  def mkTreesForProduction( p:Production, chart:Chart):List[Tree] = {
      if (p.domain.length == 0)
         List(new Application(p.function.name, Nil))
      else
         for (args <- listMixer( p.domain.toList.map(mkTreesForCat(_,chart)) ) )
         yield new Application(p.function.name, args)
  }

  def listMixer(l:List[List[Tree]]):List[List[Tree]] = l match {
    case Nil => Nil
    case List(subL) => subL.map(List(_))
    case head::tail => {
      for {first <- head;
           then <- listMixer(tail)}
      yield first::then
    }
  }
    
  
}
