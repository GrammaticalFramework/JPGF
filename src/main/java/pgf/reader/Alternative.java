package pgf.reader;

public class Alternative {
String[] alt1;
String[] alt2;

public Alternative(String[] _alt1, String[] _alt2)
{alt1 = _alt1;
 alt2 = _alt2;}

public String toString()
{return "(Alternative 1 : "+ alt1 + " , Alternative2 : "+alt2+")";}
}
