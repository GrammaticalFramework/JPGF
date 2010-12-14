package org.grammaticalframework.reader;

public class Eq {
Pattern[] patts;
Expr expr;
	
public Eq(Pattern[] _patts, Expr _expr) 
{patts = _patts;
 expr = _expr;}

public String toString()
{String ss = "Patterns : (";
for(int i=0; i<patts.length;i++)
	ss+=(" "+patts[i].toString());
ss+=") , Expression : "+expr.toString();
return ss;
	}
}
