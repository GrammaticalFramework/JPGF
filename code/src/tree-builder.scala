package se.chalmers.cs.pgf.parse.pmcfg

import se.chalmers.cs.pgf.parse.trees._

class Chart

object TreeBuilder {
  def buidTrees( c : Chart ):List[Tree] = {
    for(c.getPassive(0,fin).fileter(cat = deridedCat).mkTree)
  }
  
  def mkTreeForEdge( c : Chart, e : PassiveEdge) = {
    val c = e.getFreshCat()
    forall(p <- c.getProdByReturnCat(c))
      p.
  }
  def mkTreeForCat(c : Category) = {
    forall(p <- c.getProdByReturnCat(c))
      for (c1 <- p.domain)
	c1' <- bild tree for c1
    return build tree for function p.abstractFunction with arguments the c1's
  }
    
}
