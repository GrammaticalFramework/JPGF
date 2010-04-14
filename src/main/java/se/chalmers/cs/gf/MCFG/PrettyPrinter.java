package se.chalmers.cs.gf.MCFG;
import se.chalmers.cs.gf.MCFG.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.MCFGM foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.MCFGM foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.MCFG foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.MCFG foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListMCFG foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListMCFG foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Flag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Flag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListFlag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListFlag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Rule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Rule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListRule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListRule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Profile foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Profile foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListProfileItem foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListProfileItem foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ProfileItem foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ProfileItem foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Field foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Field foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListField foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListField foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Sym foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Sym foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListSym foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListSym foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.Cat foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.Cat foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.MCFG.Absyn.ListCat foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.MCFG.Absyn.ListCat foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.MCFGM foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MCFGM)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MCFGM _mcfgm = (se.chalmers.cs.gf.MCFG.Absyn.MCFGM) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_mcfgm.listmcfg_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.MCFG foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MCFG)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MCFG _mcfg = (se.chalmers.cs.gf.MCFG.Absyn.MCFG) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("grammar");
       pp(_mcfg.ident_, 0);
       render(";");
       pp(_mcfg.listflag_, 0);
       pp(_mcfg.listrule_, 0);
       render("end");
       render("grammar");
       render(";");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListMCFG foo, int _i_)
  {
     for (java.util.Iterator<MCFG> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Flag foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.StartCat)
    {
       se.chalmers.cs.gf.MCFG.Absyn.StartCat _startcat = (se.chalmers.cs.gf.MCFG.Absyn.StartCat) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("startcat");
       pp(_startcat.cat_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListFlag foo, int _i_)
  {
     for (java.util.Iterator<Flag> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Rule foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Rule)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Rule _rule = (se.chalmers.cs.gf.MCFG.Absyn.Rule) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_rule.ident_, 0);
       pp(_rule.profile_, 0);
       render(".");
       pp(_rule.cat_, 0);
       render("->");
       pp(_rule.listcat_, 0);
       render(":=");
       render("{");
       pp(_rule.listfield_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListRule foo, int _i_)
  {
     for (java.util.Iterator<Rule> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Profile foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Profile)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Profile _profile = (se.chalmers.cs.gf.MCFG.Absyn.Profile) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       pp(_profile.listprofileitem_, 0);
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListProfileItem foo, int _i_)
  {
     for (java.util.Iterator<ProfileItem> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ProfileItem foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem)
    {
       se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem _argprofileitem = (se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_argprofileitem.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem _metaprofileitem = (se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("?");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Field foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Field)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Field _field = (se.chalmers.cs.gf.MCFG.Absyn.Field) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_field.ident_, 0);
       render("=");
       pp(_field.listsym_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListField foo, int _i_)
  {
     for (java.util.Iterator<Field> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Sym foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Term)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Term _term = (se.chalmers.cs.gf.MCFG.Absyn.Term) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_term.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Proj)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Proj _proj = (se.chalmers.cs.gf.MCFG.Absyn.Proj) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_proj.cat_, 0);
       render("@");
       pp(_proj.integer_, 0);
       render(".");
       pp(_proj.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListSym foo, int _i_)
  {
     for (java.util.Iterator<Sym> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.Cat foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Cat)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Cat _cat = (se.chalmers.cs.gf.MCFG.Absyn.Cat) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_cat.singlequotestring_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.MCFG.Absyn.ListCat foo, int _i_)
  {
     for (java.util.Iterator<Cat> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }


  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.MCFGM foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MCFGM)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MCFGM _mcfgm = (se.chalmers.cs.gf.MCFG.Absyn.MCFGM) foo;
       render("(");
       render("MCFGM");
       render("[");
       sh(_mcfgm.listmcfg_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.MCFG foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MCFG)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MCFG _mcfg = (se.chalmers.cs.gf.MCFG.Absyn.MCFG) foo;
       render("(");
       render("MCFG");
       sh(_mcfg.ident_);
       render("[");
       sh(_mcfg.listflag_);
       render("]");
       render("[");
       sh(_mcfg.listrule_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListMCFG foo)
  {
     for (java.util.Iterator<MCFG> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Flag foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.StartCat)
    {
       se.chalmers.cs.gf.MCFG.Absyn.StartCat _startcat = (se.chalmers.cs.gf.MCFG.Absyn.StartCat) foo;
       render("(");
       render("StartCat");
       sh(_startcat.cat_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListFlag foo)
  {
     for (java.util.Iterator<Flag> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Rule foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Rule)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Rule _rule = (se.chalmers.cs.gf.MCFG.Absyn.Rule) foo;
       render("(");
       render("Rule");
       sh(_rule.ident_);
       sh(_rule.profile_);
       sh(_rule.cat_);
       render("[");
       sh(_rule.listcat_);
       render("]");
       render("[");
       sh(_rule.listfield_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListRule foo)
  {
     for (java.util.Iterator<Rule> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Profile foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Profile)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Profile _profile = (se.chalmers.cs.gf.MCFG.Absyn.Profile) foo;
       render("(");
       render("Profile");
       render("[");
       sh(_profile.listprofileitem_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListProfileItem foo)
  {
     for (java.util.Iterator<ProfileItem> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ProfileItem foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem)
    {
       se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem _argprofileitem = (se.chalmers.cs.gf.MCFG.Absyn.ArgProfileItem) foo;
       render("(");
       render("ArgProfileItem");
       sh(_argprofileitem.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem)
    {
       se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem _metaprofileitem = (se.chalmers.cs.gf.MCFG.Absyn.MetaProfileItem) foo;
       render("MetaProfileItem");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Field foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Field)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Field _field = (se.chalmers.cs.gf.MCFG.Absyn.Field) foo;
       render("(");
       render("Field");
       sh(_field.ident_);
       render("[");
       sh(_field.listsym_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListField foo)
  {
     for (java.util.Iterator<Field> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Sym foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Term)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Term _term = (se.chalmers.cs.gf.MCFG.Absyn.Term) foo;
       render("(");
       render("Term");
       sh(_term.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Proj)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Proj _proj = (se.chalmers.cs.gf.MCFG.Absyn.Proj) foo;
       render("(");
       render("Proj");
       sh(_proj.cat_);
       sh(_proj.integer_);
       sh(_proj.ident_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListSym foo)
  {
     for (java.util.Iterator<Sym> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.Cat foo)
  {
    if (foo instanceof se.chalmers.cs.gf.MCFG.Absyn.Cat)
    {
       se.chalmers.cs.gf.MCFG.Absyn.Cat _cat = (se.chalmers.cs.gf.MCFG.Absyn.Cat) foo;
       render("(");
       render("Cat");
       sh(_cat.singlequotestring_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.MCFG.Absyn.ListCat foo)
  {
     for (java.util.Iterator<Cat> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
	   if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}

