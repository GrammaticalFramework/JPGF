/* Type.java
 * Copyright (C) 2010 Grégoire Détrez, Ramona Enache
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
