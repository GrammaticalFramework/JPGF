package se.chalmers.cs.gf.GFCC.Absyn; // Java Package generated by the BNF Converter.

public class Abs extends Abstract {
  public final ListAbsDef listabsdef_;

  public Abs(ListAbsDef p1) { listabsdef_ = p1; }

  public <R,A> R accept(se.chalmers.cs.gf.GFCC.Absyn.Abstract.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof se.chalmers.cs.gf.GFCC.Absyn.Abs) {
      se.chalmers.cs.gf.GFCC.Absyn.Abs x = (se.chalmers.cs.gf.GFCC.Absyn.Abs)o;
      return this.listabsdef_.equals(x.listabsdef_);
    }
    return false;
  }

  public int hashCode() {
    return this.listabsdef_.hashCode();
  }


}