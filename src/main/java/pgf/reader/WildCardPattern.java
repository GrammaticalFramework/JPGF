package pgf.reader;

public class WildCardPattern extends Pattern{

public WildCardPattern()
{}

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.WildCardPattern) {
    return true;
  }
  return false;
}

public String toString()
{return "Wild Card Pattern";}

}
