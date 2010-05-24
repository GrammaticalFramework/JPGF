package org.grammaticalframework.reader;

public class ImpArgPattern extends Pattern{
Pattern name;

public ImpArgPattern(Pattern _name)
{name = _name; }

public String toString()
{return "Implicit Argument Pattern : "+name.toString();}

}
