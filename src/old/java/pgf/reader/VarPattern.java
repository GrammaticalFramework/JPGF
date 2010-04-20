package reader;

public class VarPattern extends Pattern {
String name;

public VarPattern(String _name)
{name = _name; }

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.VarPattern) {
    return true;
  }
  return false;
}

public String toString()
{return "Variable Pattern : " + name;}
}
