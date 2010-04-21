package pgf.reader;

public class AppPattern extends Pattern{
String name;
Pattern[] patts;

public AppPattern(String _name, Pattern[] _patts)
{name = _name; 
 patts = _patts;}

public <R,A> R accept(reader.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

public boolean equals(Object o) {
  if (this == o) return true;
  if (o instanceof reader.AppPattern) {
    return true;
  }
  return false;
}

public String toString()
{String ss = "Application pattern [ Name : "+name+ " , Patterns : (";
for (int i=0; i<patts.length;i++)
ss+=(" "+patts[i].toString());
ss+=")]";
return ss;
}

}
