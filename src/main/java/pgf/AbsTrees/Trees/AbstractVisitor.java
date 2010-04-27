package Trees;
import Trees.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tree */
    public R visit(Trees.Absyn.Lambda p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.Variable p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.Application p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.Literal p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.MetaVariable p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.Function p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(Trees.Absyn.Tree p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Lit */
    public R visit(Trees.Absyn.IntLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.FloatLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(Trees.Absyn.StringLiteral p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(Trees.Absyn.Lit p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
