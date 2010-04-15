/*
 * MCFGGrammars.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.parse.mcfg;

import se.chalmers.cs.gf.MCFG.*;
import se.chalmers.cs.gf.MCFG.Absyn.Cat;
import se.chalmers.cs.gf.MCFG.Absyn.ListCat;
import se.chalmers.cs.gf.MCFG.Absyn.Field;
import se.chalmers.cs.gf.MCFG.Absyn.ListField;
import se.chalmers.cs.gf.MCFG.Absyn.Sym;
import se.chalmers.cs.gf.MCFG.Absyn.Term;
import se.chalmers.cs.gf.MCFG.Absyn.Proj;
import se.chalmers.cs.gf.MCFG.Absyn.MCFGM;
import se.chalmers.cs.gf.MCFG.Absyn.MCFG;
import se.chalmers.cs.gf.MCFG.Absyn.Flag;
import se.chalmers.cs.gf.MCFG.Absyn.StartCat;

import se.chalmers.cs.gf.parse.Token;

import se.chalmers.cs.gf.util.*;
import se.chalmers.cs.gf.GFException;

import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 *  A collection of MCFG grammars. Can be read from a file in MCFGM
 *  format.
 */
public class MCFGGrammars {

        private Map<String,Grammar> grammars;

        public MCFGGrammars(MCFGM gs) {
                grammars = new HashMap<String,Grammar>();

                for (MCFG g : gs.listmcfg_) {
                        Grammar gr = makeGrammar(g);
                        grammars.put(gr.getName(), gr);
                }
        }

        public Set<String> listGrammars() {
                return grammars.keySet();
        }

        public Grammar getGrammar(String name) {
                return grammars.get(name);
        }

        private Grammar makeGrammar(MCFG g) {
                Category start = getStartCat(g);
                Grammar gr = new Grammar(g.ident_, start);

                for (se.chalmers.cs.gf.MCFG.Absyn.Rule r : g.listrule_) {
                        String fun = r.ident_;
                        // FIXME: get profile

                        Category cat = catToCategory(r.cat_);
			List<Category> childCats = catsToCategories(r.listcat_);
			Record<List<Rule.Symbol>> rhs = makeRHS(r.listfield_);

			Rule rule = new Rule(fun, cat, childCats, rhs);
                        gr.addRule(rule);
                }

                return gr;
        }

        private Category getStartCat(MCFG g) {
                Category cat = new Category("S{}.s"); // FIXME: is this right for MCFG?
                Flag.Visitor<Category,Category> visitor 
			= new Flag.Visitor<Category,Category>() {
			public Category visit(StartCat p, Category arg) {
				return catToCategory(p.cat_);
			}
		};

                for (Flag f : g.listflag_)
                        cat = f.accept(visitor, cat);
                return cat;
        }

	private Record<List<Rule.Symbol>> makeRHS (ListField fs) {
		Record<List<Rule.Symbol>> r = new Record<List<Rule.Symbol>>();
		for (Field f : fs) {
			List<Rule.Symbol> ss = new ArrayList<Rule.Symbol>();
			for (Sym s : f.listsym_) {
				ss.add(symToSymbol(s));
			}
			r.put(f.ident_, ss);
		}
		return r;
	}

	private static Rule.Symbol symToSymbol(Sym s) {
		Sym.Visitor<Rule.Symbol,Object> visitor = new Sym.Visitor<Rule.Symbol,Object>() {
			public Rule.Symbol visit(Term p, Object arg) {
				return new Rule.Terminal(p.string_);
			}
			public Rule.Symbol visit(Proj p, Object arg) {
				return new Rule.Projection(catToCategory(p.cat_),
							   p.integer_.intValue(), p.ident_);
			}
		};
		return s.accept(visitor, null);
	}

        private static Category catToCategory(Cat cat) {
                return new Category(StringUtil.singleUnquote(cat.singlequotestring_));
        }

        private static List<Category> catsToCategories(ListCat cats) {
		ArrayList<Category> rs = new ArrayList<Category>(cats.size());
		for (Cat c : cats)
			rs.add(catToCategory(c));
		return rs;
        }


        public static MCFGGrammars readMCFGM(Reader in) throws IOException {
                MCFGM gs = MCFGParser.parse(in);
                in.close();
                return new MCFGGrammars(gs);
        }

        public static MCFGGrammars readMCFGM(String file) throws IOException {
                return readMCFGM(FileUtil.openFile(file));
        }

}
