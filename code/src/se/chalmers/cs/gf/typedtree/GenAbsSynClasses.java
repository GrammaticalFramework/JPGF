/*
 * GenAbsSynClasses.java
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
package se.chalmers.cs.gf.typedtree;

import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.gfcutil.*;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.util.Pair.pair;

import java.util.*;
import java.io.*;

/**
 *  Generates classes for typed abstract syntax trees and conversions
 *  from and to untyped trees.
 *
 *  FIXME: large and hard to read, needs refactoring
 */
public class GenAbsSynClasses {

        private static final int INDENT_SIZE = 8;

        /**
         *  Get a map of categories to functions producing that category.
         */
        private static Map<String,Set<Function>> moduleAbsProds(GFCModule mod) {
                Set<String> cs = mod.listCategories();
                Set<Function> fs = mod.listFunctions();

                Map<String,Set<Function>> syn 
                        = new HashMap<String,Set<Function>>();
                for (String c : cs)
                        syn.put(c, new HashSet<Function>());

                for (Function f : fs)
                        syn.get(f.getResultCat()).add(f);

                return syn;
        }

        private static class JavaFile {
                public final String packageName;
                public final String className;
                public final CharSequence contents;
                public JavaFile(String packageName, String className, CharSequence contents) {
                        this.packageName = packageName;
                        this.className = className;
                        this.contents = contents;
                }
        }


        /*
         *  Generate typed tree classes.
         */

        private static JavaFile makeCatFile(String packageName, 
                                            String cat, 
                                            Collection<Function> funs) {

                String className = makeClassName(cat);
                List<String> children = new LinkedList<String>();
                for (Function f : funs)
                        children.add(makeClassName(f.getName()));

                StringBuilder p = new StringBuilder();

                pr(p, 0, "package " + packageName + ";");
                blank(p);
                pr(p, 0, "/**");
                pr(p, 0, " *  Base class for trees of the category " + className + ".");
                pr(p, 0, " */");
                pr(p, 0, "public abstract class " + className + " {");
                blank(p);
                pr(p, 1, "public String toString() { return TypedToTree.getInstance().toTree(this).toString(); }");
                pr(p, 1, "public abstract <R,A> R accept(Visitor<R,A> v, A arg);");
                blank(p);
                pr(p, 1, "/**");
                pr(p, 1, " *  Visits trees of the category " + className + ".");
                pr(p, 1, " */");
                pr(p, 1, "public interface Visitor <R,A> {");
                for (String c : children)
                        pr(p, 2, "public R visit(" + c + " p, A arg);");
                pr(p, 1, "}");
                pr(p,0,"}");

                return new JavaFile(packageName, className, p);
        }

        /**
         *  Write java file for a class corresponging to a function.
         */
        private static JavaFile makeFunFile(String packageName, 
                                            Function fun) {

                String className = makeClassName(fun.getName());
                String parent = makeClassName(fun.getResultCat()); // FIXME: what if className == parent?
               
                List<String> fieldTypes = makeTypeNames(fun.getArgCats());

                StringBuilder p = new StringBuilder();

                pr(p, 0, "package " + packageName + ";");
                blank(p);
                pr(p, 0, "/**");
                pr(p, 0, " *  Represents the function " + className + " in category " + parent + ".");
                pr(p, 0, " */");
                String h = "public class " + className;
                if (!parent.equals(className))
                        h += " extends " + parent;
                h += " {";
                pr(p, 0, h);

                List<Pair<String,String>> fs = makeFieldNames(fieldTypes);
                for (Pair f : fs)
                        pr(p, 1, "public " + f.fst + " " + f.snd + ";");

                prConstructor(p, 1, className, fs);
                blank(p);
                pr(p, 1, "public <R,A> R accept(Visitor<R,A> v, A arg) { return v.visit(this, arg); }");
                blank(p);
                pr(p,0,"}");

                return new JavaFile(packageName, className, p);
        }

