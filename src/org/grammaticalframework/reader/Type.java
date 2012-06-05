package org.grammaticalframework.reader;

import java.util.*;

public class Type {
    public final Hypo [] hypos;
    private String str;
    private Expr[] exprs;

    public Type(Hypo[] _hypos, String _str, Expr[] _exprs) {
	hypos = _hypos; 
	str = _str;
	exprs = _exprs;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	for (Hypo h: hypos) {
	    if (h.type.isFunctionType())
		sb.append("(");
	    sb.append(h);
	    if (h.type.isFunctionType())
		sb.append(")");
	    sb.append(" -> ");
	}
	sb.append(str);
	for (Expr e: exprs) {
	    sb.append(" ");
	    sb.append(e);
	}
	return sb.toString();
    }

    public Hypo[] getHypos() {return hypos;}
    public String getName() {return str;}
    public Expr[] getExprs() {return exprs;}

    public boolean isFunctionType() {
	return this.hypos.length > 0;
    }

    public boolean isFunctorType() {
	for (Hypo h: hypos)
	    if (h.type.isFunctionType())
		return true;
	return false;
    }

}
