package reader;

import java.io.FileInputStream;

import org.junit.*;
import static org.junit.Assert.*;


public class Tests {
	NewReader rr = new NewReader();
	
	@Test public void testLang() 
	{try {
		rr.process(new FileInputStream("tesstsPGF/Lang.pgf"));
	      }
     catch(Exception e) {assert(1==0);}
	}

	@Test public void testFoods() 
	{try {
		rr.process(new FileInputStream("testsPGF/Foods.pgf"));
	      }
     catch(Exception e) {assert(1==0);}
	}

	@Test public void testNat() 
	{try {
		rr.process(new FileInputStream("testsPGF/Nat.pgf"));
	      }
     catch(Exception e) {assert(1==0);}
	}

	@Test public void testAnimals() 
	{try {
		rr.process(new FileInputStream("testsPGF/Animals.pgf"));
	      }
     catch(Exception e) {assert(1==0);}
	}
   
	@Test public void testLetter() 
	{try {
		rr.process(new FileInputStream("testsPGF/Letter.pgf"));
	      }
     catch(Exception e) {assert(1==0);}
	}

 






}