        private static void prConstructor(StringBuilder p, int indent, String className, 
                                          List<Pair<String,String>> fields) {
                StringBuilder sb = new StringBuilder(); 
                sb.append("public ").append(className).append("(");

                Iterator<Pair<String,String>> ps = fields.iterator();
                while (ps.hasNext()) {
                        Pair<String,String> f = ps.next();
                        sb.append(f.fst).append(" ").append(f.snd);
                        if (ps.hasNext())
                                sb.append(", ");
                }
                sb.append(") {");
                pr(p, indent, sb.toString());
                for (Pair f : fields)
                        pr(p, indent+1, "this." + f.snd + " = " + f.snd + ";");
                pr(p, indent, "}");
        }

        /**
         *  Take a list of category name and produce a list of type names.
         */
        private static List<String> makeTypeNames(List<String> cats) {
                List<String> fieldTypes = new LinkedList<String>();
                for (String cat : cats)
                        fieldTypes.add(makeClassName(cat));
                return fieldTypes;
        }

        /**
         *  Take a list of type names and produce a list of pairs (type, variable name).
         */
        private static List<Pair<String,String>> makeFieldNames(List<String> types) {
                Map<String,Integer> typeCount = countValues(types);

                Map<String,Integer> alias = new HashMap<String,Integer>();
                
                List<Pair<String,String>> names = new LinkedList<Pair<String,String>>();
                for (String t : types) {
                        String name = t.toLowerCase() + "_";
                        if (typeCount.get(t) > 0)
                                name += incValue(alias, t);
                        names.add(pair(t, name));
                }

                return names;
        }


        /*
         * Generate conversion from untyped trees.
         */

        private static JavaFile makeTreeToTyped(String packageName,
                                                Map<String,Set<Function>> prods) {

                StringBuilder p = new StringBuilder();

                pr(p, 0, "package " + packageName + ";");
                pr(p, 0, "import se.chalmers.cs.gf.GFException;");
                pr(p, 0, "import se.chalmers.cs.gf.abssyn.*;");
                pr(p, 0, "import se.chalmers.cs.gf.typedtree.*;");
                blank(p);
                pr(p, 0, "/**");
                pr(p, 0, " *  Converts untyped trees to typed trees.");
                pr(p, 0, " */");
                pr(p, 0, "public class TreeToTyped extends FromTree {");
                blank(p);
                pr(p, 1, "private static final TreeToTyped instance = new TreeToTyped();");
                blank(p);
                pr(p, 1, "/**");
                pr(p, 1, " *  Returns a shared instance of this class.");
                pr(p, 1, " */");
                pr(p, 1, "public static TreeToTyped getInstance() { return instance; }");
                blank(p);


                pr(p, 1, "public <T> T fromTree(Tree t, Class<T> cls) {");
                for (String cat : prods.keySet()) {
                        String catClassName = makeClassName(cat);
                        pr(p, 2, "if (cls == " + catClassName + ".class)");
                        pr(p, 3, "return cls.cast(to" + catClassName + "(t));");
                }
                pr(p, 2, "throw new GFException(\"Unknown category class \" + cls);");
                pr(p, 1, "}");
                blank(p);

                for (Map.Entry<String,Set<Function>> e : prods.entrySet()) {
                        String cat = e.getKey();
                        String catClassName = makeClassName(cat);
                        pr(p, 1, "public " + catClassName + " to" + catClassName + "(Tree t) {");
                        pr(p, 2, "if (!(t instanceof Fun))");
                        pr(p, 3, "throw new GFException(t + \" is not a fun\");");
                        pr(p, 2, "Fun f = (Fun)t;");
                        pr(p, 2, "String l = f.getLabel();");

                        for (Function f : e.getValue()) {
                                String className = makeClassName(f.getName());
                                // FIXME: use trie or something instead of repeated ifs
                                pr(p, 2, "if (l.equalsIgnoreCase(\"" + f.getName() + "\")) {");
                                String args = "";
                                Iterator<String> cats = f.getArgCats().iterator();
                                int i = 0;
                                while (cats.hasNext()) {
                                        String catClass = makeClassName(cats.next());
                                        args += "to" + catClass + "(f.getChild(" + i + "))";
                                        if (cats.hasNext())
                                                args += ",";
                                        i++;
                                }
                                pr(p, 3, "return new " + className + "(" + args + ");");
                                pr(p, 2, "}");
                        }
                        pr(p, 2, "throw new GFException(\"Cannot make a " + catClassName 
                                 + " from a \" + l);");
                        pr(p, 1, "}");
                        blank(p);
                }

                pr(p, 0, "}");

                return new JavaFile(packageName, "TreeToTyped", p);
        }


