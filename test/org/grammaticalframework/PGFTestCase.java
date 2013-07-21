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
package org.grammaticalframework;

import junit.framework.TestCase;
import java.io.IOException;
import org.grammaticalframework.reader.*;
import org.grammaticalframework.Trees.Yylex;
import org.grammaticalframework.Trees.parser;
import org.grammaticalframework.Trees.Absyn.Tree;
import java.io.StringReader;

public abstract class PGFTestCase extends TestCase {
    
    public PGFTestCase(String name) {
	super(name);
    }
    
    /* **** Support function for oppening pdf files **** */
    
    protected PGF getPGF(String filename) throws IOException {
	String fullname =
	    this.getClass().getResource(filename).getFile();
	PGF pgf = PGFBuilder.fromFile(fullname);
	return pgf;
    }
    
    /* **** Support function for using abstract trees **** */
    
    protected Tree parseTree(String s) {
	Yylex l = new Yylex(new StringReader(s));
	parser p = new parser(l);
	try {
	    Tree parse_tree = p.pTree();
	    return parse_tree;
	} catch(Exception e) {
	    return null;
	}
    }
    
    
    /* **** Support function for manually building PGF objects **** */
    
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
