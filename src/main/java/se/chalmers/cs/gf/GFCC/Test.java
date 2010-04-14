package se.chalmers.cs.gf.GFCC;
import java_cup.runtime.*;
import se.chalmers.cs.gf.GFCC.*;
import se.chalmers.cs.gf.GFCC.Absyn.*;
import java.io.*;

public class Test
{
  public static void main(String args[]) throws Exception
  {
    Yylex l = null;
    parser p;
    try
    {
      if (args.length == 0) l = new Yylex(System.in);
      else l = new Yylex(new FileReader(args[0]));
    }
    catch(FileNotFoundException e)
    {
     System.err.println("Error: File not found: " + args[0]);
     System.exit(1);
    }
    p = new parser(l);
    /* The default parser is the first-defined entry point. */
    /* You may want to change this. Other options are: */
    /* pHeader, pAbstract, pConcrete, pAbsDef, pCncDef, pType, pExp, pAtom, pTerm, pTokn, pVariant, pListConcrete, pListAbsDef, pListCncDef, pListCId, pListTerm, pListExp, pListString, pListVariant */
    try
    {
      se.chalmers.cs.gf.GFCC.Absyn.Grammar parse_tree = p.pGrammar();
      System.out.println();
      System.out.println("Parse Succesful!");
      System.out.println();
      System.out.println("[Abstract Syntax]");
      System.out.println();
      System.out.println(PrettyPrinter.show(parse_tree));
      System.out.println();
      System.out.println("[Linearized Tree]");
      System.out.println();
      System.out.println(PrettyPrinter.print(parse_tree));
    }
    catch(Throwable e)
    {
      System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
      System.err.println("     " + e.getMessage());
      System.exit(1);
    }
  }
}
