package org.grammaticalframework.Trees;

import org.grammaticalframework.Trees.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Tree */
    public R visit(org.grammaticalframework.Trees.Absyn.Lambda p, A arg) {
      R r = leaf(arg);
      r = combine(p.tree_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.Variable p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.Application p, A arg) {
      R r = leaf(arg);
      r = combine(p.tree_1.accept(this, arg), r, arg);
      r = combine(p.tree_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.Literal p, A arg) {
      R r = leaf(arg);
      r = combine(p.lit_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.MetaVariable p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.Function p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Lit */
    public R visit(org.grammaticalframework.Trees.Absyn.IntLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.FloatLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(org.grammaticalframework.Trees.Absyn.StringLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
