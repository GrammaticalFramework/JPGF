package se.chalmers.cs.gf.GFCC.Absyn; // Java Package generated by the BNF Converter.

public abstract class Grammar implements java.io.Serializable {
  public abstract <R,A> R accept(Grammar.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Grm p, A arg);

  }

}