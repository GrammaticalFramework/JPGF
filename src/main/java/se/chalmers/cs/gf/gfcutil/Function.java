/*
 * Function.java
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
package se.chalmers.cs.gf.gfcutil;

import se.chalmers.cs.gf.GFC.Absyn.*;
import se.chalmers.cs.gf.GFException;

import java.util.*;
import java.io.Serializable;

/**
 *  Represents a GFC function.
 */
public class Function implements Definition, Serializable {
        
        private String name;

        private List<String> argCats;

        private String resultCat;

        public Function(String name, Exp exp) {
                this.name = name;
                List<String> t = expToType(exp);
                if (t.size() == 0) {
                        System.err.println(name + " : " 
                                           + se.chalmers.cs.gf.GFC.PrettyPrinter.show(exp));
                }
                this.resultCat = t.remove(t.size()-1);
                this.argCats = t;
        }

        /**
         *  Get the name of this function.
         */
        public String getName() {
                return name;
        }

        /**
         *  Get the argument types of this function.
         */
        public List<String> getArgCats() {
                return argCats;
        }

        /**
         *  Get one of the argument types of this function.
         */
        public String getArgCat(int i) {
                return argCats.get(i);
        }

        /**
         *  Get the result type of this function
         */
        public String getResultCat() {
                return resultCat;
        }

        /**
         *  Get the number of arguments that this function takes.
         */
        public int getArity() {
                return argCats.size();
        }

        public boolean equals(Object o) {
                return o instanceof Function && equals((Function)o);
        }

        public boolean equals(Function f) {
                return name.equals(f.name) 
                        && argCats.equals(f.argCats) 
                        && resultCat.equals(f.resultCat);
        }

        public int hashCode() {
                return name.hashCode() + argCats.hashCode() + resultCat.hashCode();
        }

        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("fun ");
                sb.append(name);
                sb.append(" : ");
                for (String c : argCats) 
                        sb.append(c).append(" -> ");
                sb.append(resultCat);
                return sb.toString();
        }

        /**
         * FIXME: result should be a list of identifiers, not a list of strings
         */
        private static List<String> expToType(Exp exp) {
                return exp.accept(new ExpToType(), new LinkedList<String>());
        }

        private static class ExpToType implements Exp.Visitor<List<String>,List<String>> {
                private AtomToString atomToString = new AtomToString();

                public List<String> visit(EApp p, List<String> arg) { 
                        // FIXME: is it ok to just take the first one and use it?
                        return p.exp_1.accept(this, arg);
                }
                public List<String> visit(EProd p, List<String> arg) {
                        p.exp_1.accept(this, arg);
                        return p.exp_2.accept(this, arg);
                }
                public List<String> visit(EAbs p, List<String> arg) { return arg; }
                public List<String> visit(EEq p, List<String> arg) { return arg; } 
                public List<String> visit(EAtom p, List<String> arg) { 
                        String s = p.atom_.accept(atomToString, null);
                        arg.add(s);
                        return arg;
                }
                public List<String> visit(EData p, List<String> arg) { return arg; }
        }

        private static class AtomToString implements Atom.Visitor<String,Object> {
                public String visit(AC p, Object arg) { 
                        CIQ ciq = (CIQ)p.cident_;
                        return ciq.ident_1 + "." + ciq.ident_2;
                }
                public String visit(AD p, Object arg) {
                        CIQ ciq = (CIQ)p.cident_;
                        return "<" + ciq.ident_1 + "." + ciq.ident_2 + ">";
                }
                public String visit(AV p, Object arg) {
                        return "$" + p.ident_;
                }
                public String visit(AM p, Object arg) {
                        return "?" + String.valueOf(p.integer_);
                }
                public String visit(AS p, Object arg) {
                        return '"' + p.string_ + '"'; //FIXME: escape
                }
                public String visit(AI p, Object arg) {
                        return String.valueOf(p.integer_);
                }
                public String visit(AT p, Object arg) {
                        return "Type";
                }
        }

}
