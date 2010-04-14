package se.chalmers.cs.gf.GFCC;
import se.chalmers.cs.gf.GFCC.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  se.chalmers.cs.gf.GFCC.Absyn.Grammar.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Grammar,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Header.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Header,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Abstract.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Abstract,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Concrete.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Concrete,A>,
  se.chalmers.cs.gf.GFCC.Absyn.AbsDef.Visitor<se.chalmers.cs.gf.GFCC.Absyn.AbsDef,A>,
  se.chalmers.cs.gf.GFCC.Absyn.CncDef.Visitor<se.chalmers.cs.gf.GFCC.Absyn.CncDef,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Type.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Type,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Exp.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Exp,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Atom.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Atom,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Term.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Term,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Tokn.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Tokn,A>,
  se.chalmers.cs.gf.GFCC.Absyn.Variant.Visitor<se.chalmers.cs.gf.GFCC.Absyn.Variant,A>
{
/* Grammar */
    public Grammar visit(se.chalmers.cs.gf.GFCC.Absyn.Grm p, A arg)
    {
      Header header_ = p.header_.accept(this, arg);
      Abstract abstract_ = p.abstract_.accept(this, arg);
      ListConcrete listconcrete_ = new ListConcrete();
      for (Concrete x : p.listconcrete_) {
        listconcrete_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.Grm(header_, abstract_, listconcrete_);
    }

/* Header */
    public Header visit(se.chalmers.cs.gf.GFCC.Absyn.Hdr p, A arg)
    {
      String cid_ = p.cid_;
      ListCId listcid_ = p.listcid_;

      return new se.chalmers.cs.gf.GFCC.Absyn.Hdr(cid_, listcid_);
    }

/* Abstract */
    public Abstract visit(se.chalmers.cs.gf.GFCC.Absyn.Abs p, A arg)
    {
      ListAbsDef listabsdef_ = new ListAbsDef();
      for (AbsDef x : p.listabsdef_) {
        listabsdef_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.Abs(listabsdef_);
    }

/* Concrete */
    public Concrete visit(se.chalmers.cs.gf.GFCC.Absyn.Cnc p, A arg)
    {
      String cid_ = p.cid_;
      ListCncDef listcncdef_ = new ListCncDef();
      for (CncDef x : p.listcncdef_) {
        listcncdef_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.Cnc(cid_, listcncdef_);
    }

/* AbsDef */
    public AbsDef visit(se.chalmers.cs.gf.GFCC.Absyn.Fun p, A arg)
    {
      String cid_ = p.cid_;
      Type type_ = p.type_.accept(this, arg);
      Exp exp_ = p.exp_.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.Fun(cid_, type_, exp_);
    }

/* CncDef */
    public CncDef visit(se.chalmers.cs.gf.GFCC.Absyn.Lin p, A arg)
    {
      String cid_ = p.cid_;
      Term term_ = p.term_.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.Lin(cid_, term_);
    }

/* Type */
    public Type visit(se.chalmers.cs.gf.GFCC.Absyn.Typ p, A arg)
    {
      ListCId listcid_ = p.listcid_;
      String cid_ = p.cid_;

      return new se.chalmers.cs.gf.GFCC.Absyn.Typ(listcid_, cid_);
    }

/* Exp */
    public Exp visit(se.chalmers.cs.gf.GFCC.Absyn.Tr p, A arg)
    {
      Atom atom_ = p.atom_.accept(this, arg);
      ListExp listexp_ = new ListExp();
      for (Exp x : p.listexp_) {
        listexp_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.Tr(atom_, listexp_);
    }

/* Atom */
    public Atom visit(se.chalmers.cs.gf.GFCC.Absyn.AC p, A arg)
    {
      String cid_ = p.cid_;

      return new se.chalmers.cs.gf.GFCC.Absyn.AC(cid_);
    }
    public Atom visit(se.chalmers.cs.gf.GFCC.Absyn.AS p, A arg)
    {
      String string_ = p.string_;

      return new se.chalmers.cs.gf.GFCC.Absyn.AS(string_);
    }
    public Atom visit(se.chalmers.cs.gf.GFCC.Absyn.AI p, A arg)
    {
      Integer integer_ = p.integer_;

      return new se.chalmers.cs.gf.GFCC.Absyn.AI(integer_);
    }
    public Atom visit(se.chalmers.cs.gf.GFCC.Absyn.AF p, A arg)
    {
      Double double_ = p.double_;

      return new se.chalmers.cs.gf.GFCC.Absyn.AF(double_);
    }
    public Atom visit(se.chalmers.cs.gf.GFCC.Absyn.AM p, A arg)
    {

      return new se.chalmers.cs.gf.GFCC.Absyn.AM();
    }

/* Term */
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.R p, A arg)
    {
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.R(listterm_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.P p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.P(term_1, term_2);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.S p, A arg)
    {
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.S(listterm_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.K p, A arg)
    {
      Tokn tokn_ = p.tokn_.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.K(tokn_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.V p, A arg)
    {
      Integer integer_ = p.integer_;

      return new se.chalmers.cs.gf.GFCC.Absyn.V(integer_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.C p, A arg)
    {
      Integer integer_ = p.integer_;

      return new se.chalmers.cs.gf.GFCC.Absyn.C(integer_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.F p, A arg)
    {
      String cid_ = p.cid_;

      return new se.chalmers.cs.gf.GFCC.Absyn.F(cid_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.FV p, A arg)
    {
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.FV(listterm_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.W p, A arg)
    {
      String string_ = p.string_;
      Term term_ = p.term_.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.W(string_, term_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.RP p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.RP(term_1, term_2);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.TM p, A arg)
    {

      return new se.chalmers.cs.gf.GFCC.Absyn.TM();
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.L p, A arg)
    {
      String cid_ = p.cid_;
      Term term_ = p.term_.accept(this, arg);

      return new se.chalmers.cs.gf.GFCC.Absyn.L(cid_, term_);
    }
    public Term visit(se.chalmers.cs.gf.GFCC.Absyn.BV p, A arg)
    {
      String cid_ = p.cid_;

      return new se.chalmers.cs.gf.GFCC.Absyn.BV(cid_);
    }

/* Tokn */
    public Tokn visit(se.chalmers.cs.gf.GFCC.Absyn.KS p, A arg)
    {
      String string_ = p.string_;

      return new se.chalmers.cs.gf.GFCC.Absyn.KS(string_);
    }
    public Tokn visit(se.chalmers.cs.gf.GFCC.Absyn.KP p, A arg)
    {
      ListString liststring_ = p.liststring_;
      ListVariant listvariant_ = new ListVariant();
      for (Variant x : p.listvariant_) {
        listvariant_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.GFCC.Absyn.KP(liststring_, listvariant_);
    }

/* Variant */
    public Variant visit(se.chalmers.cs.gf.GFCC.Absyn.Var p, A arg)
    {
      ListString liststring_1 = p.liststring_1;
      ListString liststring_2 = p.liststring_2;

      return new se.chalmers.cs.gf.GFCC.Absyn.Var(liststring_1, liststring_2);
    }

}