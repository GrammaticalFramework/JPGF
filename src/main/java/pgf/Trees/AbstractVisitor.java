package pgf.Trees;
import pgf.Trees.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tree */
    public R visit(pgf.Trees.Absyn.Lambda p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.Variable p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.Application p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.Literal p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.MetaVariable p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.Function p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(pgf.Trees.Absyn.Tree p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Lit */
    public R visit(pgf.Trees.Absyn.IntLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.FloatLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(pgf.Trees.Absyn.StringLiteral p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(pgf.Trees.Absyn.Lit p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
