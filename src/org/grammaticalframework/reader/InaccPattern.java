package org.grammaticalframework.reader;

public class InaccPattern extends Pattern{
Expr exp;

public InaccPattern(Expr _exp)
{exp = _exp; }

public String toString()
{return "Inaccessible Pattern : "+ exp.toString();}


}
