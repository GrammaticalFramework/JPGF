package se.chalmers.cs.gf.GFC;
import se.chalmers.cs.gf.GFC.Absyn.*;

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
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Canon foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Canon foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Line foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Line foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Module foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Module foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ModType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ModType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListModule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListModule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Extend foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Extend foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Open foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Open foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Flag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Flag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Def foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Def foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ParDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ParDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Status foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Status foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.CIdent foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.CIdent foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Exp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Sort foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Sort foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Equation foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Equation foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.APatt foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.APatt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListDecl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListDecl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListAPatt foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListAPatt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListEquation foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListEquation foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Atom foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Atom foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Decl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Decl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.CType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.CType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Labelling foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Labelling foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Term foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Term foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Tokn foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Tokn foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Assign foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Assign foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Case foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Case foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Variant foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Variant foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Label foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Label foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ArgVar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ArgVar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.Patt foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.Patt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.PattAssign foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.PattAssign foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListFlag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListFlag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListParDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListParDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListCType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListCType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListCIdent foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListCIdent foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListAssign foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListAssign foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListArgVar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListArgVar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListLabelling foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListLabelling foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListCase foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListCase foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListString foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListString foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListVariant foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListVariant foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListPattAssign foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListPattAssign foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListPatt foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListPatt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.GFC.Absyn.ListIdent foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.GFC.Absyn.ListIdent foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Canon foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MGr)
    {
       se.chalmers.cs.gf.GFC.Absyn.MGr _mgr = (se.chalmers.cs.gf.GFC.Absyn.MGr) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("grammar");
       pp(_mgr.listident_, 0);
       render("of");
       pp(_mgr.ident_, 0);
       render(";");
       pp(_mgr.listmodule_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Gr)
    {
       se.chalmers.cs.gf.GFC.Absyn.Gr _gr = (se.chalmers.cs.gf.GFC.Absyn.Gr) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_gr.listmodule_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Line foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LMulti)
    {
       se.chalmers.cs.gf.GFC.Absyn.LMulti _lmulti = (se.chalmers.cs.gf.GFC.Absyn.LMulti) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("grammar");
       pp(_lmulti.listident_, 0);
       render("of");
       pp(_lmulti.ident_, 0);
       render(";");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LHeader)
    {
       se.chalmers.cs.gf.GFC.Absyn.LHeader _lheader = (se.chalmers.cs.gf.GFC.Absyn.LHeader) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_lheader.modtype_, 0);
       render("=");
       pp(_lheader.extend_, 0);
       pp(_lheader.open_, 0);
       render("{");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LFlag)
    {
       se.chalmers.cs.gf.GFC.Absyn.LFlag _lflag = (se.chalmers.cs.gf.GFC.Absyn.LFlag) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_lflag.flag_, 0);
       render(";");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LDef)
    {
       se.chalmers.cs.gf.GFC.Absyn.LDef _ldef = (se.chalmers.cs.gf.GFC.Absyn.LDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ldef.def_, 0);
       render(";");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LEnd)
    {
       se.chalmers.cs.gf.GFC.Absyn.LEnd _lend = (se.chalmers.cs.gf.GFC.Absyn.LEnd) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Module foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Mod)
    {
       se.chalmers.cs.gf.GFC.Absyn.Mod _mod = (se.chalmers.cs.gf.GFC.Absyn.Mod) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_mod.modtype_, 0);
       render("=");
       pp(_mod.extend_, 0);
       pp(_mod.open_, 0);
       render("{");
       pp(_mod.listflag_, 0);
       pp(_mod.listdef_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ModType foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTAbs)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTAbs _mtabs = (se.chalmers.cs.gf.GFC.Absyn.MTAbs) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("abstract");
       pp(_mtabs.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTCnc)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTCnc _mtcnc = (se.chalmers.cs.gf.GFC.Absyn.MTCnc) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("concrete");
       pp(_mtcnc.ident_1, 0);
       render("of");
       pp(_mtcnc.ident_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTRes)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTRes _mtres = (se.chalmers.cs.gf.GFC.Absyn.MTRes) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("resource");
       pp(_mtres.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTTrans)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTTrans _mttrans = (se.chalmers.cs.gf.GFC.Absyn.MTTrans) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("transfer");
       pp(_mttrans.ident_1, 0);
       render(":");
       pp(_mttrans.ident_2, 0);
       render("->");
       pp(_mttrans.ident_3, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListModule foo, int _i_)
  {
     for (java.util.Iterator<Module> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Extend foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Ext)
    {
       se.chalmers.cs.gf.GFC.Absyn.Ext _ext = (se.chalmers.cs.gf.GFC.Absyn.Ext) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ext.listident_, 0);
       render("**");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NoExt)
    {
       se.chalmers.cs.gf.GFC.Absyn.NoExt _noext = (se.chalmers.cs.gf.GFC.Absyn.NoExt) foo;
       if (_i_ > 0) render(_L_PAREN);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Open foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Opens)
    {
       se.chalmers.cs.gf.GFC.Absyn.Opens _opens = (se.chalmers.cs.gf.GFC.Absyn.Opens) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("open");
       pp(_opens.listident_, 0);
       render("in");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NoOpens)
    {
       se.chalmers.cs.gf.GFC.Absyn.NoOpens _noopens = (se.chalmers.cs.gf.GFC.Absyn.NoOpens) foo;
       if (_i_ > 0) render(_L_PAREN);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Flag foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Flg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Flg _flg = (se.chalmers.cs.gf.GFC.Absyn.Flg) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("flags");
       pp(_flg.ident_1, 0);
       render("=");
       pp(_flg.ident_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Def foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDCat)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDCat _absdcat = (se.chalmers.cs.gf.GFC.Absyn.AbsDCat) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("cat");
       pp(_absdcat.ident_, 0);
       render("[");
       pp(_absdcat.listdecl_, 0);
       render("]");
       render("=");
       pp(_absdcat.listcident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDFun)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDFun _absdfun = (se.chalmers.cs.gf.GFC.Absyn.AbsDFun) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("fun");
       pp(_absdfun.ident_, 0);
       render(":");
       pp(_absdfun.exp_1, 0);
       render("=");
       pp(_absdfun.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDTrans)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDTrans _absdtrans = (se.chalmers.cs.gf.GFC.Absyn.AbsDTrans) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("transfer");
       pp(_absdtrans.ident_, 0);
       render("=");
       pp(_absdtrans.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ResDPar)
    {
       se.chalmers.cs.gf.GFC.Absyn.ResDPar _resdpar = (se.chalmers.cs.gf.GFC.Absyn.ResDPar) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("param");
       pp(_resdpar.ident_, 0);
       render("=");
       pp(_resdpar.listpardef_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ResDOper)
    {
       se.chalmers.cs.gf.GFC.Absyn.ResDOper _resdoper = (se.chalmers.cs.gf.GFC.Absyn.ResDOper) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("oper");
       pp(_resdoper.ident_, 0);
       render(":");
       pp(_resdoper.ctype_, 0);
       render("=");
       pp(_resdoper.term_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CncDCat)
    {
       se.chalmers.cs.gf.GFC.Absyn.CncDCat _cncdcat = (se.chalmers.cs.gf.GFC.Absyn.CncDCat) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("lincat");
       pp(_cncdcat.ident_, 0);
       render("=");
       pp(_cncdcat.ctype_, 0);
       render("=");
       pp(_cncdcat.term_1, 0);
       render(";");
       pp(_cncdcat.term_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CncDFun)
    {
       se.chalmers.cs.gf.GFC.Absyn.CncDFun _cncdfun = (se.chalmers.cs.gf.GFC.Absyn.CncDFun) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("lin");
       pp(_cncdfun.ident_, 0);
       render(":");
       pp(_cncdfun.cident_, 0);
       render("=");
       render("\\");
       pp(_cncdfun.listargvar_, 0);
       render("->");
       pp(_cncdfun.term_1, 0);
       render(";");
       pp(_cncdfun.term_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AnyDInd)
    {
       se.chalmers.cs.gf.GFC.Absyn.AnyDInd _anydind = (se.chalmers.cs.gf.GFC.Absyn.AnyDInd) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_anydind.ident_1, 0);
       pp(_anydind.status_, 0);
       render("in");
       pp(_anydind.ident_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ParDef foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ParD)
    {
       se.chalmers.cs.gf.GFC.Absyn.ParD _pard = (se.chalmers.cs.gf.GFC.Absyn.ParD) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pard.ident_, 0);
       pp(_pard.listctype_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Status foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Canon)
    {
       se.chalmers.cs.gf.GFC.Absyn.Canon _canon = (se.chalmers.cs.gf.GFC.Absyn.Canon) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("data");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NonCan)
    {
       se.chalmers.cs.gf.GFC.Absyn.NonCan _noncan = (se.chalmers.cs.gf.GFC.Absyn.NonCan) foo;
       if (_i_ > 0) render(_L_PAREN);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.CIdent foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CIQ)
    {
       se.chalmers.cs.gf.GFC.Absyn.CIQ _ciq = (se.chalmers.cs.gf.GFC.Absyn.CIQ) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ciq.ident_1, 0);
       render(".");
       pp(_ciq.ident_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EApp)
    {
       se.chalmers.cs.gf.GFC.Absyn.EApp _eapp = (se.chalmers.cs.gf.GFC.Absyn.EApp) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_eapp.exp_1, 1);
       pp(_eapp.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EProd)
    {
       se.chalmers.cs.gf.GFC.Absyn.EProd _eprod = (se.chalmers.cs.gf.GFC.Absyn.EProd) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_eprod.ident_, 0);
       render(":");
       pp(_eprod.exp_1, 0);
       render(")");
       render("->");
       pp(_eprod.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EAbs)
    {
       se.chalmers.cs.gf.GFC.Absyn.EAbs _eabs = (se.chalmers.cs.gf.GFC.Absyn.EAbs) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("\\");
       pp(_eabs.ident_, 0);
       render("->");
       pp(_eabs.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EEq)
    {
       se.chalmers.cs.gf.GFC.Absyn.EEq _eeq = (se.chalmers.cs.gf.GFC.Absyn.EEq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("{");
       pp(_eeq.listequation_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EAtom)
    {
       se.chalmers.cs.gf.GFC.Absyn.EAtom _eatom = (se.chalmers.cs.gf.GFC.Absyn.EAtom) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_eatom.atom_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EData)
    {
       se.chalmers.cs.gf.GFC.Absyn.EData _edata = (se.chalmers.cs.gf.GFC.Absyn.EData) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("data");
       if (_i_ > 2) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Sort foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.SType)
    {
       se.chalmers.cs.gf.GFC.Absyn.SType _stype = (se.chalmers.cs.gf.GFC.Absyn.SType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Type");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Equation foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Equ)
    {
       se.chalmers.cs.gf.GFC.Absyn.Equ _equ = (se.chalmers.cs.gf.GFC.Absyn.Equ) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_equ.listapatt_, 0);
       render("->");
       pp(_equ.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.APatt foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APC)
    {
       se.chalmers.cs.gf.GFC.Absyn.APC _apc = (se.chalmers.cs.gf.GFC.Absyn.APC) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_apc.cident_, 0);
       pp(_apc.listapatt_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APV)
    {
       se.chalmers.cs.gf.GFC.Absyn.APV _apv = (se.chalmers.cs.gf.GFC.Absyn.APV) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_apv.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APS)
    {
       se.chalmers.cs.gf.GFC.Absyn.APS _aps = (se.chalmers.cs.gf.GFC.Absyn.APS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_aps.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.API)
    {
       se.chalmers.cs.gf.GFC.Absyn.API _api = (se.chalmers.cs.gf.GFC.Absyn.API) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_api.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APF)
    {
       se.chalmers.cs.gf.GFC.Absyn.APF _apf = (se.chalmers.cs.gf.GFC.Absyn.APF) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_apf.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APW)
    {
       se.chalmers.cs.gf.GFC.Absyn.APW _apw = (se.chalmers.cs.gf.GFC.Absyn.APW) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListDecl foo, int _i_)
  {
     for (java.util.Iterator<Decl> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListAPatt foo, int _i_)
  {
     for (java.util.Iterator<APatt> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListEquation foo, int _i_)
  {
     for (java.util.Iterator<Equation> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Atom foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AC)
    {
       se.chalmers.cs.gf.GFC.Absyn.AC _ac = (se.chalmers.cs.gf.GFC.Absyn.AC) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ac.cident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AD)
    {
       se.chalmers.cs.gf.GFC.Absyn.AD _ad = (se.chalmers.cs.gf.GFC.Absyn.AD) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("<");
       pp(_ad.cident_, 0);
       render(">");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AV)
    {
       se.chalmers.cs.gf.GFC.Absyn.AV _av = (se.chalmers.cs.gf.GFC.Absyn.AV) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("$");
       pp(_av.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AM)
    {
       se.chalmers.cs.gf.GFC.Absyn.AM _am = (se.chalmers.cs.gf.GFC.Absyn.AM) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("?");
       pp(_am.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AS)
    {
       se.chalmers.cs.gf.GFC.Absyn.AS _as = (se.chalmers.cs.gf.GFC.Absyn.AS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_as.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AI)
    {
       se.chalmers.cs.gf.GFC.Absyn.AI _ai = (se.chalmers.cs.gf.GFC.Absyn.AI) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ai.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AT)
    {
       se.chalmers.cs.gf.GFC.Absyn.AT _at = (se.chalmers.cs.gf.GFC.Absyn.AT) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_at.sort_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Decl foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Decl)
    {
       se.chalmers.cs.gf.GFC.Absyn.Decl _decl = (se.chalmers.cs.gf.GFC.Absyn.Decl) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_decl.ident_, 0);
       render(":");
       pp(_decl.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.CType foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.RecType)
    {
       se.chalmers.cs.gf.GFC.Absyn.RecType _rectype = (se.chalmers.cs.gf.GFC.Absyn.RecType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("{");
       pp(_rectype.listlabelling_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Table)
    {
       se.chalmers.cs.gf.GFC.Absyn.Table _table = (se.chalmers.cs.gf.GFC.Absyn.Table) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_table.ctype_1, 0);
       render("=>");
       pp(_table.ctype_2, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Cn)
    {
       se.chalmers.cs.gf.GFC.Absyn.Cn _cn = (se.chalmers.cs.gf.GFC.Absyn.Cn) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_cn.cident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.TStr)
    {
       se.chalmers.cs.gf.GFC.Absyn.TStr _tstr = (se.chalmers.cs.gf.GFC.Absyn.TStr) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Str");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.TInts)
    {
       se.chalmers.cs.gf.GFC.Absyn.TInts _tints = (se.chalmers.cs.gf.GFC.Absyn.TInts) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Ints");
       pp(_tints.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Labelling foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Lbg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Lbg _lbg = (se.chalmers.cs.gf.GFC.Absyn.Lbg) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_lbg.label_, 0);
       render(":");
       pp(_lbg.ctype_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Term foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Arg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Arg _arg = (se.chalmers.cs.gf.GFC.Absyn.Arg) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_arg.argvar_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.I)
    {
       se.chalmers.cs.gf.GFC.Absyn.I _i = (se.chalmers.cs.gf.GFC.Absyn.I) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_i.cident_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Par)
    {
       se.chalmers.cs.gf.GFC.Absyn.Par _par = (se.chalmers.cs.gf.GFC.Absyn.Par) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("<");
       pp(_par.cident_, 0);
       pp(_par.listterm_, 2);
       render(">");
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LI)
    {
       se.chalmers.cs.gf.GFC.Absyn.LI _li = (se.chalmers.cs.gf.GFC.Absyn.LI) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("$");
       pp(_li.ident_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.R)
    {
       se.chalmers.cs.gf.GFC.Absyn.R _r = (se.chalmers.cs.gf.GFC.Absyn.R) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("{");
       pp(_r.listassign_, 0);
       render("}");
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EInt)
    {
       se.chalmers.cs.gf.GFC.Absyn.EInt _eint = (se.chalmers.cs.gf.GFC.Absyn.EInt) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_eint.integer_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EFloat)
    {
       se.chalmers.cs.gf.GFC.Absyn.EFloat _efloat = (se.chalmers.cs.gf.GFC.Absyn.EFloat) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_efloat.double_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.K)
    {
       se.chalmers.cs.gf.GFC.Absyn.K _k = (se.chalmers.cs.gf.GFC.Absyn.K) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_k.tokn_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.E)
    {
       se.chalmers.cs.gf.GFC.Absyn.E _e = (se.chalmers.cs.gf.GFC.Absyn.E) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("[");
       render("]");
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.P)
    {
       se.chalmers.cs.gf.GFC.Absyn.P _p = (se.chalmers.cs.gf.GFC.Absyn.P) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_p.term_, 2);
       render(".");
       pp(_p.label_, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.T)
    {
       se.chalmers.cs.gf.GFC.Absyn.T _t = (se.chalmers.cs.gf.GFC.Absyn.T) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("table");
       pp(_t.ctype_, 0);
       render("{");
       pp(_t.listcase_, 0);
       render("}");
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.V)
    {
       se.chalmers.cs.gf.GFC.Absyn.V _v = (se.chalmers.cs.gf.GFC.Absyn.V) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("table");
       pp(_v.ctype_, 0);
       render("[");
       pp(_v.listterm_, 2);
       render("]");
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.S)
    {
       se.chalmers.cs.gf.GFC.Absyn.S _s = (se.chalmers.cs.gf.GFC.Absyn.S) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_s.term_1, 1);
       render("!");
       pp(_s.term_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.FV)
    {
       se.chalmers.cs.gf.GFC.Absyn.FV _fv = (se.chalmers.cs.gf.GFC.Absyn.FV) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("variants");
       render("{");
       pp(_fv.listterm_, 2);
       render("}");
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.C)
    {
       se.chalmers.cs.gf.GFC.Absyn.C _c = (se.chalmers.cs.gf.GFC.Absyn.C) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_c.term_1, 0);
       render("++");
       pp(_c.term_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Tokn foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KS)
    {
       se.chalmers.cs.gf.GFC.Absyn.KS _ks = (se.chalmers.cs.gf.GFC.Absyn.KS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_ks.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KP)
    {
       se.chalmers.cs.gf.GFC.Absyn.KP _kp = (se.chalmers.cs.gf.GFC.Absyn.KP) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       render("pre");
       pp(_kp.liststring_, 0);
       render("{");
       pp(_kp.listvariant_, 0);
       render("}");
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KM)
    {
       se.chalmers.cs.gf.GFC.Absyn.KM _km = (se.chalmers.cs.gf.GFC.Absyn.KM) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_km.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Assign foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Ass)
    {
       se.chalmers.cs.gf.GFC.Absyn.Ass _ass = (se.chalmers.cs.gf.GFC.Absyn.Ass) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ass.label_, 0);
       render("=");
       pp(_ass.term_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Case foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Cas)
    {
       se.chalmers.cs.gf.GFC.Absyn.Cas _cas = (se.chalmers.cs.gf.GFC.Absyn.Cas) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_cas.listpatt_, 0);
       render("=>");
       pp(_cas.term_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Variant foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Var)
    {
       se.chalmers.cs.gf.GFC.Absyn.Var _var = (se.chalmers.cs.gf.GFC.Absyn.Var) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_var.liststring_1, 0);
       render("/");
       pp(_var.liststring_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Label foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.L)
    {
       se.chalmers.cs.gf.GFC.Absyn.L _l = (se.chalmers.cs.gf.GFC.Absyn.L) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_l.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LV)
    {
       se.chalmers.cs.gf.GFC.Absyn.LV _lv = (se.chalmers.cs.gf.GFC.Absyn.LV) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("$");
       pp(_lv.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ArgVar foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.A)
    {
       se.chalmers.cs.gf.GFC.Absyn.A _a = (se.chalmers.cs.gf.GFC.Absyn.A) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_a.ident_, 0);
       render("@");
       pp(_a.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AB)
    {
       se.chalmers.cs.gf.GFC.Absyn.AB _ab = (se.chalmers.cs.gf.GFC.Absyn.AB) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ab.ident_, 0);
       render("+");
       pp(_ab.integer_1, 0);
       render("@");
       pp(_ab.integer_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.Patt foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PC)
    {
       se.chalmers.cs.gf.GFC.Absyn.PC _pc = (se.chalmers.cs.gf.GFC.Absyn.PC) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_pc.cident_, 0);
       pp(_pc.listpatt_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PV)
    {
       se.chalmers.cs.gf.GFC.Absyn.PV _pv = (se.chalmers.cs.gf.GFC.Absyn.PV) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pv.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PW)
    {
       se.chalmers.cs.gf.GFC.Absyn.PW _pw = (se.chalmers.cs.gf.GFC.Absyn.PW) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PR)
    {
       se.chalmers.cs.gf.GFC.Absyn.PR _pr = (se.chalmers.cs.gf.GFC.Absyn.PR) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("{");
       pp(_pr.listpattassign_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PI)
    {
       se.chalmers.cs.gf.GFC.Absyn.PI _pi = (se.chalmers.cs.gf.GFC.Absyn.PI) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pi.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PF)
    {
       se.chalmers.cs.gf.GFC.Absyn.PF _pf = (se.chalmers.cs.gf.GFC.Absyn.PF) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pf.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.PattAssign foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PAss)
    {
       se.chalmers.cs.gf.GFC.Absyn.PAss _pass = (se.chalmers.cs.gf.GFC.Absyn.PAss) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pass.label_, 0);
       render("=");
       pp(_pass.patt_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListFlag foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListDef foo, int _i_)
  {
     for (java.util.Iterator<Def> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render(";");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListParDef foo, int _i_)
  {
     for (java.util.Iterator<ParDef> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("|");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListCType foo, int _i_)
  {
     for (java.util.Iterator<CType> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListCIdent foo, int _i_)
  {
     for (java.util.Iterator<CIdent> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListAssign foo, int _i_)
  {
     for (java.util.Iterator<Assign> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListArgVar foo, int _i_)
  {
     for (java.util.Iterator<ArgVar> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListLabelling foo, int _i_)
  {
     for (java.util.Iterator<Labelling> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListCase foo, int _i_)
  {
     for (java.util.Iterator<Case> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListTerm foo, int _i_)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListString foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListVariant foo, int _i_)
  {
     for (java.util.Iterator<Variant> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListPattAssign foo, int _i_)
  {
     for (java.util.Iterator<PattAssign> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListPatt foo, int _i_)
  {
     for (java.util.Iterator<Patt> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.GFC.Absyn.ListIdent foo, int _i_)
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


  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Canon foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MGr)
    {
       se.chalmers.cs.gf.GFC.Absyn.MGr _mgr = (se.chalmers.cs.gf.GFC.Absyn.MGr) foo;
       render("(");
       render("MGr");
       render("[");
       sh(_mgr.listident_);
       render("]");
       sh(_mgr.ident_);
       render("[");
       sh(_mgr.listmodule_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Gr)
    {
       se.chalmers.cs.gf.GFC.Absyn.Gr _gr = (se.chalmers.cs.gf.GFC.Absyn.Gr) foo;
       render("(");
       render("Gr");
       render("[");
       sh(_gr.listmodule_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Line foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LMulti)
    {
       se.chalmers.cs.gf.GFC.Absyn.LMulti _lmulti = (se.chalmers.cs.gf.GFC.Absyn.LMulti) foo;
       render("(");
       render("LMulti");
       render("[");
       sh(_lmulti.listident_);
       render("]");
       sh(_lmulti.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LHeader)
    {
       se.chalmers.cs.gf.GFC.Absyn.LHeader _lheader = (se.chalmers.cs.gf.GFC.Absyn.LHeader) foo;
       render("(");
       render("LHeader");
       sh(_lheader.modtype_);
       sh(_lheader.extend_);
       sh(_lheader.open_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LFlag)
    {
       se.chalmers.cs.gf.GFC.Absyn.LFlag _lflag = (se.chalmers.cs.gf.GFC.Absyn.LFlag) foo;
       render("(");
       render("LFlag");
       sh(_lflag.flag_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LDef)
    {
       se.chalmers.cs.gf.GFC.Absyn.LDef _ldef = (se.chalmers.cs.gf.GFC.Absyn.LDef) foo;
       render("(");
       render("LDef");
       sh(_ldef.def_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LEnd)
    {
       se.chalmers.cs.gf.GFC.Absyn.LEnd _lend = (se.chalmers.cs.gf.GFC.Absyn.LEnd) foo;
       render("LEnd");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Module foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Mod)
    {
       se.chalmers.cs.gf.GFC.Absyn.Mod _mod = (se.chalmers.cs.gf.GFC.Absyn.Mod) foo;
       render("(");
       render("Mod");
       sh(_mod.modtype_);
       sh(_mod.extend_);
       sh(_mod.open_);
       render("[");
       sh(_mod.listflag_);
       render("]");
       render("[");
       sh(_mod.listdef_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ModType foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTAbs)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTAbs _mtabs = (se.chalmers.cs.gf.GFC.Absyn.MTAbs) foo;
       render("(");
       render("MTAbs");
       sh(_mtabs.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTCnc)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTCnc _mtcnc = (se.chalmers.cs.gf.GFC.Absyn.MTCnc) foo;
       render("(");
       render("MTCnc");
       sh(_mtcnc.ident_1);
       sh(_mtcnc.ident_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTRes)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTRes _mtres = (se.chalmers.cs.gf.GFC.Absyn.MTRes) foo;
       render("(");
       render("MTRes");
       sh(_mtres.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.MTTrans)
    {
       se.chalmers.cs.gf.GFC.Absyn.MTTrans _mttrans = (se.chalmers.cs.gf.GFC.Absyn.MTTrans) foo;
       render("(");
       render("MTTrans");
       sh(_mttrans.ident_1);
       sh(_mttrans.ident_2);
       sh(_mttrans.ident_3);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListModule foo)
  {
     for (java.util.Iterator<Module> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Extend foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Ext)
    {
       se.chalmers.cs.gf.GFC.Absyn.Ext _ext = (se.chalmers.cs.gf.GFC.Absyn.Ext) foo;
       render("(");
       render("Ext");
       render("[");
       sh(_ext.listident_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NoExt)
    {
       se.chalmers.cs.gf.GFC.Absyn.NoExt _noext = (se.chalmers.cs.gf.GFC.Absyn.NoExt) foo;
       render("NoExt");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Open foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Opens)
    {
       se.chalmers.cs.gf.GFC.Absyn.Opens _opens = (se.chalmers.cs.gf.GFC.Absyn.Opens) foo;
       render("(");
       render("Opens");
       render("[");
       sh(_opens.listident_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NoOpens)
    {
       se.chalmers.cs.gf.GFC.Absyn.NoOpens _noopens = (se.chalmers.cs.gf.GFC.Absyn.NoOpens) foo;
       render("NoOpens");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Flag foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Flg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Flg _flg = (se.chalmers.cs.gf.GFC.Absyn.Flg) foo;
       render("(");
       render("Flg");
       sh(_flg.ident_1);
       sh(_flg.ident_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Def foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDCat)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDCat _absdcat = (se.chalmers.cs.gf.GFC.Absyn.AbsDCat) foo;
       render("(");
       render("AbsDCat");
       sh(_absdcat.ident_);
       render("[");
       sh(_absdcat.listdecl_);
       render("]");
       render("[");
       sh(_absdcat.listcident_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDFun)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDFun _absdfun = (se.chalmers.cs.gf.GFC.Absyn.AbsDFun) foo;
       render("(");
       render("AbsDFun");
       sh(_absdfun.ident_);
       sh(_absdfun.exp_1);
       sh(_absdfun.exp_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AbsDTrans)
    {
       se.chalmers.cs.gf.GFC.Absyn.AbsDTrans _absdtrans = (se.chalmers.cs.gf.GFC.Absyn.AbsDTrans) foo;
       render("(");
       render("AbsDTrans");
       sh(_absdtrans.ident_);
       sh(_absdtrans.exp_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ResDPar)
    {
       se.chalmers.cs.gf.GFC.Absyn.ResDPar _resdpar = (se.chalmers.cs.gf.GFC.Absyn.ResDPar) foo;
       render("(");
       render("ResDPar");
       sh(_resdpar.ident_);
       render("[");
       sh(_resdpar.listpardef_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ResDOper)
    {
       se.chalmers.cs.gf.GFC.Absyn.ResDOper _resdoper = (se.chalmers.cs.gf.GFC.Absyn.ResDOper) foo;
       render("(");
       render("ResDOper");
       sh(_resdoper.ident_);
       sh(_resdoper.ctype_);
       sh(_resdoper.term_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CncDCat)
    {
       se.chalmers.cs.gf.GFC.Absyn.CncDCat _cncdcat = (se.chalmers.cs.gf.GFC.Absyn.CncDCat) foo;
       render("(");
       render("CncDCat");
       sh(_cncdcat.ident_);
       sh(_cncdcat.ctype_);
       sh(_cncdcat.term_1);
       sh(_cncdcat.term_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CncDFun)
    {
       se.chalmers.cs.gf.GFC.Absyn.CncDFun _cncdfun = (se.chalmers.cs.gf.GFC.Absyn.CncDFun) foo;
       render("(");
       render("CncDFun");
       sh(_cncdfun.ident_);
       sh(_cncdfun.cident_);
       render("[");
       sh(_cncdfun.listargvar_);
       render("]");
       sh(_cncdfun.term_1);
       sh(_cncdfun.term_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AnyDInd)
    {
       se.chalmers.cs.gf.GFC.Absyn.AnyDInd _anydind = (se.chalmers.cs.gf.GFC.Absyn.AnyDInd) foo;
       render("(");
       render("AnyDInd");
       sh(_anydind.ident_1);
       sh(_anydind.status_);
       sh(_anydind.ident_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ParDef foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.ParD)
    {
       se.chalmers.cs.gf.GFC.Absyn.ParD _pard = (se.chalmers.cs.gf.GFC.Absyn.ParD) foo;
       render("(");
       render("ParD");
       sh(_pard.ident_);
       render("[");
       sh(_pard.listctype_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Status foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Canon)
    {
       se.chalmers.cs.gf.GFC.Absyn.Canon _canon = (se.chalmers.cs.gf.GFC.Absyn.Canon) foo;
       render("Canon");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.NonCan)
    {
       se.chalmers.cs.gf.GFC.Absyn.NonCan _noncan = (se.chalmers.cs.gf.GFC.Absyn.NonCan) foo;
       render("NonCan");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.CIdent foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.CIQ)
    {
       se.chalmers.cs.gf.GFC.Absyn.CIQ _ciq = (se.chalmers.cs.gf.GFC.Absyn.CIQ) foo;
       render("(");
       render("CIQ");
       sh(_ciq.ident_1);
       sh(_ciq.ident_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Exp foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EApp)
    {
       se.chalmers.cs.gf.GFC.Absyn.EApp _eapp = (se.chalmers.cs.gf.GFC.Absyn.EApp) foo;
       render("(");
       render("EApp");
       sh(_eapp.exp_1);
       sh(_eapp.exp_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EProd)
    {
       se.chalmers.cs.gf.GFC.Absyn.EProd _eprod = (se.chalmers.cs.gf.GFC.Absyn.EProd) foo;
       render("(");
       render("EProd");
       sh(_eprod.ident_);
       sh(_eprod.exp_1);
       sh(_eprod.exp_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EAbs)
    {
       se.chalmers.cs.gf.GFC.Absyn.EAbs _eabs = (se.chalmers.cs.gf.GFC.Absyn.EAbs) foo;
       render("(");
       render("EAbs");
       sh(_eabs.ident_);
       sh(_eabs.exp_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EEq)
    {
       se.chalmers.cs.gf.GFC.Absyn.EEq _eeq = (se.chalmers.cs.gf.GFC.Absyn.EEq) foo;
       render("(");
       render("EEq");
       render("[");
       sh(_eeq.listequation_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EAtom)
    {
       se.chalmers.cs.gf.GFC.Absyn.EAtom _eatom = (se.chalmers.cs.gf.GFC.Absyn.EAtom) foo;
       render("(");
       render("EAtom");
       sh(_eatom.atom_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EData)
    {
       se.chalmers.cs.gf.GFC.Absyn.EData _edata = (se.chalmers.cs.gf.GFC.Absyn.EData) foo;
       render("EData");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Sort foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.SType)
    {
       se.chalmers.cs.gf.GFC.Absyn.SType _stype = (se.chalmers.cs.gf.GFC.Absyn.SType) foo;
       render("SType");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Equation foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Equ)
    {
       se.chalmers.cs.gf.GFC.Absyn.Equ _equ = (se.chalmers.cs.gf.GFC.Absyn.Equ) foo;
       render("(");
       render("Equ");
       render("[");
       sh(_equ.listapatt_);
       render("]");
       sh(_equ.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.APatt foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APC)
    {
       se.chalmers.cs.gf.GFC.Absyn.APC _apc = (se.chalmers.cs.gf.GFC.Absyn.APC) foo;
       render("(");
       render("APC");
       sh(_apc.cident_);
       render("[");
       sh(_apc.listapatt_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APV)
    {
       se.chalmers.cs.gf.GFC.Absyn.APV _apv = (se.chalmers.cs.gf.GFC.Absyn.APV) foo;
       render("(");
       render("APV");
       sh(_apv.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APS)
    {
       se.chalmers.cs.gf.GFC.Absyn.APS _aps = (se.chalmers.cs.gf.GFC.Absyn.APS) foo;
       render("(");
       render("APS");
       sh(_aps.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.API)
    {
       se.chalmers.cs.gf.GFC.Absyn.API _api = (se.chalmers.cs.gf.GFC.Absyn.API) foo;
       render("(");
       render("API");
       sh(_api.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APF)
    {
       se.chalmers.cs.gf.GFC.Absyn.APF _apf = (se.chalmers.cs.gf.GFC.Absyn.APF) foo;
       render("(");
       render("APF");
       sh(_apf.double_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.APW)
    {
       se.chalmers.cs.gf.GFC.Absyn.APW _apw = (se.chalmers.cs.gf.GFC.Absyn.APW) foo;
       render("APW");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListDecl foo)
  {
     for (java.util.Iterator<Decl> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListAPatt foo)
  {
     for (java.util.Iterator<APatt> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListEquation foo)
  {
     for (java.util.Iterator<Equation> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Atom foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AC)
    {
       se.chalmers.cs.gf.GFC.Absyn.AC _ac = (se.chalmers.cs.gf.GFC.Absyn.AC) foo;
       render("(");
       render("AC");
       sh(_ac.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AD)
    {
       se.chalmers.cs.gf.GFC.Absyn.AD _ad = (se.chalmers.cs.gf.GFC.Absyn.AD) foo;
       render("(");
       render("AD");
       sh(_ad.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AV)
    {
       se.chalmers.cs.gf.GFC.Absyn.AV _av = (se.chalmers.cs.gf.GFC.Absyn.AV) foo;
       render("(");
       render("AV");
       sh(_av.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AM)
    {
       se.chalmers.cs.gf.GFC.Absyn.AM _am = (se.chalmers.cs.gf.GFC.Absyn.AM) foo;
       render("(");
       render("AM");
       sh(_am.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AS)
    {
       se.chalmers.cs.gf.GFC.Absyn.AS _as = (se.chalmers.cs.gf.GFC.Absyn.AS) foo;
       render("(");
       render("AS");
       sh(_as.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AI)
    {
       se.chalmers.cs.gf.GFC.Absyn.AI _ai = (se.chalmers.cs.gf.GFC.Absyn.AI) foo;
       render("(");
       render("AI");
       sh(_ai.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AT)
    {
       se.chalmers.cs.gf.GFC.Absyn.AT _at = (se.chalmers.cs.gf.GFC.Absyn.AT) foo;
       render("(");
       render("AT");
       sh(_at.sort_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Decl foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Decl)
    {
       se.chalmers.cs.gf.GFC.Absyn.Decl _decl = (se.chalmers.cs.gf.GFC.Absyn.Decl) foo;
       render("(");
       render("Decl");
       sh(_decl.ident_);
       sh(_decl.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.CType foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.RecType)
    {
       se.chalmers.cs.gf.GFC.Absyn.RecType _rectype = (se.chalmers.cs.gf.GFC.Absyn.RecType) foo;
       render("(");
       render("RecType");
       render("[");
       sh(_rectype.listlabelling_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Table)
    {
       se.chalmers.cs.gf.GFC.Absyn.Table _table = (se.chalmers.cs.gf.GFC.Absyn.Table) foo;
       render("(");
       render("Table");
       sh(_table.ctype_1);
       sh(_table.ctype_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Cn)
    {
       se.chalmers.cs.gf.GFC.Absyn.Cn _cn = (se.chalmers.cs.gf.GFC.Absyn.Cn) foo;
       render("(");
       render("Cn");
       sh(_cn.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.TStr)
    {
       se.chalmers.cs.gf.GFC.Absyn.TStr _tstr = (se.chalmers.cs.gf.GFC.Absyn.TStr) foo;
       render("TStr");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.TInts)
    {
       se.chalmers.cs.gf.GFC.Absyn.TInts _tints = (se.chalmers.cs.gf.GFC.Absyn.TInts) foo;
       render("(");
       render("TInts");
       sh(_tints.integer_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Labelling foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Lbg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Lbg _lbg = (se.chalmers.cs.gf.GFC.Absyn.Lbg) foo;
       render("(");
       render("Lbg");
       sh(_lbg.label_);
       sh(_lbg.ctype_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Term foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Arg)
    {
       se.chalmers.cs.gf.GFC.Absyn.Arg _arg = (se.chalmers.cs.gf.GFC.Absyn.Arg) foo;
       render("(");
       render("Arg");
       sh(_arg.argvar_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.I)
    {
       se.chalmers.cs.gf.GFC.Absyn.I _i = (se.chalmers.cs.gf.GFC.Absyn.I) foo;
       render("(");
       render("I");
       sh(_i.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Par)
    {
       se.chalmers.cs.gf.GFC.Absyn.Par _par = (se.chalmers.cs.gf.GFC.Absyn.Par) foo;
       render("(");
       render("Par");
       sh(_par.cident_);
       render("[");
       sh(_par.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LI)
    {
       se.chalmers.cs.gf.GFC.Absyn.LI _li = (se.chalmers.cs.gf.GFC.Absyn.LI) foo;
       render("(");
       render("LI");
       sh(_li.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.R)
    {
       se.chalmers.cs.gf.GFC.Absyn.R _r = (se.chalmers.cs.gf.GFC.Absyn.R) foo;
       render("(");
       render("R");
       render("[");
       sh(_r.listassign_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EInt)
    {
       se.chalmers.cs.gf.GFC.Absyn.EInt _eint = (se.chalmers.cs.gf.GFC.Absyn.EInt) foo;
       render("(");
       render("EInt");
       sh(_eint.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.EFloat)
    {
       se.chalmers.cs.gf.GFC.Absyn.EFloat _efloat = (se.chalmers.cs.gf.GFC.Absyn.EFloat) foo;
       render("(");
       render("EFloat");
       sh(_efloat.double_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.K)
    {
       se.chalmers.cs.gf.GFC.Absyn.K _k = (se.chalmers.cs.gf.GFC.Absyn.K) foo;
       render("(");
       render("K");
       sh(_k.tokn_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.E)
    {
       se.chalmers.cs.gf.GFC.Absyn.E _e = (se.chalmers.cs.gf.GFC.Absyn.E) foo;
       render("E");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.P)
    {
       se.chalmers.cs.gf.GFC.Absyn.P _p = (se.chalmers.cs.gf.GFC.Absyn.P) foo;
       render("(");
       render("P");
       sh(_p.term_);
       sh(_p.label_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.T)
    {
       se.chalmers.cs.gf.GFC.Absyn.T _t = (se.chalmers.cs.gf.GFC.Absyn.T) foo;
       render("(");
       render("T");
       sh(_t.ctype_);
       render("[");
       sh(_t.listcase_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.V)
    {
       se.chalmers.cs.gf.GFC.Absyn.V _v = (se.chalmers.cs.gf.GFC.Absyn.V) foo;
       render("(");
       render("V");
       sh(_v.ctype_);
       render("[");
       sh(_v.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.S)
    {
       se.chalmers.cs.gf.GFC.Absyn.S _s = (se.chalmers.cs.gf.GFC.Absyn.S) foo;
       render("(");
       render("S");
       sh(_s.term_1);
       sh(_s.term_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.FV)
    {
       se.chalmers.cs.gf.GFC.Absyn.FV _fv = (se.chalmers.cs.gf.GFC.Absyn.FV) foo;
       render("(");
       render("FV");
       render("[");
       sh(_fv.listterm_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.C)
    {
       se.chalmers.cs.gf.GFC.Absyn.C _c = (se.chalmers.cs.gf.GFC.Absyn.C) foo;
       render("(");
       render("C");
       sh(_c.term_1);
       sh(_c.term_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Tokn foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KS)
    {
       se.chalmers.cs.gf.GFC.Absyn.KS _ks = (se.chalmers.cs.gf.GFC.Absyn.KS) foo;
       render("(");
       render("KS");
       sh(_ks.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KP)
    {
       se.chalmers.cs.gf.GFC.Absyn.KP _kp = (se.chalmers.cs.gf.GFC.Absyn.KP) foo;
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
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.KM)
    {
       se.chalmers.cs.gf.GFC.Absyn.KM _km = (se.chalmers.cs.gf.GFC.Absyn.KM) foo;
       render("(");
       render("KM");
       sh(_km.string_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Assign foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Ass)
    {
       se.chalmers.cs.gf.GFC.Absyn.Ass _ass = (se.chalmers.cs.gf.GFC.Absyn.Ass) foo;
       render("(");
       render("Ass");
       sh(_ass.label_);
       sh(_ass.term_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Case foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Cas)
    {
       se.chalmers.cs.gf.GFC.Absyn.Cas _cas = (se.chalmers.cs.gf.GFC.Absyn.Cas) foo;
       render("(");
       render("Cas");
       render("[");
       sh(_cas.listpatt_);
       render("]");
       sh(_cas.term_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Variant foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.Var)
    {
       se.chalmers.cs.gf.GFC.Absyn.Var _var = (se.chalmers.cs.gf.GFC.Absyn.Var) foo;
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

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Label foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.L)
    {
       se.chalmers.cs.gf.GFC.Absyn.L _l = (se.chalmers.cs.gf.GFC.Absyn.L) foo;
       render("(");
       render("L");
       sh(_l.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.LV)
    {
       se.chalmers.cs.gf.GFC.Absyn.LV _lv = (se.chalmers.cs.gf.GFC.Absyn.LV) foo;
       render("(");
       render("LV");
       sh(_lv.integer_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ArgVar foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.A)
    {
       se.chalmers.cs.gf.GFC.Absyn.A _a = (se.chalmers.cs.gf.GFC.Absyn.A) foo;
       render("(");
       render("A");
       sh(_a.ident_);
       sh(_a.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.AB)
    {
       se.chalmers.cs.gf.GFC.Absyn.AB _ab = (se.chalmers.cs.gf.GFC.Absyn.AB) foo;
       render("(");
       render("AB");
       sh(_ab.ident_);
       sh(_ab.integer_1);
       sh(_ab.integer_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.Patt foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PC)
    {
       se.chalmers.cs.gf.GFC.Absyn.PC _pc = (se.chalmers.cs.gf.GFC.Absyn.PC) foo;
       render("(");
       render("PC");
       sh(_pc.cident_);
       render("[");
       sh(_pc.listpatt_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PV)
    {
       se.chalmers.cs.gf.GFC.Absyn.PV _pv = (se.chalmers.cs.gf.GFC.Absyn.PV) foo;
       render("(");
       render("PV");
       sh(_pv.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PW)
    {
       se.chalmers.cs.gf.GFC.Absyn.PW _pw = (se.chalmers.cs.gf.GFC.Absyn.PW) foo;
       render("PW");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PR)
    {
       se.chalmers.cs.gf.GFC.Absyn.PR _pr = (se.chalmers.cs.gf.GFC.Absyn.PR) foo;
       render("(");
       render("PR");
       render("[");
       sh(_pr.listpattassign_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PI)
    {
       se.chalmers.cs.gf.GFC.Absyn.PI _pi = (se.chalmers.cs.gf.GFC.Absyn.PI) foo;
       render("(");
       render("PI");
       sh(_pi.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PF)
    {
       se.chalmers.cs.gf.GFC.Absyn.PF _pf = (se.chalmers.cs.gf.GFC.Absyn.PF) foo;
       render("(");
       render("PF");
       sh(_pf.double_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.PattAssign foo)
  {
    if (foo instanceof se.chalmers.cs.gf.GFC.Absyn.PAss)
    {
       se.chalmers.cs.gf.GFC.Absyn.PAss _pass = (se.chalmers.cs.gf.GFC.Absyn.PAss) foo;
       render("(");
       render("PAss");
       sh(_pass.label_);
       sh(_pass.patt_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListFlag foo)
  {
     for (java.util.Iterator<Flag> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListDef foo)
  {
     for (java.util.Iterator<Def> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListParDef foo)
  {
     for (java.util.Iterator<ParDef> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListCType foo)
  {
     for (java.util.Iterator<CType> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListCIdent foo)
  {
     for (java.util.Iterator<CIdent> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListAssign foo)
  {
     for (java.util.Iterator<Assign> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListArgVar foo)
  {
     for (java.util.Iterator<ArgVar> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListLabelling foo)
  {
     for (java.util.Iterator<Labelling> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListCase foo)
  {
     for (java.util.Iterator<Case> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListTerm foo)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListString foo)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListVariant foo)
  {
     for (java.util.Iterator<Variant> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListPattAssign foo)
  {
     for (java.util.Iterator<PattAssign> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListPatt foo)
  {
     for (java.util.Iterator<Patt> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.GFC.Absyn.ListIdent foo)
  {
     for (java.util.Iterator<String> it = foo.iterator(); it.hasNext();)
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