        /*
         * Generate conversion to untyped trees.
         */

        private static JavaFile makeTypedToTree(String packageName,
                                                Map<String,Set<Function>> prods) {

                StringBuilder p = new StringBuilder();
                
                pr(p, 0, "package " + packageName + ";");
                blank(p);
                pr(p, 0, "import se.chalmers.cs.gf.abssyn.*;");
                pr(p, 0, "import se.chalmers.cs.gf.typedtree.*;");
                blank(p);
                pr(p, 0, "/**");
                pr(p, 0, " *  Converts typed trees to untyped trees.");
                pr(p, 0, " */");
                pr(p, 0, "public class TypedToTree extends ToTree {");
                blank(p);
                pr(p, 1, "private static final TypedToTree instance = new TypedToTree();");
                blank(p);
                pr(p, 1, "/**");
                pr(p, 1, " *  Returns a shared instance of this class.");
                pr(p, 1, " */");
                pr(p, 1, "public static TypedToTree getInstance() { return instance; }");
                blank(p);

                for (Map.Entry<String,Set<Function>> e : prods.entrySet()) {
                        prTypedToTreeMethod(p, e.getKey(), e.getValue());
                        prTypedToTreeVisitor(p, e.getKey(), e.getValue());
                        blank(p);
                }

                pr(p, 0, "}");

                return new JavaFile(packageName, "TypedToTree", p);
        }

        private static void prTypedToTreeMethod(StringBuilder p, String cat, 
                                                Collection<Function> funs) {
                String catClass = makeClassName(cat);
                String toTreeClass = catClass + "ToTree";
                String toTreeField = toTreeClass.toLowerCase();

                pr(p, 1, "private " + toTreeClass + " " + toTreeField 
                         + " = new " + toTreeClass + "();");
                
                pr(p, 1, "public Tree toTree(" + catClass + " t) {");
                pr(p, 2, "return t.accept(" + toTreeField + ", null);");
                pr(p, 1, "}");
        }

        private static void prTypedToTreeVisitor(StringBuilder p, String cat, 
                                                 Collection<Function> funs) {
                String catClass = makeClassName(cat);

                pr(p, 1, "private class " + catClass + "ToTree implements " 
                   + catClass + ".Visitor<Tree,Object> {");

                for (Function f : funs) {
                        String c = makeClassName(f.getName());
                        pr(p, 2, "public Tree visit(" + c + " t, Object arg) {");
                        if (f.getArity() == 0) {
                                pr(p, 3, "return new Fun(\"" + f.getName() + "\");");
                        } else {
                                pr(p, 3, "Tree[] ts = new Tree[] {");
                                List<String> ts = makeTypeNames(f.getArgCats());
                                Iterator<Pair<String,String>> fields = 
                                        makeFieldNames(ts).iterator();
                                while (fields.hasNext()) {
                                        Pair<String,String> x = fields.next();
                                        String v = x.fst + "ToTree";
                                        pr(p, 4, "toTree(t." + x.snd + "),");
                                }
                                pr(p, 3, "};");
                                pr(p, 3, "return new Fun(\"" + f.getName() + "\", ts);");
                        }
                        pr(p, 2, "}");
                }
                
                pr(p, 1, "}");
        }

        /*
         *  Generate top-level class
         */

