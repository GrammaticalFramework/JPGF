package reader;

public class AppPattern extends Pattern{
String name;
Pattern[] patts;

public AppPattern(String _name, Pattern[] _patts)
{name = _name; 
 patts = _patts;}

public String toString()
{String ss = "Application pattern [ Name : "+name+ " , Patterns : (";
for (int i=0; i<patts.length;i++)
ss+=(" "+patts[i].toString());
ss+=")]";
return ss;
}

}
