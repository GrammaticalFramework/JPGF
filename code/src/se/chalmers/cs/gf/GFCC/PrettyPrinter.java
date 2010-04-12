package se.chalmers.cs.gf.GFCC;
import se.chalmers.cs.gf.GFCC.Absyn.*;

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
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Grammar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Grammar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Header foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Header foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Abstract foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Abstract foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Concrete foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Concrete foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.AbsDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.AbsDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.CncDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.CncDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Type foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Type foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Exp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Atom foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Atom foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Term foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Term foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Tokn foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Tokn foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.Variant foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.Variant foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListConcrete foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListConcrete foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListAbsDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListAbsDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListCncDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListCncDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListCId foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListCId foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListTerm foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListTerm foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListExp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListExp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListString foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListString foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFCC.Absyn.ListVariant foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFCC.Absyn.ListVariant foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Grammar foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Grm)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Grm _grm = (se.chalmers.cs.gf.GFCC.Absyn.Grm) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_grm.header_, 0);
       render(";");
       pp(_grm.abstract_, 0);
       render(";");
       pp(_grm.listconcrete_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Header foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Hdr)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Hdr _hdr = (se.chalmers.cs.gf.GFCC.Absyn.Hdr) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("grammar");
       pp(_hdr.cid_, 0);
       render("(");
       pp(_hdr.listcid_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Abstract foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Abs)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Abs _abs = (se.chalmers.cs.gf.GFCC.Absyn.Abs) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("abstract");
       render("{");
       pp(_abs.listabsdef_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Concrete foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Cnc)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Cnc _cnc = (se.chalmers.cs.gf.GFCC.Absyn.Cnc) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("concrete");
       pp(_cnc.cid_, 0);
       render("{");
       pp(_cnc.listcncdef_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.AbsDef foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Fun)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Fun _fun = (se.chalmers.cs.gf.GFCC.Absyn.Fun) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_fun.cid_, 0);
       render(":");
       pp(_fun.type_, 0);
       render("=");
       pp(_fun.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.CncDef foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Lin)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Lin _lin = (se.chalmers.cs.gf.GFCC.Absyn.Lin) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_lin.cid_, 0);
       render("=");
       pp(_lin.term_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Type foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Typ)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Typ _typ = (se.chalmers.cs.gf.GFCC.Absyn.Typ) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_typ.listcid_, 0);
       render("->");
       pp(_typ.cid_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Tr)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Tr _tr = (se.chalmers.cs.gf.GFCC.Absyn.Tr) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_tr.atom_, 0);
       pp(_tr.listexp_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Atom foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AC)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AC _ac = (se.chalmers.cs.gf.GFCC.Absyn.AC) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ac.cid_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AS)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AS _as = (se.chalmers.cs.gf.GFCC.Absyn.AS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_as.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AI)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AI _ai = (se.chalmers.cs.gf.GFCC.Absyn.AI) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ai.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AF)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AF _af = (se.chalmers.cs.gf.GFCC.Absyn.AF) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_af.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AM)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AM _am = (se.chalmers.cs.gf.GFCC.Absyn.AM) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("?");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Term foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.R)
    {
       se.chalmers.cs.gf.GFCC.Absyn.R _r = (se.chalmers.cs.gf.GFCC.Absyn.R) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       pp(_r.listterm_, 0);
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.P)
    {
       se.chalmers.cs.gf.GFCC.Absyn.P _p = (se.chalmers.cs.gf.GFCC.Absyn.P) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_p.term_1, 0);
       render("!");
       pp(_p.term_2, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.S)
    {
       se.chalmers.cs.gf.GFCC.Absyn.S _s = (se.chalmers.cs.gf.GFCC.Absyn.S) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_s.listterm_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.K)
    {
       se.chalmers.cs.gf.GFCC.Absyn.K _k = (se.chalmers.cs.gf.GFCC.Absyn.K) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_k.tokn_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.V)
    {
       se.chalmers.cs.gf.GFCC.Absyn.V _v = (se.chalmers.cs.gf.GFCC.Absyn.V) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("$");
       pp(_v.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.C)
    {
       se.chalmers.cs.gf.GFCC.Absyn.C _c = (se.chalmers.cs.gf.GFCC.Absyn.C) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_c.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.F)
    {
       se.chalmers.cs.gf.GFCC.Absyn.F _f = (se.chalmers.cs.gf.GFCC.Absyn.F) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_f.cid_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.FV)
    {
       se.chalmers.cs.gf.GFCC.Absyn.FV _fv = (se.chalmers.cs.gf.GFCC.Absyn.FV) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[|");
       pp(_fv.listterm_, 0);
       render("|]");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.W)
    {
       se.chalmers.cs.gf.GFCC.Absyn.W _w = (se.chalmers.cs.gf.GFCC.Absyn.W) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       printQuoted(_w.string_);
       render("+");
       pp(_w.term_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.RP)
    {
       se.chalmers.cs.gf.GFCC.Absyn.RP _rp = (se.chalmers.cs.gf.GFCC.Absyn.RP) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_rp.term_1, 0);
       render("@");
       pp(_rp.term_2, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.TM)
    {
       se.chalmers.cs.gf.GFCC.Absyn.TM _tm = (se.chalmers.cs.gf.GFCC.Absyn.TM) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("?");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.L)
    {
       se.chalmers.cs.gf.GFCC.Absyn.L _l = (se.chalmers.cs.gf.GFCC.Absyn.L) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_l.cid_, 0);
       render("->");
       pp(_l.term_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.BV)
    {
       se.chalmers.cs.gf.GFCC.Absyn.BV _bv = (se.chalmers.cs.gf.GFCC.Absyn.BV) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("#");
       pp(_bv.cid_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Tokn foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.KS)
    {
       se.chalmers.cs.gf.GFCC.Absyn.KS _ks = (se.chalmers.cs.gf.GFCC.Absyn.KS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_ks.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.KP)
    {
       se.chalmers.cs.gf.GFCC.Absyn.KP _kp = (se.chalmers.cs.gf.GFCC.Absyn.KP) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       render("pre");
       pp(_kp.liststring_, 0);
       render("[");
       pp(_kp.listvariant_, 0);
       render("]");
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.Variant foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Var)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Var _var = (se.chalmers.cs.gf.GFCC.Absyn.Var) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_var.liststring_1, 0);
       render("/");
       pp(_var.liststring_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListConcrete foo, int _i_)
  {
     for (java.util.Iterator<Concrete> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListAbsDef foo, int _i_)
  {
     for (java.util.Iterator<AbsDef> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListCncDef foo, int _i_)
  {
     for (java.util.Iterator<CncDef> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListCId foo, int _i_)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListTerm foo, int _i_)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListExp foo, int _i_)
  {
     for (java.util.Iterator<Exp> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListString foo, int _i_)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFCC.Absyn.ListVariant foo, int _i_)
  {
     for (java.util.Iterator<Variant> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }


  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Grammar foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Grm)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Grm _grm = (se.chalmers.cs.gf.GFCC.Absyn.Grm) foo;
       render("(");
       render("Grm");
       sh(_grm.header_);
       sh(_grm.abstract_);
       render("[");
       sh(_grm.listconcrete_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Header foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Hdr)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Hdr _hdr = (se.chalmers.cs.gf.GFCC.Absyn.Hdr) foo;
       render("(");
       render("Hdr");
       sh(_hdr.cid_);
       render("[");
       sh(_hdr.listcid_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Abstract foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Abs)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Abs _abs = (se.chalmers.cs.gf.GFCC.Absyn.Abs) foo;
       render("(");
       render("Abs");
       render("[");
       sh(_abs.listabsdef_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Concrete foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Cnc)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Cnc _cnc = (se.chalmers.cs.gf.GFCC.Absyn.Cnc) foo;
       render("(");
       render("Cnc");
       sh(_cnc.cid_);
       render("[");
       sh(_cnc.listcncdef_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.AbsDef foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Fun)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Fun _fun = (se.chalmers.cs.gf.GFCC.Absyn.Fun) foo;
       render("(");
       render("Fun");
       sh(_fun.cid_);
       sh(_fun.type_);
       sh(_fun.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.CncDef foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Lin)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Lin _lin = (se.chalmers.cs.gf.GFCC.Absyn.Lin) foo;
       render("(");
       render("Lin");
       sh(_lin.cid_);
       sh(_lin.term_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Type foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Typ)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Typ _typ = (se.chalmers.cs.gf.GFCC.Absyn.Typ) foo;
       render("(");
       render("Typ");
       render("[");
       sh(_typ.listcid_);
       render("]");
       sh(_typ.cid_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Exp foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Tr)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Tr _tr = (se.chalmers.cs.gf.GFCC.Absyn.Tr) foo;
       render("(");
       render("Tr");
       sh(_tr.atom_);
       render("[");
       sh(_tr.listexp_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Atom foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AC)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AC _ac = (se.chalmers.cs.gf.GFCC.Absyn.AC) foo;
       render("(");
       render("AC");
       sh(_ac.cid_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AS)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AS _as = (se.chalmers.cs.gf.GFCC.Absyn.AS) foo;
       render("(");
       render("AS");
       sh(_as.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AI)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AI _ai = (se.chalmers.cs.gf.GFCC.Absyn.AI) foo;
       render("(");
       render("AI");
       sh(_ai.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AF)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AF _af = (se.chalmers.cs.gf.GFCC.Absyn.AF) foo;
       render("(");
       render("AF");
       sh(_af.double_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.AM)
    {
       se.chalmers.cs.gf.GFCC.Absyn.AM _am = (se.chalmers.cs.gf.GFCC.Absyn.AM) foo;
       render("AM");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Term foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.R)
    {
       se.chalmers.cs.gf.GFCC.Absyn.R _r = (se.chalmers.cs.gf.GFCC.Absyn.R) foo;
       render("(");
       render("R");
       render("[");
       sh(_r.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.P)
    {
       se.chalmers.cs.gf.GFCC.Absyn.P _p = (se.chalmers.cs.gf.GFCC.Absyn.P) foo;
       render("(");
       render("P");
       sh(_p.term_1);
       sh(_p.term_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.S)
    {
       se.chalmers.cs.gf.GFCC.Absyn.S _s = (se.chalmers.cs.gf.GFCC.Absyn.S) foo;
       render("(");
       render("S");
       render("[");
       sh(_s.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.K)
    {
       se.chalmers.cs.gf.GFCC.Absyn.K _k = (se.chalmers.cs.gf.GFCC.Absyn.K) foo;
       render("(");
       render("K");
       sh(_k.tokn_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.V)
    {
       se.chalmers.cs.gf.GFCC.Absyn.V _v = (se.chalmers.cs.gf.GFCC.Absyn.V) foo;
       render("(");
       render("V");
       sh(_v.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.C)
    {
       se.chalmers.cs.gf.GFCC.Absyn.C _c = (se.chalmers.cs.gf.GFCC.Absyn.C) foo;
       render("(");
       render("C");
       sh(_c.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.F)
    {
       se.chalmers.cs.gf.GFCC.Absyn.F _f = (se.chalmers.cs.gf.GFCC.Absyn.F) foo;
       render("(");
       render("F");
       sh(_f.cid_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.FV)
    {
       se.chalmers.cs.gf.GFCC.Absyn.FV _fv = (se.chalmers.cs.gf.GFCC.Absyn.FV) foo;
       render("(");
       render("FV");
       render("[");
       sh(_fv.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.W)
    {
       se.chalmers.cs.gf.GFCC.Absyn.W _w = (se.chalmers.cs.gf.GFCC.Absyn.W) foo;
       render("(");
       render("W");
       sh(_w.string_);
       sh(_w.term_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.RP)
    {
       se.chalmers.cs.gf.GFCC.Absyn.RP _rp = (se.chalmers.cs.gf.GFCC.Absyn.RP) foo;
       render("(");
       render("RP");
       sh(_rp.term_1);
       sh(_rp.term_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.TM)
    {
       se.chalmers.cs.gf.GFCC.Absyn.TM _tm = (se.chalmers.cs.gf.GFCC.Absyn.TM) foo;
       render("TM");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.L)
    {
       se.chalmers.cs.gf.GFCC.Absyn.L _l = (se.chalmers.cs.gf.GFCC.Absyn.L) foo;
       render("(");
       render("L");
       sh(_l.cid_);
       sh(_l.term_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.BV)
    {
       se.chalmers.cs.gf.GFCC.Absyn.BV _bv = (se.chalmers.cs.gf.GFCC.Absyn.BV) foo;
       render("(");
       render("BV");
       sh(_bv.cid_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Tokn foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.KS)
    {
       se.chalmers.cs.gf.GFCC.Absyn.KS _ks = (se.chalmers.cs.gf.GFCC.Absyn.KS) foo;
       render("(");
       render("KS");
       sh(_ks.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.KP)
    {
       se.chalmers.cs.gf.GFCC.Absyn.KP _kp = (se.chalmers.cs.gf.GFCC.Absyn.KP) foo;
       render("(");
       render("KP");
       render("[");
       sh(_kp.liststring_);
       render("]");
       render("[");
       sh(_kp.listvariant_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.Variant foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFCC.Absyn.Var)
    {
       se.chalmers.cs.gf.GFCC.Absyn.Var _var = (se.chalmers.cs.gf.GFCC.Absyn.Var) foo;
       render("(");
       render("Var");
       render("[");
       sh(_var.liststring_1);
       render("]");
       render("[");
       sh(_var.liststring_2);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListConcrete foo)
  {
     for (java.util.Iterator<Concrete> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListAbsDef foo)
  {
     for (java.util.Iterator<AbsDef> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListCncDef foo)
  {
     for (java.util.Iterator<CncDef> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListCId foo)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListTerm foo)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListExp foo)
  {
     for (java.util.Iterator<Exp> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListString foo)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFCC.Absyn.ListVariant foo)
  {
     for (java.util.Iterator<Variant> it = foo.iterator(); it.hasNext();)
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

