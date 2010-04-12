package se.chalmers.cs.gf.CFG;
import se.chalmers.cs.gf.CFG.Absyn.*;

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
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Grammars foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Grammars foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Grammar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Grammar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListGrammar foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListGrammar foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Flag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Flag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListFlag foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListFlag foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Rule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Rule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListRule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListRule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Fun foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Fun foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Profiles foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Profiles foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListProfile foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListProfile foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Profile foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Profile foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListInteger foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListInteger foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Symbol foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Symbol foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.ListSymbol foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.ListSymbol foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(se.chalmers.cs.gf.CFG.Absyn.Category foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.CFG.Absyn.Category foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Grammars foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Grammars)
    {
       se.chalmers.cs.gf.CFG.Absyn.Grammars _grammars = (se.chalmers.cs.gf.CFG.Absyn.Grammars) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_grammars.listgrammar_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Grammar foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Grammar)
    {
       se.chalmers.cs.gf.CFG.Absyn.Grammar _grammar = (se.chalmers.cs.gf.CFG.Absyn.Grammar) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("grammar");
       pp(_grammar.ident_, 0);
       pp(_grammar.listflag_, 0);
       pp(_grammar.listrule_, 0);
       render("end");
       render("grammar");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListGrammar foo, int _i_)
  {
     for (java.util.Iterator<Grammar> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Flag foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.StartCat)
    {
       se.chalmers.cs.gf.CFG.Absyn.StartCat _startcat = (se.chalmers.cs.gf.CFG.Absyn.StartCat) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("startcat");
       pp(_startcat.category_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListFlag foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Rule foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Rule)
    {
       se.chalmers.cs.gf.CFG.Absyn.Rule _rule = (se.chalmers.cs.gf.CFG.Absyn.Rule) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_rule.fun_, 0);
       render(":");
       pp(_rule.profiles_, 0);
       render(".");
       pp(_rule.category_, 0);
       render("->");
       pp(_rule.listsymbol_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListRule foo, int _i_)
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

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Fun foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Cons)
    {
       se.chalmers.cs.gf.CFG.Absyn.Cons _cons = (se.chalmers.cs.gf.CFG.Absyn.Cons) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_cons.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Coerce)
    {
       se.chalmers.cs.gf.CFG.Absyn.Coerce _coerce = (se.chalmers.cs.gf.CFG.Absyn.Coerce) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Profiles foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Profiles)
    {
       se.chalmers.cs.gf.CFG.Absyn.Profiles _profiles = (se.chalmers.cs.gf.CFG.Absyn.Profiles) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       pp(_profiles.listprofile_, 0);
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListProfile foo, int _i_)
  {
     for (java.util.Iterator<Profile> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Profile foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.UnifyProfile)
    {
       se.chalmers.cs.gf.CFG.Absyn.UnifyProfile _unifyprofile = (se.chalmers.cs.gf.CFG.Absyn.UnifyProfile) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("[");
       pp(_unifyprofile.listinteger_, 0);
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.ConstProfile)
    {
       se.chalmers.cs.gf.CFG.Absyn.ConstProfile _constprofile = (se.chalmers.cs.gf.CFG.Absyn.ConstProfile) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_constprofile.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListInteger foo, int _i_)
  {
     for (java.util.Iterator<Integer> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Symbol foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.CatS)
    {
       se.chalmers.cs.gf.CFG.Absyn.CatS _cats = (se.chalmers.cs.gf.CFG.Absyn.CatS) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_cats.category_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.TermS)
    {
       se.chalmers.cs.gf.CFG.Absyn.TermS _terms = (se.chalmers.cs.gf.CFG.Absyn.TermS) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_terms.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.ListSymbol foo, int _i_)
  {
     for (java.util.Iterator<Symbol> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(se.chalmers.cs.gf.CFG.Absyn.Category foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Category)
    {
       se.chalmers.cs.gf.CFG.Absyn.Category _category = (se.chalmers.cs.gf.CFG.Absyn.Category) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_category.singlequotestring_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Grammars foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Grammars)
    {
       se.chalmers.cs.gf.CFG.Absyn.Grammars _grammars = (se.chalmers.cs.gf.CFG.Absyn.Grammars) foo;
       render("(");
       render("Grammars");
       render("[");
       sh(_grammars.listgrammar_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Grammar foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Grammar)
    {
       se.chalmers.cs.gf.CFG.Absyn.Grammar _grammar = (se.chalmers.cs.gf.CFG.Absyn.Grammar) foo;
       render("(");
       render("Grammar");
       sh(_grammar.ident_);
       render("[");
       sh(_grammar.listflag_);
       render("]");
       render("[");
       sh(_grammar.listrule_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListGrammar foo)
  {
     for (java.util.Iterator<Grammar> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Flag foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.StartCat)
    {
       se.chalmers.cs.gf.CFG.Absyn.StartCat _startcat = (se.chalmers.cs.gf.CFG.Absyn.StartCat) foo;
       render("(");
       render("StartCat");
       sh(_startcat.category_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListFlag foo)
  {
     for (java.util.Iterator<Flag> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Rule foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Rule)
    {
       se.chalmers.cs.gf.CFG.Absyn.Rule _rule = (se.chalmers.cs.gf.CFG.Absyn.Rule) foo;
       render("(");
       render("Rule");
       sh(_rule.fun_);
       sh(_rule.profiles_);
       sh(_rule.category_);
       render("[");
       sh(_rule.listsymbol_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListRule foo)
  {
     for (java.util.Iterator<Rule> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Fun foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Cons)
    {
       se.chalmers.cs.gf.CFG.Absyn.Cons _cons = (se.chalmers.cs.gf.CFG.Absyn.Cons) foo;
       render("(");
       render("Cons");
       sh(_cons.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Coerce)
    {
       se.chalmers.cs.gf.CFG.Absyn.Coerce _coerce = (se.chalmers.cs.gf.CFG.Absyn.Coerce) foo;
       render("Coerce");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Profiles foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Profiles)
    {
       se.chalmers.cs.gf.CFG.Absyn.Profiles _profiles = (se.chalmers.cs.gf.CFG.Absyn.Profiles) foo;
       render("(");
       render("Profiles");
       render("[");
       sh(_profiles.listprofile_);
       render("]");
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListProfile foo)
  {
     for (java.util.Iterator<Profile> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Profile foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.UnifyProfile)
    {
       se.chalmers.cs.gf.CFG.Absyn.UnifyProfile _unifyprofile = (se.chalmers.cs.gf.CFG.Absyn.UnifyProfile) foo;
       render("(");
       render("UnifyProfile");
       render("[");
       sh(_unifyprofile.listinteger_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.ConstProfile)
    {
       se.chalmers.cs.gf.CFG.Absyn.ConstProfile _constprofile = (se.chalmers.cs.gf.CFG.Absyn.ConstProfile) foo;
       render("(");
       render("ConstProfile");
       sh(_constprofile.ident_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListInteger foo)
  {
     for (java.util.Iterator<Integer> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Symbol foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.CatS)
    {
       se.chalmers.cs.gf.CFG.Absyn.CatS _cats = (se.chalmers.cs.gf.CFG.Absyn.CatS) foo;
       render("(");
       render("CatS");
       sh(_cats.category_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.TermS)
    {
       se.chalmers.cs.gf.CFG.Absyn.TermS _terms = (se.chalmers.cs.gf.CFG.Absyn.TermS) foo;
       render("(");
       render("TermS");
       sh(_terms.string_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.ListSymbol foo)
  {
     for (java.util.Iterator<Symbol> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(se.chalmers.cs.gf.CFG.Absyn.Category foo)
  {
    if (foo instanceof se.chalmers.cs.gf.CFG.Absyn.Category)
    {
       se.chalmers.cs.gf.CFG.Absyn.Category _category = (se.chalmers.cs.gf.CFG.Absyn.Category) foo;
       render("(");
       render("Category");
       sh(_category.singlequotestring_);
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

