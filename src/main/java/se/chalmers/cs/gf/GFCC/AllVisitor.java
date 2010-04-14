package se.chalmers.cs.gf.GFCC;

import se.chalmers.cs.gf.GFCC.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  se.chalmers.cs.gf.GFCC.Absyn.Grammar.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Header.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Abstract.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Concrete.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.AbsDef.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.CncDef.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Type.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Exp.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Atom.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Term.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Tokn.Visitor<R,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Variant.Visitor<R,A>
{}
