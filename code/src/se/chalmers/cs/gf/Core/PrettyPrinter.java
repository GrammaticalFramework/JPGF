package se.chalmers.cs.gf.Core;
import se.chalmers.cs.gf.Core.Absyn.*;

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
  public static String print(se.chalmers.cs.gf.Core.Absyn.Module foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.Module foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListDecl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListDecl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.Decl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.Decl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ConsDecl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ConsDecl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListConsDecl foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListConsDecl foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListPattern foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListPattern foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.Pattern foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.Pattern foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.FieldPattern foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.FieldPattern foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListFieldPattern foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListFieldPattern foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.PatternVariable foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.PatternVariable foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.Exp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.LetDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.LetDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListLetDef foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListLetDef foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.Case foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.Case foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListCase foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListCase foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.FieldType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.FieldType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListFieldType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListFieldType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.FieldValue foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.FieldValue foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.Core.Absyn.ListFieldValue foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.Core.Absyn.ListFieldValue foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.Core.Absyn.Module foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.Module)
    {
       se.chalmers.cs.gf.Core.Absyn.Module _module = (se.chalmers.cs.gf.Core.Absyn.Module) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_module.listdecl_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListDecl foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.Core.Absyn.Decl foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.DataDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.DataDecl _datadecl = (se.chalmers.cs.gf.Core.Absyn.DataDecl) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("data");
       pp(_datadecl.cident_, 0);
       render(":");
       pp(_datadecl.exp_, 0);
       render("where");
       render("{");
       pp(_datadecl.listconsdecl_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.TypeDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.TypeDecl _typedecl = (se.chalmers.cs.gf.Core.Absyn.TypeDecl) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_typedecl.cident_, 0);
       render(":");
       pp(_typedecl.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ValueDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.ValueDecl _valuedecl = (se.chalmers.cs.gf.Core.Absyn.ValueDecl) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_valuedecl.cident_, 0);
       render("=");
       pp(_valuedecl.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ConsDecl foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ConsDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.ConsDecl _consdecl = (se.chalmers.cs.gf.Core.Absyn.ConsDecl) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_consdecl.cident_, 0);
       render(":");
       pp(_consdecl.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListConsDecl foo, int _i_)
  {
     for (java.util.Iterator<ConsDecl> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListPattern foo, int _i_)
  {
     for (java.util.Iterator<Pattern> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.Pattern foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PCons)
    {
       se.chalmers.cs.gf.Core.Absyn.PCons _pcons = (se.chalmers.cs.gf.Core.Absyn.PCons) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_pcons.cident_, 0);
       pp(_pcons.listpattern_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVar)
    {
       se.chalmers.cs.gf.Core.Absyn.PVar _pvar = (se.chalmers.cs.gf.Core.Absyn.PVar) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pvar.patternvariable_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PRec)
    {
       se.chalmers.cs.gf.Core.Absyn.PRec _prec = (se.chalmers.cs.gf.Core.Absyn.PRec) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("rec");
       render("{");
       pp(_prec.listfieldpattern_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PStr)
    {
       se.chalmers.cs.gf.Core.Absyn.PStr _pstr = (se.chalmers.cs.gf.Core.Absyn.PStr) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_pstr.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PInt)
    {
       se.chalmers.cs.gf.Core.Absyn.PInt _pint = (se.chalmers.cs.gf.Core.Absyn.PInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pint.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.FieldPattern foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldPattern)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldPattern _fieldpattern = (se.chalmers.cs.gf.Core.Absyn.FieldPattern) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_fieldpattern.cident_, 0);
       render("=");
       pp(_fieldpattern.pattern_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListFieldPattern foo, int _i_)
  {
     for (java.util.Iterator<FieldPattern> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.PatternVariable foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVVar)
    {
       se.chalmers.cs.gf.Core.Absyn.PVVar _pvvar = (se.chalmers.cs.gf.Core.Absyn.PVVar) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pvvar.cident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVWild)
    {
       se.chalmers.cs.gf.Core.Absyn.PVWild _pvwild = (se.chalmers.cs.gf.Core.Absyn.PVWild) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ELet)
    {
       se.chalmers.cs.gf.Core.Absyn.ELet _elet = (se.chalmers.cs.gf.Core.Absyn.ELet) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("let");
       render("{");
       pp(_elet.listletdef_, 0);
       render("}");
       render("in");
       pp(_elet.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ECase)
    {
       se.chalmers.cs.gf.Core.Absyn.ECase _ecase = (se.chalmers.cs.gf.Core.Absyn.ECase) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("case");
       pp(_ecase.exp_, 0);
       render("of");
       render("{");
       pp(_ecase.listcase_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EAbs)
    {
       se.chalmers.cs.gf.Core.Absyn.EAbs _eabs = (se.chalmers.cs.gf.Core.Absyn.EAbs) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("\\");
       pp(_eabs.patternvariable_, 0);
       render("->");
       pp(_eabs.exp_, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EPi)
    {
       se.chalmers.cs.gf.Core.Absyn.EPi _epi = (se.chalmers.cs.gf.Core.Absyn.EPi) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("(");
       pp(_epi.patternvariable_, 0);
       render(":");
       pp(_epi.exp_1, 0);
       render(")");
       render("->");
       pp(_epi.exp_2, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EApp)
    {
       se.chalmers.cs.gf.Core.Absyn.EApp _eapp = (se.chalmers.cs.gf.Core.Absyn.EApp) foo;
       if (_i_ > 3) render(_L_PAREN);
       pp(_eapp.exp_1, 3);
       pp(_eapp.exp_2, 4);
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EProj)
    {
       se.chalmers.cs.gf.Core.Absyn.EProj _eproj = (se.chalmers.cs.gf.Core.Absyn.EProj) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_eproj.exp_, 4);
       render(".");
       pp(_eproj.cident_, 0);
       if (_i_ > 4) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ERecType)
    {
       se.chalmers.cs.gf.Core.Absyn.ERecType _erectype = (se.chalmers.cs.gf.Core.Absyn.ERecType) foo;
       if (_i_ > 5) render(_L_PAREN);
       render("sig");
       render("{");
       pp(_erectype.listfieldtype_, 0);
       render("}");
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ERec)
    {
       se.chalmers.cs.gf.Core.Absyn.ERec _erec = (se.chalmers.cs.gf.Core.Absyn.ERec) foo;
       if (_i_ > 5) render(_L_PAREN);
       render("rec");
       render("{");
       pp(_erec.listfieldvalue_, 0);
       render("}");
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EVar)
    {
       se.chalmers.cs.gf.Core.Absyn.EVar _evar = (se.chalmers.cs.gf.Core.Absyn.EVar) foo;
       if (_i_ > 5) render(_L_PAREN);
       pp(_evar.cident_, 0);
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EType)
    {
       se.chalmers.cs.gf.Core.Absyn.EType _etype = (se.chalmers.cs.gf.Core.Absyn.EType) foo;
       if (_i_ > 5) render(_L_PAREN);
       render("Type");
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EStr)
    {
       se.chalmers.cs.gf.Core.Absyn.EStr _estr = (se.chalmers.cs.gf.Core.Absyn.EStr) foo;
       if (_i_ > 5) render(_L_PAREN);
       printQuoted(_estr.string_);
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EInteger)
    {
       se.chalmers.cs.gf.Core.Absyn.EInteger _einteger = (se.chalmers.cs.gf.Core.Absyn.EInteger) foo;
       if (_i_ > 5) render(_L_PAREN);
       pp(_einteger.integer_, 0);
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EDouble)
    {
       se.chalmers.cs.gf.Core.Absyn.EDouble _edouble = (se.chalmers.cs.gf.Core.Absyn.EDouble) foo;
       if (_i_ > 5) render(_L_PAREN);
       pp(_edouble.double_, 0);
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EMeta)
    {
       se.chalmers.cs.gf.Core.Absyn.EMeta _emeta = (se.chalmers.cs.gf.Core.Absyn.EMeta) foo;
       if (_i_ > 5) render(_L_PAREN);
       pp(_emeta.tmeta_, 0);
       if (_i_ > 5) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.LetDef foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.LetDef)
    {
       se.chalmers.cs.gf.Core.Absyn.LetDef _letdef = (se.chalmers.cs.gf.Core.Absyn.LetDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_letdef.cident_, 0);
       render("=");
       pp(_letdef.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListLetDef foo, int _i_)
  {
     for (java.util.Iterator<LetDef> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.Case foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.Case)
    {
       se.chalmers.cs.gf.Core.Absyn.Case _case = (se.chalmers.cs.gf.Core.Absyn.Case) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_case.pattern_, 0);
       render("|");
       pp(_case.exp_1, 0);
       render("->");
       pp(_case.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListCase foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.Core.Absyn.FieldType foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldType)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldType _fieldtype = (se.chalmers.cs.gf.Core.Absyn.FieldType) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_fieldtype.cident_, 0);
       render(":");
       pp(_fieldtype.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListFieldType foo, int _i_)
  {
     for (java.util.Iterator<FieldType> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.FieldValue foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldValue)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldValue _fieldvalue = (se.chalmers.cs.gf.Core.Absyn.FieldValue) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_fieldvalue.cident_, 0);
       render("=");
       pp(_fieldvalue.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.Core.Absyn.ListFieldValue foo, int _i_)
  {
     for (java.util.Iterator<FieldValue> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }


  private static void sh(se.chalmers.cs.gf.Core.Absyn.Module foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.Module)
    {
       se.chalmers.cs.gf.Core.Absyn.Module _module = (se.chalmers.cs.gf.Core.Absyn.Module) foo;
       render("(");
       render("Module");
       render("[");
       sh(_module.listdecl_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListDecl foo)
  {
     for (java.util.Iterator<Decl> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.Decl foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.DataDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.DataDecl _datadecl = (se.chalmers.cs.gf.Core.Absyn.DataDecl) foo;
       render("(");
       render("DataDecl");
       sh(_datadecl.cident_);
       sh(_datadecl.exp_);
       render("[");
       sh(_datadecl.listconsdecl_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.TypeDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.TypeDecl _typedecl = (se.chalmers.cs.gf.Core.Absyn.TypeDecl) foo;
       render("(");
       render("TypeDecl");
       sh(_typedecl.cident_);
       sh(_typedecl.exp_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ValueDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.ValueDecl _valuedecl = (se.chalmers.cs.gf.Core.Absyn.ValueDecl) foo;
       render("(");
       render("ValueDecl");
       sh(_valuedecl.cident_);
       sh(_valuedecl.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ConsDecl foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ConsDecl)
    {
       se.chalmers.cs.gf.Core.Absyn.ConsDecl _consdecl = (se.chalmers.cs.gf.Core.Absyn.ConsDecl) foo;
       render("(");
       render("ConsDecl");
       sh(_consdecl.cident_);
       sh(_consdecl.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListConsDecl foo)
  {
     for (java.util.Iterator<ConsDecl> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListPattern foo)
  {
     for (java.util.Iterator<Pattern> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.Pattern foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PCons)
    {
       se.chalmers.cs.gf.Core.Absyn.PCons _pcons = (se.chalmers.cs.gf.Core.Absyn.PCons) foo;
       render("(");
       render("PCons");
       sh(_pcons.cident_);
       render("[");
       sh(_pcons.listpattern_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVar)
    {
       se.chalmers.cs.gf.Core.Absyn.PVar _pvar = (se.chalmers.cs.gf.Core.Absyn.PVar) foo;
       render("(");
       render("PVar");
       sh(_pvar.patternvariable_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PRec)
    {
       se.chalmers.cs.gf.Core.Absyn.PRec _prec = (se.chalmers.cs.gf.Core.Absyn.PRec) foo;
       render("(");
       render("PRec");
       render("[");
       sh(_prec.listfieldpattern_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PStr)
    {
       se.chalmers.cs.gf.Core.Absyn.PStr _pstr = (se.chalmers.cs.gf.Core.Absyn.PStr) foo;
       render("(");
       render("PStr");
       sh(_pstr.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PInt)
    {
       se.chalmers.cs.gf.Core.Absyn.PInt _pint = (se.chalmers.cs.gf.Core.Absyn.PInt) foo;
       render("(");
       render("PInt");
       sh(_pint.integer_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.FieldPattern foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldPattern)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldPattern _fieldpattern = (se.chalmers.cs.gf.Core.Absyn.FieldPattern) foo;
       render("(");
       render("FieldPattern");
       sh(_fieldpattern.cident_);
       sh(_fieldpattern.pattern_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListFieldPattern foo)
  {
     for (java.util.Iterator<FieldPattern> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.PatternVariable foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVVar)
    {
       se.chalmers.cs.gf.Core.Absyn.PVVar _pvvar = (se.chalmers.cs.gf.Core.Absyn.PVVar) foo;
       render("(");
       render("PVVar");
       sh(_pvvar.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.PVWild)
    {
       se.chalmers.cs.gf.Core.Absyn.PVWild _pvwild = (se.chalmers.cs.gf.Core.Absyn.PVWild) foo;
       render("PVWild");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.Exp foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ELet)
    {
       se.chalmers.cs.gf.Core.Absyn.ELet _elet = (se.chalmers.cs.gf.Core.Absyn.ELet) foo;
       render("(");
       render("ELet");
       render("[");
       sh(_elet.listletdef_);
       render("]");
       sh(_elet.exp_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ECase)
    {
       se.chalmers.cs.gf.Core.Absyn.ECase _ecase = (se.chalmers.cs.gf.Core.Absyn.ECase) foo;
       render("(");
       render("ECase");
       sh(_ecase.exp_);
       render("[");
       sh(_ecase.listcase_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EAbs)
    {
       se.chalmers.cs.gf.Core.Absyn.EAbs _eabs = (se.chalmers.cs.gf.Core.Absyn.EAbs) foo;
       render("(");
       render("EAbs");
       sh(_eabs.patternvariable_);
       sh(_eabs.exp_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EPi)
    {
       se.chalmers.cs.gf.Core.Absyn.EPi _epi = (se.chalmers.cs.gf.Core.Absyn.EPi) foo;
       render("(");
       render("EPi");
       sh(_epi.patternvariable_);
       sh(_epi.exp_1);
       sh(_epi.exp_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EApp)
    {
       se.chalmers.cs.gf.Core.Absyn.EApp _eapp = (se.chalmers.cs.gf.Core.Absyn.EApp) foo;
       render("(");
       render("EApp");
       sh(_eapp.exp_1);
       sh(_eapp.exp_2);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EProj)
    {
       se.chalmers.cs.gf.Core.Absyn.EProj _eproj = (se.chalmers.cs.gf.Core.Absyn.EProj) foo;
       render("(");
       render("EProj");
       sh(_eproj.exp_);
       sh(_eproj.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ERecType)
    {
       se.chalmers.cs.gf.Core.Absyn.ERecType _erectype = (se.chalmers.cs.gf.Core.Absyn.ERecType) foo;
       render("(");
       render("ERecType");
       render("[");
       sh(_erectype.listfieldtype_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.ERec)
    {
       se.chalmers.cs.gf.Core.Absyn.ERec _erec = (se.chalmers.cs.gf.Core.Absyn.ERec) foo;
       render("(");
       render("ERec");
       render("[");
       sh(_erec.listfieldvalue_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EVar)
    {
       se.chalmers.cs.gf.Core.Absyn.EVar _evar = (se.chalmers.cs.gf.Core.Absyn.EVar) foo;
       render("(");
       render("EVar");
       sh(_evar.cident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EType)
    {
       se.chalmers.cs.gf.Core.Absyn.EType _etype = (se.chalmers.cs.gf.Core.Absyn.EType) foo;
       render("EType");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EStr)
    {
       se.chalmers.cs.gf.Core.Absyn.EStr _estr = (se.chalmers.cs.gf.Core.Absyn.EStr) foo;
       render("(");
       render("EStr");
       sh(_estr.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EInteger)
    {
       se.chalmers.cs.gf.Core.Absyn.EInteger _einteger = (se.chalmers.cs.gf.Core.Absyn.EInteger) foo;
       render("(");
       render("EInteger");
       sh(_einteger.integer_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EDouble)
    {
       se.chalmers.cs.gf.Core.Absyn.EDouble _edouble = (se.chalmers.cs.gf.Core.Absyn.EDouble) foo;
       render("(");
       render("EDouble");
       sh(_edouble.double_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.EMeta)
    {
       se.chalmers.cs.gf.Core.Absyn.EMeta _emeta = (se.chalmers.cs.gf.Core.Absyn.EMeta) foo;
       render("(");
       render("EMeta");
       sh(_emeta.tmeta_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.LetDef foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.LetDef)
    {
       se.chalmers.cs.gf.Core.Absyn.LetDef _letdef = (se.chalmers.cs.gf.Core.Absyn.LetDef) foo;
       render("(");
       render("LetDef");
       sh(_letdef.cident_);
       sh(_letdef.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListLetDef foo)
  {
     for (java.util.Iterator<LetDef> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.Case foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.Case)
    {
       se.chalmers.cs.gf.Core.Absyn.Case _case = (se.chalmers.cs.gf.Core.Absyn.Case) foo;
       render("(");
       render("Case");
       sh(_case.pattern_);
       sh(_case.exp_1);
       sh(_case.exp_2);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListCase foo)
  {
     for (java.util.Iterator<Case> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.FieldType foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldType)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldType _fieldtype = (se.chalmers.cs.gf.Core.Absyn.FieldType) foo;
       render("(");
       render("FieldType");
       sh(_fieldtype.cident_);
       sh(_fieldtype.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListFieldType foo)
  {
     for (java.util.Iterator<FieldType> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.FieldValue foo)
  {
    if (foo instanceof se.chalmers.cs.gf.Core.Absyn.FieldValue)
    {
       se.chalmers.cs.gf.Core.Absyn.FieldValue _fieldvalue = (se.chalmers.cs.gf.Core.Absyn.FieldValue) foo;
       render("(");
       render("FieldValue");
       sh(_fieldvalue.cident_);
       sh(_fieldvalue.exp_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.Core.Absyn.ListFieldValue foo)
  {
     for (java.util.Iterator<FieldValue> it = foo.iterator(); it.hasNext();)
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

