package se.chalmers.cs.gf.MCFG;
import se.chalmers.cs.gf.MCFG.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  se.chalmers.cs.gf.MCFG.Absyn.MCFGM.Visitor<se.chalmers.cs.gf.MCFG.Absyn.MCFGM,A>,
  se.chalmers.cs.gf.MCFG.Absyn.MCFG.Visitor<se.chalmers.cs.gf.MCFG.Absyn.MCFG,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Flag.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Flag,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Rule.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Rule,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Profile.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Profile,A>,
  se.chalmers.cs.gf.MCFG.Absyn.ProfileItem.Visitor<se.chalmers.cs.gf.MCFG.Absyn.ProfileItem,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Field.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Field,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Sym.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Sym,A>,
  se.chalmers.cs.gf.MCFG.Absyn.Cat.Visitor<se.chalmers.cs.gf.MCFG.Absyn.Cat,A>
{
/* MCFGM */
    public MCFGM visit(se.chalmers.cs.gf.MCFG.Absyn.MCFGM p, A arg)
    {
      ListMCFG listmcfg_ = new ListMCFG();
      for (MCFG x : p.listmcfg_) {
        listmcfg_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.MCFG.Absyn.MCFGM(listmcfg_);
    }

/* MCFG */
    public MCFG visit(se.chalmers.cs.gf.MCFG.Absyn.MCFG p, A arg)
    {
      String ident_ = p.ident_;
      ListFlag listflag_ = new ListFlag();
      for (Flag x : p.listflag_) {
        listflag_.add(x.accept(this,arg));
      }
      ListRule listrule_ = new ListRule();
      for (Rule x : p.listrule_) {
        listrule_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.MCFG.Absyn.MCFG(ident_, listflag_, listrule_);
    }

/* Flag */
    public Flag visit(se.chalmers.cs.gf.MCFG.Absyn.StartCat p, A arg)
    {
      Cat cat_ = p.cat_.accept(this, arg);

      return new se.chalmers.cs.gf.MCFG.Absyn.StartCat(cat_);
    }

/* Rule */
    public Rule visit(se.chalmers.cs.gf.MCFG.Absyn.Rule p, A arg)
    {
      String ident_ = p.ident_;
      Profile profile_ = p.profile_.accept(this, arg);
      Cat cat_ = p.cat_.accept(this, arg);
      ListCat listcat_ = new ListCat();
      for (Cat x : p.listcat_) {
        listcat_.add(x.accept(this,arg));
      }
      ListField listfield_ = new ListField();
      for (Field x : p.listfield_) {
        listfield_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.MCFG.Absyn.Rule(ident_, profile_, cat_, listcat_, listfield_);
    }

/* Profile */
    public Profile visit(se.chalmers.cs.gf.MCFG.Absyn.Profile p, A arg)
    {
      ListProfileItem listprofileitem_ = new ListProfileItem();
      for (ProfileItem x : p.listprofileitem_) {
        listprofileitem_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.MCFG.Absyn.Profile(listprofileitem_);
    }

/* ProfileItem */
    public ProfileItem visit(se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem p, A arg)
    {
      Integer integer_ = p.integer_;

      return new se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem(integer_);
    }
    public ProfileItem visit(se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem p, A arg)
    {

      return new se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem();
    }

/* Field */
    public Field visit(se.chalmers.cs.gf.MCFG.Absyn.Field p, A arg)
    {
      String ident_ = p.ident_;
      ListSym listsym_ = new ListSym();
      for (Sym x : p.listsym_) {
        listsym_.add(x.accept(this,arg));
      }

      return new se.chalmers.cs.gf.MCFG.Absyn.Field(ident_, listsym_);
    }

/* Sym */
    public Sym visit(se.chalmers.cs.gf.MCFG.Absyn.Term p, A arg)
    {
      String string_ = p.string_;

      return new se.chalmers.cs.gf.MCFG.Absyn.Term(string_);
    }
    public Sym visit(se.chalmers.cs.gf.MCFG.Absyn.Proj p, A arg)
    {
      Cat cat_ = p.cat_.accept(this, arg);
      Integer integer_ = p.integer_;
      String ident_ = p.ident_;

      return new se.chalmers.cs.gf.MCFG.Absyn.Proj(cat_, integer_, ident_);
    }

/* Cat */
    public Cat visit(se.chalmers.cs.gf.MCFG.Absyn.Cat p, A arg)
    {
      String singlequotestring_ = p.singlequotestring_;

      return new se.chalmers.cs.gf.MCFG.Absyn.Cat(singlequotestring_);
    }

}