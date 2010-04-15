package se.chalmers.cs.pgf.parse.pmcfg;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ProductionTest extends TestCase {

	public void testHashCode()
	{
		EthernalCategory catN = new EthernalCategory("N", 3);
	    RHSItem[][] f = new RHSItem[][] 
	                    	          { new RHSItem[] { new Terminal("a"), new NonTerminal(0,0)},
	                    				new RHSItem[] { new Terminal("b"), new NonTerminal(1,0)},
	                    				new RHSItem[] { new Terminal("c"), new NonTerminal(2,0)}, 
	                                  };
		Production p1 = new Production( catN, 
        		new Category[] { catN },
        		f);
		Production p2 = new Production( catN, 
        		new Category[] { catN },
        		f);
		Assert.assertEquals(p1.hashCode(), p2.hashCode());
	}
	
	public void testEquals()
	{
		EthernalCategory catN = new EthernalCategory("N", 3);
	    RHSItem[][] f = new RHSItem[][] 
	          { new RHSItem[] { new Terminal("a"), new NonTerminal(0,0)},
				new RHSItem[] { new Terminal("b"), new NonTerminal(1,0)},
				new RHSItem[] { new Terminal("c"), new NonTerminal(2,0)}, 
              };
		Production p1 = new Production( catN, 
        		new Category[] { catN },
        		f);
		Production p2 = new Production( catN, 
        		new Category[] { catN },
        		f);
		Assert.assertEquals(p1, p2);
	}
	
	public static Test suite() {
	    return new TestSuite(ProductionTest.class);
	}
}
