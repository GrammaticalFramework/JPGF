/* CncCat.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
