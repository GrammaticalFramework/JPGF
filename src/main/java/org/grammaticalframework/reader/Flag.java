package org.grammaticalframework.reader;

public class Flag {
String str;
RLiteral lit;

public Flag(String _str, RLiteral _lit)
{str = _str; lit = _lit;}

public String toString()
{return "Name : "+str + " , Literal : ("+ lit.toString()+")";}
}
