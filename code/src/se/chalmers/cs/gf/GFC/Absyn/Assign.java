package se.chalmers.cs.gf.GFC.Absyn; // Java Package generated by the BNF Converter.

public abstract class Assign implements java.io.Serializable {
  public abstract <R,A> R accept(Assign.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(se.chalmers.cs.gf.GFC.Absyn.Ass p, A arg);

  }

}