/* PGFTestCase.java
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
package org.grammaticalframework.pgf;

import junit.framework.TestCase;
import org.grammaticalframework.reader.*;
import java.util.*;

/**
 * this class can serve as the base class for test about the pgf data structures.
 * It implements usefull methods to create all kind of data
 **/
public abstract class PGFTestCase extends TestCase {
    public PGFTestCase(String name) {
	super(name);
    }
    
    public AbsFun mkFunction(String name, String[] argTypes, String returnType) {
	int arity = argTypes.length;
	Type type = mkType(argTypes, returnType);
	return new AbsFun(name, type, arity, new Eq[]{}, 0);
    }

    public AbsCat mkCategory(String name, String[] functions) {
	int n = functions.length;
	WeightedIdent[] funs = new WeightedIdent[n];
	for (int i = 0; i < n; i++)
	    funs[i] = new WeightedIdent(functions[i], 1.0 / n);
	return new AbsCat(name, new Hypo[]{}, funs);
    }

    /**
     * Creates a simple type
     **/
    public Type mkType(String type) {
	return new Type(new Hypo[]{}, type, new Expr[]{});
    }
	    
    /**
     * Creates a function type
     **/
    public Type mkType(String[] argTypes, String returnType) {
	int arity = argTypes.length;
	Hypo[] hypos = new Hypo[arity];
	for (int i = 0 ; i < arity ; i++)
	    hypos[i] = new Hypo(true, "_", mkType(argTypes[i]));
	return new Type(hypos, returnType, new Expr[] {});
    }
}
