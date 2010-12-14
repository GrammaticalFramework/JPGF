package org.grammaticalframework.Trees;
import org.grammaticalframework.Trees.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tree */
    public R visit(org.grammaticalframework.Trees.Absyn.Lambda p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.Variable p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.Application p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.Literal p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.MetaVariable p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.Function p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(org.grammaticalframework.Trees.Absyn.Tree p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Lit */
    public R visit(org.grammaticalframework.Trees.Absyn.IntLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.FloatLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(org.grammaticalframework.Trees.Absyn.StringLiteral p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(org.grammaticalframework.Trees.Absyn.Lit p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
