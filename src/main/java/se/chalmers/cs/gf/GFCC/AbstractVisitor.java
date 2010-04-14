package se.chalmers.cs.gf.GFCC;
import se.chalmers.cs.gf.GFCC.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Grammar */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Grm p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Grammar p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Header */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Hdr p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Header p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Abstract */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Abs p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Abstract p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Concrete */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Cnc p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Concrete p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* AbsDef */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Fun p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.AbsDef p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CncDef */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Lin p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.CncDef p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Type */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Typ p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Type p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Exp */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Tr p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Exp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Atom */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AC p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AS p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AI p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AF p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AM p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Atom p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Term */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.R p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.P p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.S p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.K p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.V p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.C p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.F p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.FV p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.W p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.RP p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.TM p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.L p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.BV p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Term p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Tokn */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.KS p, A arg) { return visitDefault(p, arg); }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.KP p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Tokn p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Variant */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Var p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(se.chalmers.cs.gf.GFCC.Absyn.Variant p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
