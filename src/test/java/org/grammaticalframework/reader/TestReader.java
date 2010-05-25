package org.grammaticalframework.reader;

import java.io.FileInputStream;

import org.junit.*;
import static org.junit.Assert.*;


public class TestReader {

    private void tryToReadPGF(String pgffile) {
        try {
            PGF.readFromFile(pgffile);
        }
        catch(Exception e) {
            assert(1==0);
        }
    }

    @Test public void testLang() {
        tryToReadPGF("tesstsPGF/Lang.pgf");
    }
    @Test public void testFoods() {
        tryToReadPGF("tesstsPGF/Foods.pgf");
    }
    @Test public void testNat() {
        tryToReadPGF("tesstsPGF/Nat.pgf");
    }
    @Test public void testAnimals() {
        tryToReadPGF("tesstsPGF/Animals.pgf");
    }
    @Test public void testLetter() {
        tryToReadPGF("tesstsPGF/Lang.pgf");
    }
}
