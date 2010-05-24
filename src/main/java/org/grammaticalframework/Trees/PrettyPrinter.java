package org.grammaticalframework.Trees;
import org.grammaticalframework.Trees.Absyn.*;

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
  public static String print(org.grammaticalframework.Trees.Absyn.Tree foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(org.grammaticalframework.Trees.Absyn.Tree foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(org.grammaticalframework.Trees.Absyn.Lit foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(org.grammaticalframework.Trees.Absyn.Lit foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(org.grammaticalframework.Trees.Absyn.Tree foo, int _i_)
  {
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Lambda)
    {
       org.grammaticalframework.Trees.Absyn.Lambda _lambda = (org.grammaticalframework.Trees.Absyn.Lambda) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("\\");
       pp(_lambda.ident_, 0);
       render("->");
       pp(_lambda.tree_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.Variable)
    {
       org.grammaticalframework.Trees.Absyn.Variable _variable = (org.grammaticalframework.Trees.Absyn.Variable) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("$");
       pp(_variable.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.Application)
    {
       org.grammaticalframework.Trees.Absyn.Application _application = (org.grammaticalframework.Trees.Absyn.Application) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_application.tree_1, 0);
       pp(_application.tree_2, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.Literal)
    {
       org.grammaticalframework.Trees.Absyn.Literal _literal = (org.grammaticalframework.Trees.Absyn.Literal) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_literal.lit_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.MetaVariable)
    {
       org.grammaticalframework.Trees.Absyn.MetaVariable _metavariable = (org.grammaticalframework.Trees.Absyn.MetaVariable) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("META_");
       pp(_metavariable.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.Function)
    {
       org.grammaticalframework.Trees.Absyn.Function _function = (org.grammaticalframework.Trees.Absyn.Function) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_function.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(org.grammaticalframework.Trees.Absyn.Lit foo, int _i_)
  {
    if (foo instanceof org.grammaticalframework.Trees.Absyn.IntLiteral)
    {
       org.grammaticalframework.Trees.Absyn.IntLiteral _intliteral = (org.grammaticalframework.Trees.Absyn.IntLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_intliteral.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.FloatLiteral)
    {
       org.grammaticalframework.Trees.Absyn.FloatLiteral _floatliteral = (org.grammaticalframework.Trees.Absyn.FloatLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_floatliteral.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof org.grammaticalframework.Trees.Absyn.StringLiteral)
    {
       org.grammaticalframework.Trees.Absyn.StringLiteral _stringliteral = (org.grammaticalframework.Trees.Absyn.StringLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_stringliteral.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(org.grammaticalframework.Trees.Absyn.Tree foo)
  {
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Lambda)
    {
       org.grammaticalframework.Trees.Absyn.Lambda _lambda = (org.grammaticalframework.Trees.Absyn.Lambda) foo;
       render("(");
       render("Lambda");
       sh(_lambda.ident_);
       sh(_lambda.tree_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Variable)
    {
       org.grammaticalframework.Trees.Absyn.Variable _variable = (org.grammaticalframework.Trees.Absyn.Variable) foo;
       render("(");
       render("Variable");
       sh(_variable.integer_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Application)
    {
       org.grammaticalframework.Trees.Absyn.Application _application = (org.grammaticalframework.Trees.Absyn.Application) foo;
       render("(");
       render("Application");
       sh(_application.tree_1);
       sh(_application.tree_2);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Literal)
    {
       org.grammaticalframework.Trees.Absyn.Literal _literal = (org.grammaticalframework.Trees.Absyn.Literal) foo;
       render("(");
       render("Literal");
       sh(_literal.lit_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.MetaVariable)
    {
       org.grammaticalframework.Trees.Absyn.MetaVariable _metavariable = (org.grammaticalframework.Trees.Absyn.MetaVariable) foo;
       render("(");
       render("MetaVariable");
       sh(_metavariable.integer_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.Function)
    {
       org.grammaticalframework.Trees.Absyn.Function _function = (org.grammaticalframework.Trees.Absyn.Function) foo;
       render("(");
       render("Function");
       sh(_function.ident_);
       render(")");
    }
  }

  private static void sh(org.grammaticalframework.Trees.Absyn.Lit foo)
  {
    if (foo instanceof org.grammaticalframework.Trees.Absyn.IntLiteral)
    {
       org.grammaticalframework.Trees.Absyn.IntLiteral _intliteral = (org.grammaticalframework.Trees.Absyn.IntLiteral) foo;
       render("(");
       render("IntLiteral");
       sh(_intliteral.integer_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.FloatLiteral)
    {
       org.grammaticalframework.Trees.Absyn.FloatLiteral _floatliteral = (org.grammaticalframework.Trees.Absyn.FloatLiteral) foo;
       render("(");
       render("FloatLiteral");
       sh(_floatliteral.double_);
       render(")");
    }
    if (foo instanceof org.grammaticalframework.Trees.Absyn.StringLiteral)
    {
       org.grammaticalframework.Trees.Absyn.StringLiteral _stringliteral = (org.grammaticalframework.Trees.Absyn.StringLiteral) foo;
       render("(");
       render("StringLiteral");
       sh(_stringliteral.string_);
       render(")");
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

