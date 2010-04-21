package se.chalmers.cs.gf.GFC.Absyn; // Java Package generated by the BNF Converter.

public class K extends Term {
  public final Tokn tokn_;

  public K(Tokn p1) { tokn_ = p1; }

  public <R,A> R accept(se.chalmers.cs.gf.GFC.Absyn.Term.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.GFC.Absyn.K) {
      se.chalmers.cs.gf.GFC.Absyn.K x = (se.chalmers.cs.gf.GFC.Absyn.K)o;
      return this.tokn_.equals(x.tokn_);
    }
    return false;
  }

  public int hashCode() {
    return this.tokn_.hashCode();
  }


}