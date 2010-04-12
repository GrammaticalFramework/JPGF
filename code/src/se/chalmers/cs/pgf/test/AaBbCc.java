package se.chalmers.cs.pgf.test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import se.chalmers.cs.pgf.parse.pmcfg.*;
import se.chalmers.cs.pgf.util.PGFLogger;


public class AaBbCc {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		// Setting up logger
		Handler fh = new FileHandler("%t/pgf.log");
		fh.setFormatter(new SimpleFormatter());
		Logger.getLogger("").addHandler(fh);
	    Logger.getLogger("se.chalmers.cs.pgf").setLevel(Level.WARNING);
		PGFLogger log = new PGFLogger("se.chalmers.cs.pgf.test");

		// Define categories
		log.fine("Defining categories");
	    Category catN = new EthernalCategory("N", 3);
	    Category catS = new EthernalCategory("S", 1);
	      
		
	    Production[] productions = new Production[] {
	    	new Production( catS, 
	    			new Category[] { catN },
	    			new RHSItem[][] 
	    			              { new RHSItem[] { new NonTerminal(0,0), new NonTerminal(0,1), new NonTerminal(0,2) } }),
	        new Production( catS, 
	        		new Category[] {},
	        		new RHSItem[][] 
	        		              { new RHSItem[] { new Terminal("a"), new Terminal("a"), new Terminal("b"), new Terminal("b"), new Terminal("c"), new Terminal("c") } }),
	        new Production( catN, 
	        		new Category[] { catN },
	        		new RHSItem[][] 
	        		              { new RHSItem[] { new Terminal("a"), new NonTerminal(0,0)},
	        						new RHSItem[] { new Terminal("b"), new NonTerminal(0,1)},
	        						new RHSItem[] { new Terminal("c"), new NonTerminal(0,2)}, 
	        		              }),
	        new Production( catN, 
	        		new Category[] {},
	        		new RHSItem[][] 
	        		              { new RHSItem[] {},
	        						new RHSItem[] {},
	        						new RHSItem[] {}, 
	        		              }) };   
	    
	    PMCFGGrammar grammar = new PMCFGGrammar("AaBbCc",
	          catS, // Start category,
	          productions // productions : give everything else (non terminal set, terminal set, category set, dimensions...)
	          );
	    
	    log.fine("Grammar " + grammar.getName() + " created.");
	    log.fine("Grammar has : ");
	    log.fine(grammar.getRules().length + " Productions");
	    
	    PMCFGParser parser = new PMCFGParser(grammar);
	    Chart c = parser.parse(new String[] {"a", "a", "b", "b", "c", "c"});
	    System.out.println("Parse finished, solutions : " + c.getPassive(0, 6).size());
	    System.out.println("Terminated");
	    
		
	}

}
