/*
 * CFGGrammars.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.parse.chart;

import se.chalmers.cs.gf.CFG.*;
import se.chalmers.cs.gf.CFG.Absyn.Grammar;
import se.chalmers.cs.gf.CFG.Absyn.Grammars;
import se.chalmers.cs.gf.CFG.Absyn.Flag;
import se.chalmers.cs.gf.CFG.Absyn.StartCat;
import se.chalmers.cs.gf.CFG.Absyn.TermS;
import se.chalmers.cs.gf.CFG.Absyn.CatS;
import se.chalmers.cs.gf.util.*;
import se.chalmers.cs.gf.parse.chart.Symbol;
import se.chalmers.cs.gf.parse.chart.Category;
import se.chalmers.cs.gf.GFException;

import se.chalmers.cs.gf.util.StringUtil;

import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 *  A collection of CF grammars.
 */
public class CFGGrammars {

        private Map<String,CFGrammar> grammars;

        public CFGGrammars(Grammars gs) {
                grammars = new HashMap<String,CFGrammar>();

                for (Grammar g : gs.listgrammar_) {
                        CFGrammar cf = makeCFGrammar(g);
//                        System.err.println(cf);
                        grammars.put(cf.getName(), cf);
                }
        }

        public Set<String> listGrammars() {
                return grammars.keySet();
        }

        public CFGrammar getGrammar(String name) {
                return grammars.get(name);
        }

        private CFGrammar makeCFGrammar(Grammar g) {
                String start = getStartCat(g);
                CFGrammar cf = new CFGrammar(g.ident_, new Category(start));

                for (se.chalmers.cs.gf.CFG.Absyn.Rule r : g.listrule_) {
                        String fun = funToString(r.fun_);
                        List<Profile> profile = new LinkedList<Profile>();

                        for (se.chalmers.cs.gf.CFG.Absyn.Profile p : r.profiles_.listprofile_)
                                profile.add(profileToProfile(p));

                        Category cat = new Category(categoryToString(r.category_));
                        List<Symbol> rhs = new LinkedList<Symbol>();
                        for (se.chalmers.cs.gf.CFG.Absyn.Symbol s : r.listsymbol_)
                                rhs.add(symbolToSymbol(s));
                        cf.addRule(cat, rhs, fun, profile);
                }

                return cf;
        }

        private String getStartCat(Grammar g) {
                String cat = "S{}.s";
                GetStartCat visitor = new GetStartCat();
                for (Flag f : g.listflag_)
                        cat = f.accept(visitor, cat);
                return cat;
        }

        private static class GetStartCat implements Flag.Visitor<String,String> {
                public String visit(StartCat p, String arg) {
                        return categoryToString(p.category_);
                }
        }


        public static CFGGrammars readCFGM(Reader in) throws IOException {
//                System.err.println("Parsing CFGM file");
                Grammars gs = CFGParser.parse(in);
                in.close();
//                System.err.println("Done parsing CFGM file");
                return new CFGGrammars(gs);
        }

        public static CFGGrammars readCFGM(String file) throws IOException {
                return readCFGM(FileUtil.openFile(file));
        }

        private Profile profileToProfile(se.chalmers.cs.gf.CFG.Absyn.Profile p) {
                return p.accept(new se.chalmers.cs.gf.CFG.Absyn.Profile.Visitor<Profile,Object>() {
                        public Profile visit(se.chalmers.cs.gf.CFG.Absyn.UnifyProfile p, Object arg) {
                                int[] pr = ArrayUtil.toIntArray(p.listinteger_);
                                return new UnifyProfile(pr);
                        }
                        public Profile visit(se.chalmers.cs.gf.CFG.Absyn.ConstProfile p, Object arg) {
                                return new ConstProfile(p.ident_);
                        }
                }, null);
        }

        private Symbol symbolToSymbol(se.chalmers.cs.gf.CFG.Absyn.Symbol in) {
                return in.accept(new SymbolToSymbol(), null);
        }

        private class SymbolToSymbol 
                implements se.chalmers.cs.gf.CFG.Absyn.Symbol.Visitor<Symbol,Object> {
                public Symbol visit(CatS p, Object arg) {
                        return new Category(categoryToString(p.category_));
                }
                public Symbol visit(TermS p, Object arg) {
                        return new Terminal(p.string_);
                }
        }

        private String funToString(se.chalmers.cs.gf.CFG.Absyn.Fun fun) {
                return PrettyPrinter.print(fun);
        }

        private static String categoryToString(se.chalmers.cs.gf.CFG.Absyn.Category cat) {
                return StringUtil.singleUnquote(cat.singlequotestring_);
        }

}
