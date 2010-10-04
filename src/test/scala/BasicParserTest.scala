package org.grammaticalframework.test

import org.grammaticalframework.Trees.PrettyPrinter
import org.grammaticalframework.{PGF, PGFBuilder, Parser, Linearizer, Generator}

import java.util.logging._;

object BasicParserTest {
  def main(args: Array[String]) {
    // Setting up logging
    //val logger = Logger.getLogger("org.grammaticalframework.parser")
    //logger.setLevel(Level.INFO)
    //val hdlr = new ConsoleHandler()
    //hdlr.setLevel(Level.FINEST)
    //logger.addHandler(hdlr)

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
    
    println()
    println("*** Testing prediction ***")
    grammar = new TestGrammar("pgf/phrasebook_de_en.pgf", "PhrasebookEng")
    grammar.predictAndPrint()
  }
}


class TestGrammar(pgfFile:String, langName:String) {
  val grammar:PGF = PGFBuilder.fromFile(pgfFile)
  val parser = new Parser(grammar, langName)
  val linearizer = new Linearizer(grammar, grammar.concrete(langName))
  val generator = new Generator(grammar)

  def parseAndPrint(txt:String):Unit = {
    println("Parsing string \"" + txt + "\" with grammar " +
            pgfFile + ":" + langName)
    val tokens = txt.split(" ")
    val trees = parser.parse(tokens).getTrees
    trees.foreach( t => {
      println(PrettyPrinter.print(t))
      println(linearizer.linearizeString(t))
      println()
    })
  }

  def generateAndPrint():Unit = {
    val tree = generator.gen(grammar.getAbstract().startcat());
    println(PrettyPrinter.print(tree))
    println(linearizer.linearizeString(tree))
  }
    
  def predictAndPrint():Unit = {
    val predictions = parser.parse().predict();
    predictions.foreach( p => println(p))

  }
    
}
