package org.grammaticalframework.parser

import org.grammaticalframework.reader.PGF
import org.grammaticalframework.Trees.PrettyPrinter
import org.grammaticalframework.{Linearizer, Generator}

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

    // Testing with the food grammar
    var grammar = new TestGrammar("pgf/Foods.pgf", "FoodsEng")
    grammar.parseAndPrint("this fresh pizza is Italian")
    grammar.parseAndPrint("those boring fish are expensive")
    grammar = new TestGrammar("pgf/Foods.pgf", "FoodsSwe")
    grammar.parseAndPrint("den här läckra pizzan är färsk")
    grammar = new TestGrammar("pgf/Foods.pgf", "FoodsIta")
    grammar.parseAndPrint("questa pizza deliziosa è fresca")
    // Testing whith the phrasebook
    grammar = new TestGrammar("pgf/Phrasebook.pgf", "PhrasebookFre")
    grammar.parseAndPrint("le restaurant est ouvert le lundi")
    grammar = new TestGrammar("pgf/Phrasebook.pgf", "PhrasebookEng")
    grammar.parseAndPrint("how far is the university by train ?")

    println()
    println("*** Testing random generation ***")
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
    grammar.generateAndPrint()
  }
}


class TestGrammar(pgfFile:String, langName:String) {
  val grammar:PGF = PGF.readFromFile(pgfFile)
  val parser = new Parser(grammar.concrete(langName))
  val linearizer = new Linearizer(grammar, grammar.concrete(langName))
  val generator = new Generator(grammar)

  def parseAndPrint(txt:String):Unit = {
    println("Parsing string \"" + txt + "\" with grammar " +
            pgfFile + ":" + langName)
    val tokens = txt.split(" ")
    parser.parse(tokens)
    val trees = parser.getTrees
    trees.foreach( t => {
      println(PrettyPrinter.print(t))
      println(linearizer.renderLin(linearizer.linearize(t).elementAt(0)))
//      println(linearizer.renderLin(linearizer.linearize(t)))
      println()
    })
  }

  def generateAndPrint():Unit = {
    val tree = generator.gen(grammar.getAbstract().startcat());
    println(PrettyPrinter.print(tree))
    println(linearizer.renderLin(linearizer.linearize(tree).elementAt(0)))    
  }
    
}
