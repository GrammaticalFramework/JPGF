package reader;

public class InaccPattern extends Pattern{
Expr exp;

public InaccPattern(Expr _exp)
{exp = _exp; }

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public String toString()
{return "Inaccessible Pattern : "+ exp.toString();}

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.InaccPattern) {
    return true;
  }
  return false;
}

}
