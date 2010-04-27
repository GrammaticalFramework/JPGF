package Trees;

import Trees.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  Trees.Absyn.Tree.Visitor<R,A>,
  Trees.Absyn.Lit.Visitor<R,A>
{}
