package pgf.reader;

public class VarAsPattern extends Pattern{
String name;
Pattern patt;

public VarAsPattern(String _name, Pattern _patt)
{name = _name; 
 patt = _patt;}


public String toString()
{return "Variable as Pattern : [ Variable Name : "+name + " , Pattern : "+patt + "]";}

}
