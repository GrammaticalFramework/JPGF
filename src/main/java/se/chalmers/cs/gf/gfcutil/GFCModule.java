/*
 * GFCModule.java
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

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.GFC.*;
import se.chalmers.cs.gf.GFC.Absyn.*;
import java.util.*;
import java.io.Serializable;

/**
 *  Wraps a GFC module (concrete, abstract or resource) 
 *  and provides methods for looking up its contents.
 */
public class GFCModule implements Serializable {

        /** Module name. */
        private String name;

        /** Ttype of this module. */
        private ModuleType type;

        /** 
         *  The abstract module that this module is for. null
         *  if this module is not concrete.
         */
        private String abstractModule;

        /** Maps function names to linearization rules. */
        private Map<String,LinRule> rules;

        /** Maps function names to functions. */
        private Map<String,Function> funs;

        /** Maps oper names to opers. */
        private Map<String,Oper> opers;


        /** 
         *  Maps all inherited names to the modules that they are 
         *  inherited from.
         */
        private Map<String,Inherited> inherited;

        /** Maps module names to modules. */
        private Map<String,GFCModule> allModules;


        /**
         *  @param mod A module parse tree
         *  @param allModules A reference to a map of module names to 
         *    modules. The map need not be populated when passed to 
         *    the contructor, but it must be before find is called.
         */
        public GFCModule(Mod mod, Map<String,GFCModule> allModules) {
                this.name = getModName(mod);
                this.type = ModuleType.getModuleType(mod.modtype_);
                this.abstractModule = getAbstractModule(mod);

                this.rules = new TreeMap<String,LinRule>();
                getDefs(mod, new GetLinRule(), rules);

                this.funs = new TreeMap<String,Function>();
                getDefs(mod, new GetFunction(), funs);

                this.opers = new TreeMap<String,Oper>();
                getDefs(mod, new GetOper(), opers);

                this.inherited = new TreeMap<String,Inherited>();
                getDefs(mod, new GetInherited(), inherited);

                this.allModules = allModules;
        }

        /**
         *  Get the name of this module.
         */
        public String getName() {
                return name;
        }

        /**
         *  Checks if this module is concrete.
         */
        public ModuleType getType() {
                return type;
        }

        /**
         *  Get the name of the abstract module that this module is for.
         *  If this is not a concrete module, null is returned.
         */
        public String getAbstract() {
                return abstractModule;
        }

        /**
         *  Find a function by name. 
         */
        public Function findFunction(String f) {
                Function x = findFunction_(f);
                if (x == null)
                        throw new GFException("No function called " 
                                              + f + " in module " + name);
                return x;
        }

        private Function findFunction_(String f) {
                Function fun = funs.get(f);
                if (fun != null) 
                        return fun;
                Inherited in = inherited.get(f);
                if (in == null) 
                        return null;
                return findInheritedFunction(in);
        }

        private Function findInheritedFunction(Inherited in) {
                return getModule(in.getModule()).findFunction_(in.getName());
        }

        /**
         *  Find an oper by name. 
         */
	public Oper findOper(CIdent f) {
		CIQ i = (CIQ)f;
		return getModule(i.ident_1).getOper(i.ident_2);
	}

	private Oper getOper(String o) {
		Oper x = opers.get(o);
		if (x == null)
                        throw new GFException("No oper called " 
                                              + o + " in module " + name);
		return x;
	}

        /**
         *  Get all functions in this module, including inherited ones.
         */
        public Set<Function> listFunctions() {
                Set<Function> fs = new HashSet<Function>();
                fs.addAll(funs.values());

                for (Inherited in : inherited.values()) {
                        Function f = findInheritedFunction(in);
                        // f maybe null as in could also refer to a category
                        if (f != null)
                                fs.add(f);
                }

                return fs;
        }

        /**
         *  Find a linearization rule by function name.
         *  @return The linearization rule, or null if there
         *  is no rule for the given function.
         */
        public LinRule findLinRule(String f) {
                LinRule rule = rules.get(f);
                if (rule != null) 
                        return rule;
                Inherited in = inherited.get(f);
                if (in == null)
                        return null;
                return getModule(in.getModule()).findLinRule(f);
        }

        /**
         *  Get all categories used as the result of functions in this module, 
         *  including inherited ones.
         */
        public Set<String> listCategories() {
                Set<String> cs = new HashSet<String>();     
                for (Function f : listFunctions())
                        cs.add(f.getResultCat());
                return cs;
        }

	private GFCModule getModule(String n) {
		GFCModule m = allModules.get(n);
		if (m == null)
			throw new GFException("Module " + name + " not found.");
		return m;
	}

        public boolean equals(Object o) {
                return o instanceof GFCModule && equals((GFCModule)o);
        }

