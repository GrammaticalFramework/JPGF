package se.chalmers.cs.pgf.parse.pmcfg;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ActiveEdgeTest extends TestCase {
	ActiveEdge e1, e2, e3;
	
	public void setUp()
	{
		EthernalCategory catN = new EthernalCategory("N", 3);
	    RHSItem[][] f = new RHSItem[][] 
	                    	          { new RHSItem[] { new Terminal("a"), new NonTerminal(0,0)},
	                    				new RHSItem[] { new Terminal("b"), new NonTerminal(1,0)},
	                    				new RHSItem[] { new Terminal("c"), new NonTerminal(2,0)}, 
	                                  };
		Production p1 = new Production( catN, new Category[] { catN }, f);
		Production p2 = new Production( catN, new Category[] { catN }, f);
		e1 = new ActiveEdge(p1, 0, 1);
		e2 = new ActiveEdge(p2, 0, 1);		
		e3 = new ActiveEdge(p1, 0, 0);		
	}
	
	public void test_hashCode()
	{
		Assert.assertEquals(e1.hashCode(), e2.hashCode());
	}
	
	public void test_equals()
	{
		Assert.assertEquals(e1, e2);
	}
	
	public void test_needs()
	{
		Assert.assertTrue(e3.needs("a"));
		EthernalCategory catN = new EthernalCategory("N", 3);
        Production p4 = new Production( catN, 
        		new Category[] {},
        		new RHSItem[][] 
        		              { new RHSItem[] {},
        						new RHSItem[] {},
        						new RHSItem[] {}, 
        		              });
		ActiveEdge e4 = new ActiveEdge(p4, 0, 0);		
		Assert.assertTrue(!e4.needs("a"));
	}

}
