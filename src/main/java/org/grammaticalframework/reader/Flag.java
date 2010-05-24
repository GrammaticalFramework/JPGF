package org.grammaticalframework.reader;

public class Flag {
String str;
Literal lit;

public Flag(String _str, Literal _lit)
{str = _str; lit = _lit;}

public String toString()
{return "Name : "+str + " , Literal : ("+ lit.toString()+")";}
}
