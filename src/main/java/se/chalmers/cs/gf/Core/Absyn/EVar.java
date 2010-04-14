package se.chalmers.cs.gf.Core.Absyn; // Java Package generated by the BNF Converter.

public class EVar extends Exp {
  public final String cident_;

  public EVar(String p1) { cident_ = p1; }

  public <R,A> R accept(se.chalmers.cs.gf.Core.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.Core.Absyn.EVar) {
      se.chalmers.cs.gf.Core.Absyn.EVar x = (se.chalmers.cs.gf.Core.Absyn.EVar)o;
      return this.cident_.equals(x.cident_);
    }
    return false;
  }

  public int hashCode() {
    return this.cident_.hashCode();
  }


}