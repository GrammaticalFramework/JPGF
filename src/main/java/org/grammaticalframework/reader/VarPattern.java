package org.grammaticalframework.reader;

public class VarPattern extends Pattern {
String name;

public VarPattern(String _name)
{name = _name; }

public String toString()
{return "Variable Pattern : " + name;}
}
