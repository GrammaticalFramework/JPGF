package se.chalmers.cs.gf.SyntaxTree;
import se.chalmers.cs.gf.SyntaxTree.Absyn.*;

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
  public static String print(se.chalmers.cs.gf.SyntaxTree.Absyn.Tr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(se.chalmers.cs.gf.SyntaxTree.Absyn.Tr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(se.chalmers.cs.gf.SyntaxTree.Absyn.Tr foo, int _i_)
  {
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TNode)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TNode _tnode = (se.chalmers.cs.gf.SyntaxTree.Absyn.TNode) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_tnode.ident_, 0);
       pp(_tnode.listtr_, 2);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom _tatom = (se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_tatom.ident_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TStr)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TStr _tstr = (se.chalmers.cs.gf.SyntaxTree.Absyn.TStr) foo;
       if (_i_ > 2) render(_L_PAREN);
       printQuoted(_tstr.string_);
       if (_i_ > 2) render(_R_PAREN);
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TInt)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TInt _tint = (se.chalmers.cs.gf.SyntaxTree.Absyn.TInt) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_tint.integer_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
  }

  private static void pp(se.chalmers.cs.gf.SyntaxTree.Absyn.ListTr foo, int _i_)
  {
     for (java.util.Iterator<Tr> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }


  private static void sh(se.chalmers.cs.gf.SyntaxTree.Absyn.Tr foo)
  {
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TNode)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TNode _tnode = (se.chalmers.cs.gf.SyntaxTree.Absyn.TNode) foo;
       render("(");
       render("TNode");
       sh(_tnode.ident_);
       render("[");
       sh(_tnode.listtr_);
       render("]");
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom _tatom = (se.chalmers.cs.gf.SyntaxTree.Absyn.TAtom) foo;
       render("(");
       render("TAtom");
       sh(_tatom.ident_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TStr)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TStr _tstr = (se.chalmers.cs.gf.SyntaxTree.Absyn.TStr) foo;
       render("(");
       render("TStr");
       sh(_tstr.string_);
       render(")");
    }
    if (foo instanceof se.chalmers.cs.gf.SyntaxTree.Absyn.TInt)
    {
       se.chalmers.cs.gf.SyntaxTree.Absyn.TInt _tint = (se.chalmers.cs.gf.SyntaxTree.Absyn.TInt) foo;
       render("(");
       render("TInt");
       sh(_tint.integer_);
       render(")");
    }
  }

  private static void sh(se.chalmers.cs.gf.SyntaxTree.Absyn.ListTr foo)
  {
     for (java.util.Iterator<Tr> it = foo.iterator(); it.hasNext();)
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
   String s = buf_.toString();

     if (s.substring(buf_.length() - 1, buf_.length()).equals(" ")) {
       buf_.setCharAt(buf_.length() - 1, '\"');
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

