package se.chalmers.cs.gf.GFCC.Absyn; // Java Package generated by the BNF Converter.

public class F extends Term {
  public final String cid_;

  public F(String p1) { cid_ = p1; }

  public <R,A> R accept(se.chalmers.cs.gf.GFCC.Absyn.Term.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.GFCC.Absyn.F) {
      se.chalmers.cs.gf.GFCC.Absyn.F x = (se.chalmers.cs.gf.GFCC.Absyn.F)o;
      return this.cid_.equals(x.cid_);
    }
    return false;
  }

  public int hashCode() {
    return this.cid_.hashCode();
  }


}