        private static JavaFile makeTopClass(String packageName,
                                             Map<String,Set<Function>> prods) {

                String simplePackageName = packageName.substring(packageName.lastIndexOf('.') + 1);
                String className = firstToUpper(simplePackageName) + "Main";

                StringBuilder p = new StringBuilder();
                                
                pr(p, 0, "package " + packageName + ";");
                blank(p);
                pr(p, 0, "import se.chalmers.cs.gf.abssyn.*;");
                pr(p, 0, "import se.chalmers.cs.gf.translate.Translator;");
                pr(p, 0, "import se.chalmers.cs.gf.translate.TranslatorFactory;");
                pr(p, 0, "import se.chalmers.cs.gf.util.Pair;");
                pr(p, 0, "import static se.chalmers.cs.gf.util.Pair.pair;");
                blank(p);
                pr(p, 0, "import java.util.*;");
                blank(p);
                pr(p, 0, "/**");
                pr(p, 0, " *  Top-level interface to the " + packageName + " grammar.");
                pr(p, 0, " */");
                pr(p, 0, "public class " + className + " {");
                blank(p);
                pr(p, 1, "private Translator translator;");
                blank(p);
                pr(p, 1, "/**");
                pr(p, 1, " *  Create a new " + className + " object given a Translator.");
                pr(p, 1, " *  @param translator A Translator for the " + packageName + " grammar.");
                pr(p, 1, " */");
                pr(p, 1, "public " + className + "(Translator translator) {");
                pr(p, 2, "this.translator = translator;");
                pr(p, 1, "}");
                blank(p);
                pr(p, 1, "/**");
                pr(p, 1, " *  Create a new " + className + " object by creating a Translator.");
                pr(p, 1, " *  from a properties file.");
                pr(p, 1, " *  @param props Resource name for grammar properties file.");
                pr(p, 1, " */");
                pr(p, 1, "public " + className + "(String props) throws java.io.IOException {");
                pr(p, 2, "this(TranslatorFactory.createTranslator(props));");
                pr(p, 1, "}");
                blank(p);

                pr(p, 1, "private <T> List<T> parseTyped(String lang, String s, Class<T> c) {");
                pr(p, 2, "Collection<Tree> ts = translator.parse(lang, s);");
                pr(p, 2, "List<T> xs = new LinkedList<T>();");
                pr(p, 2, "for (Tree t : ts)");
                pr(p, 3, "xs.add(TreeToTyped.getInstance().fromTree(t, c));");
                pr(p, 2, "return xs;");
                pr(p, 1, "}");

                pr(p, 1, "private <T> List<Pair<String,T>> parseTypedWithAll(String s, Class<T> c) {");
                pr(p, 2, "Collection<Pair<String,Tree>> ts = translator.parseWithAll(s);");
                pr(p, 2, "List<Pair<String,T>> xs = new LinkedList<Pair<String,T>>();");
                pr(p, 2, "for (Pair<String,Tree> t : ts)");
                pr(p, 3, "xs.add(pair(t.fst, TreeToTyped.getInstance().fromTree(t.snd, c)));");
                pr(p, 2, "return xs;");
                pr(p, 1, "}");

                for (String cat : prods.keySet()) {
                        String c = makeClassName(cat);
                        blank(p);
                        pr(p, 1, "/**");
                        pr(p, 1, " *  Parse a string in the " + cat + " category.");
                        pr(p, 1, " *  @param lang The name of the concrete syntax to parse with.");
                        pr(p, 1, " *  @param s The string to parse.");
                        pr(p, 1, " */");
                        pr(p, 1, "public List<" + c + "> parse" + c + "(String lang, String s) {");
                        pr(p, 2, "return parseTyped(lang, s, " + c + ".class);");
                        pr(p, 1, "}");

                        blank(p);
                        pr(p, 1, "/**");
                        pr(p, 1, " *  Parse a string in the " + cat + " category with all concrete syntaxes.");
                        pr(p, 1, " *  @param s The string to parse.");
                        pr(p, 1, " */");
                        pr(p, 1, "public List<Pair<String," + c + ">> parse" + c 
                                 + "WithAll(String s) {");
                        pr(p, 2, "return parseTypedWithAll(s, " + c + ".class);");
                        pr(p, 1, "}");
        
                        blank(p);
                        pr(p, 1, "/**");
                        pr(p, 1, " *  Linearize an abstract syntax term in the " + cat + " category.");
                        pr(p, 1, " *  @param lang The name of the concrete syntax that should be used for linearization.");
                        pr(p, 1, " *  @param q The abstract syntax term to linearize.");
                        pr(p, 1, " */");
                        pr(p, 1, "public String linearize" + c + "(String lang, " + c + " q) {");
                        pr(p, 2, "return translator.linearize(lang, TypedToTree.getInstance().toTree(q));");
                        pr(p, 1, "}");
                }

                pr(p, 0, "}");
                return new JavaFile(packageName, className, p);
        }

