package org.grammaticalframework.reader;

import java.io.FileInputStream;

import org.junit.*;
import static org.junit.Assert.*;
import org.grammaticalframework.PGF;
import org.grammaticalframework.PGFBuilder;

public class TestReader {

    private void tryToReadPGF(String pgffile) {
        try {
            PGFBuilder.fromFile(pgffile);
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
