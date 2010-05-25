package org.grammaticalframework.parser

import org.grammaticalframework.reader.PGF
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

    // Testing with the food grammar
    val foodGrammar = new TestGrammar("pgf/Foods.pgf", "FoodsEng")
    foodGrammar.parseAndPrint("this fresh pizza is Italian")
    foodGrammar.parseAndPrint("those boring fish are expensive")
    val foodGrammarSwe = new TestGrammar("pgf/Foods.pgf", "FoodsSwe")
    foodGrammarSwe.parseAndPrint("den här läckra pizzan är färsk")
    val foodGrammarIta = new TestGrammar("pgf/Foods.pgf", "FoodsIta")
    foodGrammarIta.parseAndPrint("questa pizza deliziosa è fresca")
    // testing whith the phrasebook
    val engPhrasebook = new TestGrammar("pgf/Phrasebook.pgf", "PhrasebookFre")
    engPhrasebook.parseAndPrint("le restaurant est ouvert le lundi .")
  }
}

class TestGrammar(pgfFile:String, langName:String) {
  val grammar:PGF = PGF.readFromFile(pgfFile)
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
