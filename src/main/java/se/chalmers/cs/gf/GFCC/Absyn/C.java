package se.chalmers.cs.gf.GFCC.Absyn; // Java Package generated by the BNF Converter.

public class C extends Term {
  public final Integer integer_;

  public C(Integer p1) { integer_ = p1; }

  public <R,A> R accept(se.chalmers.cs.gf.GFCC.Absyn.Term.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.GFCC.Absyn.C) {
      se.chalmers.cs.gf.GFCC.Absyn.C x = (se.chalmers.cs.gf.GFCC.Absyn.C)o;
      return this.integer_.equals(x.integer_);
    }
    return false;
  }

  public int hashCode() {
    return this.integer_.hashCode();
  }


}