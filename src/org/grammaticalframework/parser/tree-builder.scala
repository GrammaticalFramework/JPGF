/* tree-builder.scala
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

import org.grammaticalframework.intermediateTrees._
import org.grammaticalframework.reader.{
  ApplProduction => Production,
  CncFun,
  CncCat
}
//import java.util.logging._;


object TreeBuilder {

  //val log = Logger.getLogger("org.grammaticalframework.parser.TreeBuilder")

  def buildTrees( chart:Chart, startCat:CncCat, length:Int ):List[Tree] = {
    //log.fine("Building trees with start category " + (0, startCat, 0, length))
    (startCat.firstID until startCat.lastID + 1).flatMap( catID =>
      chart.getCategory(catID, 0, 0, length) match {
        case None => Nil
        case Some(cat) => mkTreesForCat(cat, chart)
      }).toList
  }

  def mkTreesForCat(cat : Int, chart:Chart):List[Tree] = {
    //log.fine("Making trees for category "+ cat)
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
