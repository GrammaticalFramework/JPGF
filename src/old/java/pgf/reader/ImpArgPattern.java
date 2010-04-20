package reader;

public class ImpArgPattern extends Pattern{
Pattern name;

public ImpArgPattern(Pattern _name)
{name = _name; }

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.ImpArgPattern) {
    return true;
  }
  return false;
}

public String toString()
{return "Implicit Argument Pattern : "+name.toString();}

}
