package se.chalmers.cs.gf.GFC.Absyn; // Java Package generated by the BNF Converter.

public abstract class Variant implements java.io.Serializable {
  public abstract <R,A> R accept(Variant.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(se.chalmers.cs.gf.GFC.Absyn.Var p, A arg);

  }

}