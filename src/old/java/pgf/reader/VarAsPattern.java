package reader;

public class VarAsPattern extends Pattern{
String name;
Pattern patt;

public VarAsPattern(String _name, Pattern _patt)
{name = _name; 
 patt = _patt;}

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.VarAsPattern) {
    return true;
  }
  return false;
}

public String toString()
{return "Variable as Pattern : [ Variable Name : "+name + " , Pattern : "+patt + "]";}

}
