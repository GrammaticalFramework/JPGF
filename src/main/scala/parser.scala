package pgf.parsing

import pgf.reader._
import pgf.intermediateTrees._
import pgf.util.Trie
import scala.collection.mutable.Stack 


/**
 * This is the main parser class.
 * It keeps the latest parser state until parse() is caled again.
 * Then the parser state is reseted.
 * When parse() has not been called, act as if parse() have been called on the
 * empty token list.
 * */
class Parser(val grammar:Concrete) {
  var ps = new ParseState(this, this.grammar, 0)
  
  def printState = println(this.ps.toString())
  
  def parse(tokens : Seq[String], startCat: Int):Unit = {
    ps = new ParseState(this, this.grammar, tokens.length)
    for (token <- tokens) {
      println("Scanning token " + token)
      if (!ps.scan(token)) {
        println("Scan failed...")
        return
      }
    }
  }
  
  def parse(tokens:Seq[String]):Unit = parse(tokens, this.grammar.startCat)
  
  def printStats() {
    println("Parser for language: " + this.grammar.name())
    println("Productions: " + this.ps.toString())
  }
  
  def getTrees = ps.getTrees()
}


class ParseState(val parser:Parser, val grammar:Concrete, val length:Int) {
  private val startCat = 1//this.grammar.startCat
  private var trie = new Trie[String,Stack[ActiveItem]]
  private val chart = new Chart(10,this.length) // TODO: 0 is a bad value...
  private var agenda = new Stack[ActiveItem]
  private var position = 0
  // Adding base productions in the chart
  this.grammar.productions.filter{_.isInstanceOf[ApplProduction]}.foreach(p => this.chart.addProduction(p.asInstanceOf[ApplProduction]))
  init()
  compute()
  
  def getTrees():List[Tree] = {
    val chart = this.chart
    val startCat = this.startCat
    val length = this.length
    return TreeBuilder.buildTrees(chart, startCat, length)
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
    return true
  }
  
  private def init() = {
    println("Initializing parse state with start cat " + this.startCat)
    for (prod <- chart.getProductions(this.startCat)) {
      val it = new ActiveItem(0, this.startCat, prod.function, 
                              prod.domain, 0, 0)
      agenda += it
      
    }
  }
      
    
  
  private def compute() = {
    println("Computing parse state for k=" + this.position)
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
    println("Processing active item " + item + " from the agenda")
    item.nextSymbol match {
      // before s∈T
      case Some(tok:ToksSymbol) => {
        println("Case before s∈T")
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
        println("Adding item " + i + " for terminals " + tokens.map(_.toString))
        newAgenda += i
      }

        //
      //// before <d,r>
      //

      case Some(arg:ArgConstSymbol) => {
        println("Case before <d,r>")
        val d = arg.arg
        val r = arg.cons
        val Bd = item.domain(d)
        if (chart.active(this.position).add(Bd,r,item,d)) {
          for (prod <- chart.getProductions(Bd)) {
            val it = new ActiveItem(this.position, Bd, prod.function, 
                                    prod.domain, r, 0)
            println("Adding " + it + " to the agenda*")
            agenda += it
          }
        }
        chart.getCategory(Bd,r, this.position, this.position) match {
          case None => {}
          case Some(catN) => {
            val newDomain = B.subArray(0, B.length)
            newDomain(d) = catN
            val it = new ActiveItem(j, A, f, newDomain, l, p + 1)
            println("Adding " + it + " to the agenda")
            agenda += it
          }
        }
      }

      // ** at the end
      case None => {
        println("Case at the end")
        chart.getCategory(A, l, j, this.position) match {
          case None => {
            val N = chart.generateFreshCategory(A, l, j, this.position)
            for( (ip,d) <- chart.active(j).get(A,l)) {
              println("combine with " + ip + " (" + d + ")")
              val domain = ip.domain.subArray(0, ip.domain.length)
              domain(d) = N
              val i = new ActiveItem(ip.begin, ip.category, ip.function,
                                     domain, ip.constituent, ip.position + 1)
              println("Adding " + i + " to the agenda")
              agenda += i
            }
            chart.addProduction(N, f, B)
          }
          case Some(n) => {
            for((xprime, dprime, r) <- chart.active(this.position).get(n)) {
              val i = new ActiveItem(this.position, n, f, B, r, 0)
              println("Adding "+ i + " to the agenda")
              agenda += i
            }
            chart.addProduction(n, f, B)
          }
        }
        
      }
      // return (S,P,C)
    }
  }


  override def toString() = {
    "ParseState\n" +
    "Chart:\n" +
    chart.toString()
  }
}


class ActiveItem(val begin : Int, 
                 val category:Int,
                 val function:CncFun,
                 val domain:Array[Int],
                 val constituent:Int,
                 val position:Int) {
  
  class NoNextItem extends Exception
  // get next symbol
  def nextSymbol():Option[Symbol] = 
    if (position < function.sequence(constituent).length) {
      val symbol = function.sequence(constituent).symbol(position)
      return Some(symbol)
    }
    else
      return None

  override def toString() = {
    "[" + this.begin + ";" + this.category + "->" + this.function.name + "[" + this.domain.map(_.toString) + "];" + this.constituent + ";" + this.position + "]"
  }
}
