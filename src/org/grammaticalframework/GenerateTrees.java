package org.grammaticalframework;

import org.grammaticalframework.Trees.Absyn.*;
import org.grammaticalframework.reader.*;
import java.util.*;


/**
 * This class generates all {@link Tree}s up to a certain depth.
 * <p>
 * Usage:
 * <pre>
 * {@code
 * PGF myPGF = loadPGF("MyGrammar.pgf");
 * GenerateTrees generator = new GenerateTrees(myPgf);
 * for (Tree t: generator.generateTrees("Sentence", 4)) // Generates sentence of depth up to 4
 *     linearize(t);
 * }
 * </pre>
 */
public class GenerateTrees {

    private final Abstract mAbstract;

    /**
     * Initialise the generator with the given abstract;
     * @param  abs An abstract syntax definition.
     **/
    public GenerateTrees(Abstract abs) {
	this.mAbstract = abs;
    }

    /**
     * Initialise the generator with the abstract synta associated with the 
     * given pgf.
     * @param  pgf  A PGF object
     **/
    public GenerateTrees(PGF pgf) {
	this(pgf.abstr);
    }

    /**
     * Generates all {@link Tree}s where the root of the tree is the 
     * given category and the depth is at most 'depth'
     * @param startcat  Root category of the tree
     * @param depth     Maximum depth of the tree
     * @return          A collection of trees
     **/
    public Collection<Tree> generateTrees(String startcat, int depth) {
	//System.out.println("Generating trees for " + startcat + " [" + depth + "]");
	Vector<Tree> trees = new Vector();
	for (AbsFun f: mAbstract.functions(startcat))
	    if (depth > 0 || f.type.hypos.length == 0)
		trees.addAll(generateTrees(f, depth));
	return trees;
    }

    private Collection<Tree> generateTrees(AbsFun function, int depth) {
	//System.out.println("Generating trees for " + function + " [" + depth + "]");
	Type t = function.type;
	int argc = function.type.hypos.length;
	assert !t.isFunctorType() : "Functor not supported by JPGF";
	String[] argCat = new String[argc];
	for (int i = 0; i < argc; i++)
	    /* Since we asserted that 'function' is not a functor, 
	     * We know that its argument types are not function types. */
	    argCat[i] = function.type.hypos[i].type.getName();
	Collection<Tree>[] children = new Collection[argc];
	for (int i = 0; i < argc; i++) {
	    children[i] = generateTrees(argCat[i], depth - 1);
	}
	Vector<Tree> results = new Vector();
	Tree f = new  Function(function.name);
	for (List<Tree> args: Utils.combine(children)) {
	    results.add(Application.application(f, args));
	}
	return results;	
    }
}