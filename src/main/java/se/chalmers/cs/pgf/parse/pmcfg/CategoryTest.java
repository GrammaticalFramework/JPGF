package se.chalmers.cs.pgf.parse.pmcfg;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CategoryTest extends TestCase {
	public void testHashCode()
	{
		EthernalCategory cat1 = new EthernalCategory("N", 3);
	    EthernalCategory cat2 = new EthernalCategory("N", 3);
		Assert.assertEquals(cat1.hashCode(), cat2.hashCode());
		//Assert.assertTrue(!cat1.equals(cat3));
		//Assert.assertTrue(!cat1.equals(cat4));
	}
	
	public void testEquals()
	{
		EthernalCategory cat1 = new EthernalCategory("N", 3);
		EthernalCategory cat2 = new EthernalCategory("N", 3);
		Assert.assertEquals(cat1, cat2);		//Assert.assertTrue(!cat1.equals(cat4));
	}
	
	public void test_different()
	{
		EthernalCategory cat1 = new EthernalCategory("N", 3);
	    EthernalCategory cat3 = new EthernalCategory("M", 3);
	    EthernalCategory cat4 = new EthernalCategory("N", 2);
		Assert.assertTrue(!cat1.equals(cat3));
		Assert.assertTrue(!cat1.equals(cat4));
		
	}
	
	public static Test suite() {
	    return new TestSuite(CategoryTest.class);
	}

}
