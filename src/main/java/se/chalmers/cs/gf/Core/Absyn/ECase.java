package se.chalmers.cs.gf.Core.Absyn; // Java Package generated by the BNF Converter.

public class ECase extends Exp {
  public final Exp exp_;
  public final ListCase listcase_;

  public ECase(Exp p1, ListCase p2) { exp_ = p1; listcase_ = p2; }

  public <R,A> R accept(se.chalmers.cs.gf.Core.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.Core.Absyn.ECase) {
      se.chalmers.cs.gf.Core.Absyn.ECase x = (se.chalmers.cs.gf.Core.Absyn.ECase)o;
      return this.exp_.equals(x.exp_) && this.listcase_.equals(x.listcase_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.exp_.hashCode())+this.listcase_.hashCode();
  }


}