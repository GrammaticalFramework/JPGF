package se.chalmers.cs.gf.GFCC;

import se.chalmers.cs.gf.GFCC.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Grammar */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Grm p, A arg) {
      R r = leaf(arg);
      r = combine(p.header_.accept(this, arg), r, arg);
      r = combine(p.abstract_.accept(this, arg), r, arg);
      for (Concrete x : p.listconcrete_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Header */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Hdr p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Abstract */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Abs p, A arg) {
      R r = leaf(arg);
      for (AbsDef x : p.listabsdef_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Concrete */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Cnc p, A arg) {
      R r = leaf(arg);
      for (CncDef x : p.listcncdef_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* AbsDef */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Fun p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      r = combine(p.exp_.accept(this, arg), r, arg);
      return r;
    }

/* CncDef */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Lin p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }

/* Type */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Typ p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Exp */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Tr p, A arg) {
      R r = leaf(arg);
      r = combine(p.atom_.accept(this, arg), r, arg);
      for (Exp x : p.listexp_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Atom */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AC p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AS p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AI p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AF p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.AM p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Term */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.R p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.P p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.S p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.K p, A arg) {
      R r = leaf(arg);
      r = combine(p.tokn_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.V p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.C p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.F p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.FV p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.W p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.RP p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.TM p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.L p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.BV p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Tokn */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.KS p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.KP p, A arg) {
      R r = leaf(arg);
      for (Variant x : p.listvariant_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Variant */
    public R visit(se.chalmers.cs.gf.GFCC.Absyn.Var p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
