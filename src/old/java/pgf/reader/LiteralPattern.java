package reader;

public class LiteralPattern extends Pattern {
Literal value;

public LiteralPattern(Literal _value)
{value = _value; }

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.LiteralPattern) {
    return true;
  }
  return false;
}

public String toString()
{return "Literal Pattern : "+value.toString();}
}
