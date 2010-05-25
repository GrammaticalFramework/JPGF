package org.grammaticalframework.reader;

public class LiteralPattern extends Pattern {
private RLiteral value;

public LiteralPattern(RLiteral _value)
{value = _value; }

public String toString()
{return "Literal Pattern : "+value.toString();}

public RLiteral getLit() {return value;}
}
