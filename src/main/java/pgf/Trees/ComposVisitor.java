package pgf.Trees;
import pgf.Trees.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  pgf.Trees.Absyn.Tree.Visitor<pgf.Trees.Absyn.Tree,A>,
  pgf.Trees.Absyn.Lit.Visitor<pgf.Trees.Absyn.Lit,A>
{
/* Tree */
    public Tree visit(pgf.Trees.Absyn.Lambda p, A arg)
    {
      String ident_ = p.ident_;
      Tree tree_ = p.tree_.accept(this, arg);

      return new pgf.Trees.Absyn.Lambda(ident_, tree_);
    }
    public Tree visit(pgf.Trees.Absyn.Variable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new pgf.Trees.Absyn.Variable(integer_);
    }
    public Tree visit(pgf.Trees.Absyn.Application p, A arg)
    {
      Tree tree_1 = p.tree_1.accept(this, arg);
      Tree tree_2 = p.tree_2.accept(this, arg);

      return new pgf.Trees.Absyn.Application(tree_1, tree_2);
    }
    public Tree visit(pgf.Trees.Absyn.Literal p, A arg)
    {
      Lit lit_ = p.lit_.accept(this, arg);

      return new pgf.Trees.Absyn.Literal(lit_);
    }
    public Tree visit(pgf.Trees.Absyn.MetaVariable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new pgf.Trees.Absyn.MetaVariable(integer_);
    }
    public Tree visit(pgf.Trees.Absyn.Function p, A arg)
    {
      String ident_ = p.ident_;

      return new pgf.Trees.Absyn.Function(ident_);
    }

/* Lit */
    public Lit visit(pgf.Trees.Absyn.IntLiteral p, A arg)
    {
      Integer integer_ = p.integer_;

      return new pgf.Trees.Absyn.IntLiteral(integer_);
    }
    public Lit visit(pgf.Trees.Absyn.FloatLiteral p, A arg)
    {
      Double double_ = p.double_;

      return new pgf.Trees.Absyn.FloatLiteral(double_);
    }
    public Lit visit(pgf.Trees.Absyn.StringLiteral p, A arg)
    {
      String string_ = p.string_;

      return new pgf.Trees.Absyn.StringLiteral(string_);
    }

}