package org.grammaticalframework.parser

import org.grammaticalframework.reader._
import org.grammaticalframework.intermediateTrees._
import org.grammaticalframework.Trees.Absyn.{ Tree => AbsSynTree }
import scala.collection.mutable.Stack

/* ************************************************************************* */
/** ParseState
 * The parse-state is the core of the parser.
 * */
private class ParseState(val grammar:Concrete, val length:Int) {
  private val startCat = this.grammar.startCat
  private var trie = new ParseTrie
  private val chart = new Chart(100,this.length) // TODO: 10 is a bad value...
  private var agenda = new Stack[ActiveItem]
  private var position = 0
  // Adding base productions in the chart
  this.grammar.productions.foreach( p => this.chart.addProduction(p) )
  init()
  compute()
  
  /**
   * returns the set of possible next words
   * */
  def predict():Array[String] = this.trie.predict

  def getTrees():List[AbsSynTree] = {
    val chart = this.chart
    val startCat = this.startCat
    val length = this.length
    val parseTrees = TreeBuilder.buildTrees(chart, startCat, length)
    return parseTrees.map(TreeConverter.intermediate2abstract)
  }

  def scan(token:String):Boolean = this.trie.getSubTrie(token) match {
    case None => return false
    case Some(newTrie) => {
      newTrie.lookup(Nil) match {
        case None => return false
        case Some(agenda) => {
          this.trie = newTrie
          this.position += 1
          this.agenda = agenda
          this.compute()
        }
      }
    }
    //log.finer(this.trie.toString)
    return true
  }

  private def init() = {
    //println("Initializing parse state with category : " + startCat)
    for (id <- startCat.firstID until (startCat.lastID + 1) ;
	 prod <- chart.getProductions(id) ) {
	   val it = new ActiveItem(0, id, prod.function,
                                   prod.domain, 0, 0)
           agenda += it
	 }
  }

  private def compute() = {
    while (!agenda.isEmpty) {
      val e:ActiveItem = agenda.pop();
      processActiveItem(e)
    }
  }

  private def processActiveItem(item:ActiveItem) = {
    val j = item.begin
    val A = item.category
    val f = item.function
    val B = item.domain
    val l = item.constituent
    val p = item.position
    //log.fine("Processing active item " + item + " from the agenda")
    item.nextSymbol match {
      // ------------------------- before s∈T -------------------------
      case Some(tok:ToksSymbol) => {
        //log.fine("Case before s∈T")
        val tokens = tok.tokens
        val i = new ActiveItem(j, A, f, B, l, p + 1)
        // SCAN
        // this.trie.add(tokens, i)
        val newAgenda = this.trie.lookup(tokens) match {
          case None => {val a = new Stack[ActiveItem]
                        this.trie.add(tokens,a)
                        a}
          case Some(a) => a
        }
        //log.fine("Adding item " + i + " for terminals "
        //           + tokens.map(_.toString) + "(from item : " + item + ")")
        newAgenda += i
      }

      // ------------------------- before <d,r> -----------------------
      case Some(arg:ArgConstSymbol) => {
        //log.finest("Case before <d,r>")
        val d = arg.arg
        val r = arg.cons
        val Bd = item.domain(d)
        if (chart.active(this.position).add(Bd,r,item,d)) {
          for (prod <- chart.getProductions(Bd)) {
            val it = new ActiveItem(this.position, Bd, prod.function,
                                    prod.domain, r, 0)
            agenda += it
          }
        }
        chart.getCategory(Bd,r, this.position, this.position) match {
          case None => {}
          case Some(catN) => {
            val newDomain = B.subArray(0, B.length)
            newDomain(d) = catN
            val it = new ActiveItem(j, A, f, newDomain, l, p + 1)
            //log.finest("Adding " + it + " to the agenda")
            agenda += it
          }
        }
      }

      // ------------------------- at the end --------------------------
      case None => {
        //log.finest("Case at the end")
        chart.getCategory(A, l, j, this.position) match {
          case None => {
            val N = chart.generateFreshCategory(A, l, j, this.position)
            for( (ip,d) <- chart.active(j).get(A,l)) {
              //log.finest("combine with " + ip + " (" + d + ")")
              val domain = ip.domain.subArray(0, ip.domain.length)
              domain(d) = N
              val i = new ActiveItem(ip.begin, ip.category, ip.function,
                                     domain, ip.constituent, ip.position + 1)
              //log.finest("Adding " + i + " to the agenda")
              agenda += i
            }
            chart.addProduction(N, f, B)
          }
          case Some(n) => {
            for((xprime, dprime, r) <- chart.active(this.position).get(n)) {
              val i = new ActiveItem(this.position, n, f, B, r, 0)
              //log.finest("Adding "+ i + " to the agenda")
              agenda += i
            }
            chart.addProduction(n, f, B)
          }
        }
      }
    }
  }

  /* Overrides */
  
  override def toString() =
    "= ParseState =\n" +
    "== Chart ==\n" +
    this.chart.toString() +
    "== Trie ==\n" +
    this.trie.toString()
}

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

  /* ************************************ *
   * Overrides
   * ************************************ */

  override def equals(o:Any):Boolean = o match {
      case (o:ActiveItem) => this.begin == o.begin &&
                             this.category == o.category &&
                             this.function == o.function && // CncFun,
                             this.domain.deepEquals(o.domain) &&
                             this.constituent == o.constituent &&
                             this.position == o.position
      case _ => false
  }

  override def toString() =
    "[" + this.begin + ";" +
          this.category + "->" + this.function.name +
          "[" + this.domain.map(_.toString) + "];" + this.constituent + ";" +
          this.position + "]"

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
