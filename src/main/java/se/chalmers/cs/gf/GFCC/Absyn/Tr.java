package se.chalmers.cs.gf.GFCC.Absyn; // Java Package generated by the BNF Converter.

public class Tr extends Exp {
  public final Atom atom_;
  public final ListExp listexp_;

  public Tr(Atom p1, ListExp p2) { atom_ = p1; listexp_ = p2; }

  public <R,A> R accept(se.chalmers.cs.gf.GFCC.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.GFCC.Absyn.Tr) {
      se.chalmers.cs.gf.GFCC.Absyn.Tr x = (se.chalmers.cs.gf.GFCC.Absyn.Tr)o;
      return this.atom_.equals(x.atom_) && this.listexp_.equals(x.listexp_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.atom_.hashCode())+this.listexp_.hashCode();
  }


}