package org.grammaticalframework.reader;

/**
 * Concrete category are a maping from category names (abstract-categories)
 * to multiple, conjoint, concrete categories.
 * They are represented in the pgf binary by :
 *  - the name of the abstract category (ex: Adj)
 *  - the first concrete categoy (ex : C18)
 *  - the last corresponding concrete category (ex : C21)
 *  - a list of labels (names of fields in the pmcfg tuples)
 * Here we will keep only the indices.
 */
public class CncCat {
    private String name;
    private int firstFID;
    private int lastFID;
    //private String[] labels;

    public CncCat(String _name, int _firstFId, int _lastFId, String[] _labels) {
        name = _name;
        firstFID = _firstFId;
        lastFID = _lastFId;
        //labels = _labels;
    }

    public String toString() {
        return name + " [" + name + "::C" + firstFID + " ... C" + lastFID + "]";
    }

    public String getName() {
        return name;
    }
    public int getFirstId() {
        return firstFID;
    }
    public int getLastId() {
        return lastFID;
    }

    public int firstID() {
        return firstFID;
    }
    public int lastID() {
        return lastFID;
    }
}