        public boolean equals(GFCModule mod) {
                return getName().equals(mod.getName());
        }

        public int hashCode() {
                return getName().hashCode();
        }

        /**
         *  Gets the abstract module that a concrete module is for.
         */
        private static String getAbstractModule(Mod mod) {
                List<String> as = mod.modtype_.accept(
                        new GetAbstractModule(), 
                        new LinkedList<String>());
                if (as.size() == 1)
                        return as.get(0);
                assert as.size() == 0;
                return null;
        }

        /**
         *  Gets the abstract module that a concrete module is for.
         */
        private static class GetAbstractModule 
                implements ModType.Visitor<List<String>,List<String>> { 
                public List<String> visit(MTAbs p, List<String> arg) { 
                        return arg; 
                }
                public List<String> visit(MTCnc p, List<String> arg) { 
                        arg.add(p.ident_2);
                        return arg; 
                }
                public List<String> visit(MTRes p, List<String> arg) { 
                        return arg; 
                }
                public List<String> visit(MTTrans p, List<String> arg) { 
                        return arg; 
                }
        }

        /**
         *  Gets certain definitions from a module.
         */
        private static <A extends Definition> Map<String,A> getDefs(Mod mod, GetDef<A> v, Map<String,A> map) {
                return mod.accept(new GetDefs<A>(v), map);
        }

        /**
         *  Get the name of a module.
         */
        private static String getModName(Mod mod) {
                return mod.modtype_.accept(new GetModName(), null);
        }

        /**
         *  Uses a GetDef on all defintions in a module.
         */
        private static class GetDefs<A extends Definition> 
                implements Module.Visitor<Map<String,A>,Map<String,A>> {

                private GetDef<A> v;
                public GetDefs(GetDef<A> v) { this.v = v; }
                public Map<String,A> visit(Mod mod, Map<String,A> arg) {
                        for (Def d : mod.listdef_) {
                                A r = d.accept(v, arg);
                                if (r != null) {
                                        String n = r.getName();
                                        if (arg.containsKey(n))
                                                throw new GFException(
                                                        n + " already exists in module " 
                                                        + getModName(mod));
                                        arg.put(n, r);
                                }
                        }
                        return arg;
                }
        }

        /**
         *  Visits a GFC ModType and gets the module name.
         */
        private static class GetModName implements ModType.Visitor<String,Object> {
                public String visit(MTAbs p, Object arg) {
                        return p.ident_;
                }
                public String visit(MTCnc p, Object arg) {
                        return p.ident_1;
                }
                public String visit(MTRes p, Object arg) {
                        return p.ident_;
                }
                public String visit(MTTrans p, Object arg) {
                        return p.ident_1;
                }
        }

        /**
         *  Base class for GFC definition visitors. Returns null for all definitions.
         */
        private static abstract class GetDef<A> implements Def.Visitor<A,Object> {
                public A visit(CncDFun cncdfun, Object arg) { return null; }
                public A visit(AbsDCat p, Object arg) { return null; }
                public A visit(AbsDFun p, Object arg) { return null; }
                public A visit(AbsDTrans p, Object arg) { return null; }
                public A visit(ResDPar p, Object arg) { return null; }
                public A visit(ResDOper p, Object arg) { return null; }
                public A visit(CncDCat p, Object arg) { return null; }
                public A visit(AnyDInd p, Object arg) { return null; }
        }

        /**
         *  Visits a GFC definition. If it is a linearization rule definition, 
         *  a LinRule is returned, otherwise null is returned.
         */
        private static class GetLinRule extends GetDef<LinRule> {
                public LinRule visit(CncDFun cncdfun, Object arg) {
                        return new LinRule(cncdfun.ident_, 
                                           cncdfun.listargvar_, 
                                           cncdfun.term_1);
                }
        }

        /**
         *  Visits a GFC definition. If it is a function definition, 
         *  a Function is returned, otherwise null is returned.
         */
        private static class GetFunction extends GetDef<Function> {
                 public Function visit(AbsDFun p, Object arg) {
                         // FIXME: do we ever care about p.exp2, the rhs?

                         return new Function(p.ident_, p.exp_1);
                 }
        }

        /**
         *  Visits a GFC definition. If it is an oper definition, 
         *  an Oper is returned, otherwise null is returned.
         */
        private static class GetOper extends GetDef<Oper> {
                 public Oper visit(ResDOper p, Object arg) {
                         return new Oper(p.ident_, p.term_);
                 }
        }


        /**
         *  Visits a GFC definition. If it is a inheritance definition, 
         *  an Inherited is returned, otherwise null is returned.
         */
        private static class GetInherited extends GetDef<Inherited> {
                 public Inherited visit(AnyDInd p, Object arg) {
                         return new Inherited(p.ident_1, p.ident_2);
                 }
        }

}
