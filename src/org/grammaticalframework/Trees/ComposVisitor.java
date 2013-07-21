package org.grammaticalframework.Trees;
import org.grammaticalframework.Trees.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  org.grammaticalframework.Trees.Absyn.Tree.Visitor<org.grammaticalframework.Trees.Absyn.Tree,A>,
  org.grammaticalframework.Trees.Absyn.Lit.Visitor<org.grammaticalframework.Trees.Absyn.Lit,A>
{
/* Tree */
    public Tree visit(org.grammaticalframework.Trees.Absyn.Lambda p, A arg)
    {
      String ident_ = p.ident_;
      Tree tree_ = p.tree_.accept(this, arg);

      return new org.grammaticalframework.Trees.Absyn.Lambda(ident_, tree_);
    }
    public Tree visit(org.grammaticalframework.Trees.Absyn.Variable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new org.grammaticalframework.Trees.Absyn.Variable(integer_);
    }
    public Tree visit(org.grammaticalframework.Trees.Absyn.Application p, A arg)
    {
      Tree tree_1 = p.tree_1.accept(this, arg);
      Tree tree_2 = p.tree_2.accept(this, arg);

      return new org.grammaticalframework.Trees.Absyn.Application(tree_1, tree_2);
    }
    public Tree visit(org.grammaticalframework.Trees.Absyn.Literal p, A arg)
    {
      Lit lit_ = p.lit_.accept(this, arg);

      return new org.grammaticalframework.Trees.Absyn.Literal(lit_);
    }
    public Tree visit(org.grammaticalframework.Trees.Absyn.MetaVariable p, A arg)
    {
      Integer integer_ = p.integer_;

      return new org.grammaticalframework.Trees.Absyn.MetaVariable(integer_);
    }
    public Tree visit(org.grammaticalframework.Trees.Absyn.Function p, A arg)
    {
      String ident_ = p.ident_;

      return new org.grammaticalframework.Trees.Absyn.Function(ident_);
    }

/* Lit */
    public Lit visit(org.grammaticalframework.Trees.Absyn.IntLiteral p, A arg)
    {
      Integer integer_ = p.integer_;

      return new org.grammaticalframework.Trees.Absyn.IntLiteral(integer_);
    }
    public Lit visit(org.grammaticalframework.Trees.Absyn.FloatLiteral p, A arg)
    {
      Double double_ = p.double_;

      return new org.grammaticalframework.Trees.Absyn.FloatLiteral(double_);
    }
    public Lit visit(org.grammaticalframework.Trees.Absyn.StringLiteral p, A arg)
    {
      String string_ = p.string_;

      return new org.grammaticalframework.Trees.Absyn.StringLiteral(string_);
    }

}
