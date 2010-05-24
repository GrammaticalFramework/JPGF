package org.grammaticalframework.parser

import org.grammaticalframework.reader.{NewReader, PGF}
import org.grammaticalframework.Trees.PrettyPrinter

import java.util.logging._;
object BasicParserTest {

  def main(args: Array[String]) {
    println("Current dir is : " + System.getProperty("user.dir"));

    // Setting up logging
    val logger = Logger.getLogger("org.grammaticalframework.parser")
    logger.setLevel(Level.INFO)
    val hdlr = new ConsoleHandler()
    hdlr.setLevel(Level.FINEST)
    logger.addHandler(hdlr)

    // Try some parsing
    val grammar = new TestGrammar("pgf/AaBbCc.pgf", "AaBbCcCnc")
    grammar.parseAndPrint("a b c")
    grammar.parseAndPrint("a a b b c c")
    grammar.parseAndPrint("a a a a b b b b c c c c")
    grammar.parseAndPrint("a b c c")
    grammar.parseAndPrint("")
    // Testing with the food grammar
    val foodGrammar = new TestGrammar("pgf/Foods.pgf", "FoodsEng")
    foodGrammar.parseAndPrint("this fresh pizza is Italian")
    foodGrammar.parseAndPrint("those boring fish are expensive")
    val foodGrammarRon = new TestGrammar("pgf/Foods.pgf", "FoodsRon")
    foodGrammarRon.parseAndPrint("această pizza proaspătă este italiană")
    val foodGrammarSwe = new TestGrammar("pgf/FoodsSwe.pgf", "FoodsSwe")
    foodGrammarSwe.parseAndPrint("den här läckra pizzan är färsk")
    val foodGrammarIta = new TestGrammar("pgf/FoodsSwe.pgf", "FoodsIta")
    foodGrammarIta.parseAndPrint("questa pizza deliziosa è fresca")
  }
}

class TestGrammar(pgfFile:String, langName:String) {
  val reader = new NewReader()
  val grammar:PGF = reader.readFile(pgfFile)
  val parser = new Parser(grammar.concrete(langName))

  def parseAndPrint(txt:String):Unit = {
    println("Parsing string \"" + txt + "\" with grammar " + pgfFile + ":" + langName)
    val tokens = txt.split(" ")
    parser.parse(tokens)
    val trees = parser.getTrees
    trees.map(PrettyPrinter.print).foreach(println)
    //parser.printState
  }
}
