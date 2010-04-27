package Trees;
import Trees.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  Trees.Absyn.Tree.Visitor<Trees.Absyn.Tree,A>,
  Trees.Absyn.Lit.Visitor<Trees.Absyn.Lit,A>
{
/* Tree */
    public Tree visit(Trees.Absyn.Lambda p, A arg)
    {
      String ident_ = p.ident_;
      Tree tree_ = p.tree_.accept(this, arg);

      return new Trees.Absyn.Lambda(ident_, tree_);
    }
    public Tree visit(Trees.Absyn.Variable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new Trees.Absyn.Variable(integer_);
    }
    public Tree visit(Trees.Absyn.Application p, A arg)
    {
      Tree tree_1 = p.tree_1.accept(this, arg);
      Tree tree_2 = p.tree_2.accept(this, arg);

      return new Trees.Absyn.Application(tree_1, tree_2);
    }
    public Tree visit(Trees.Absyn.Literal p, A arg)
    {
      Lit lit_ = p.lit_.accept(this, arg);

      return new Trees.Absyn.Literal(lit_);
    }
    public Tree visit(Trees.Absyn.MetaVariable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new Trees.Absyn.MetaVariable(integer_);
    }
    public Tree visit(Trees.Absyn.Function p, A arg)
    {
      String ident_ = p.ident_;

      return new Trees.Absyn.Function(ident_);
    }

/* Lit */
    public Lit visit(Trees.Absyn.IntLiteral p, A arg)
    {
      Integer integer_ = p.integer_;

      return new Trees.Absyn.IntLiteral(integer_);
    }
    public Lit visit(Trees.Absyn.FloatLiteral p, A arg)
    {
      Double double_ = p.double_;

      return new Trees.Absyn.FloatLiteral(double_);
    }
    public Lit visit(Trees.Absyn.StringLiteral p, A arg)
    {
      String string_ = p.string_;

      return new Trees.Absyn.StringLiteral(string_);
    }

}