        /*
         *  Utilities
         */

        /**
         *  Increment the value for a given key.
         *  @return The old value
         */ 
        private static <A> int incValue(Map<A,Integer> map, A x) {
                if (!map.containsKey(x)) {
                        map.put(x, 0);
                        return 0;
                } else {
                        int i = map.get(x)+1;
                        map.put(x,i);
                        return i;
                }
        }

        private static <A> Map<A,Integer> countValues(Collection<A> xs) {
                Map<A,Integer> count = new HashMap<A,Integer>();
                for (A x : xs)
                        incValue(count, x);
                return count;
        }

        private static String firstToUpper(String s) {
                return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }

        private static void writeJavaFile(File baseDir, JavaFile f) 
                throws IOException {

                String packagePath = f.packageName.replace('.', File.separatorChar);
                File dir = new File(baseDir, packagePath);
                boolean createdDir = dir.mkdirs();
                if (createdDir)
                        System.err.println("Created " + dir);

                File file = new File(dir, f.className + ".java");
                System.err.println("Writing " + file);

                PrintWriter p = new PrintWriter(file);
                p.append(f.contents);
                p.close();
        }

        private static void pr(PrintWriter p, int indent, String s) {
                for (int i = INDENT_SIZE*indent; i > 0; i--)
                        p.print(' ');
                p.println(s);
        }

        private static void pr(StringBuilder p, int indent, String s) {
                for (int i = INDENT_SIZE*indent; i > 0; i--)
                        p.append(' ');
                p.append(s).append('\n');
        }

        private static void blank(PrintWriter p) {
                p.println();
        }

        private static void blank(StringBuilder p) {
                p.append('\n');
        }

        private static String makeClassName(String name) {
                if (name.endsWith(".Int"))
                        return "Integer";
                else if (name.endsWith(".String"))
                        return "String";
                else {
                        String[] cs = name.split("\\.");
                        return cs[cs.length-1];
                }
        }

/*
// not used yet

        private static Function getFunction(Set<Function> funs, String name) {
                for (Function f : funs)
                        if (f.getName().equals(name))
                                return f;
                return null;
        }

        private static boolean isListCat(String cat, Set<Function> funs) {
                if (cat.startsWith("List")) {
                        String elemCat = cat.substring(4);
                        if (funs.size() != 2)
                                return false;
                        String base = getFunction(funs, "Base" + elemCat);
                        String cons = getFunction(funs, "Cons" + elemCat);
                        if (base == null || cons == null)
                                return false;
                        return true;
                } else
                        return false;
        }
*/

        public static void main(String[] args) throws IOException {
                if (args.length != 2) {
                        System.err.println("Usage: GenAbsSynClasses <gfcm file> <package name>");
                        System.exit(2);
                }

                String file = args[0];
                String packageName = args[1];

                File baseDir = new File(".");

                String absSynPackageName = packageName; // + ".abssyn" ?;

                GFCMModules ms = GFCMModules.loadGFCM(file);
                GFCModule absMod = ms.getMainAbstractModule();
                if (absMod == null) {
                        System.err.println("Found no abstract module in " + file + ".");
                        System.exit(1);
                }

                Map<String,Set<Function>> prods = moduleAbsProds(absMod);
                for (Map.Entry<String,Set<Function>> e : prods.entrySet()) {
                        String cat = e.getKey();
                        Set<Function> funs = e.getValue();

                        // FIXME: create special classes for list categories

                        // FIXME: do something special if there is only one fun in the cat
                        writeJavaFile(baseDir, makeCatFile(absSynPackageName, cat, funs));
                        for (Function f : funs)
                                writeJavaFile(baseDir, makeFunFile(absSynPackageName, f));
                }

                Set<Function> fs = absMod.listFunctions();
                writeJavaFile(baseDir, makeTreeToTyped(packageName, prods));
                writeJavaFile(baseDir, makeTypedToTree(packageName, prods));
                writeJavaFile(baseDir, makeTopClass(packageName, prods));
        }

}
