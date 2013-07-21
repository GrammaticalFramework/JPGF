/* Hypo.java
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

public class Hypo {
    public final boolean bind;
    public final String name;
    public final Type type;

    public Hypo(boolean _bind, String _str, Type _t) {
	this.bind = _bind;
	this.name = _str;
	this.type = _t;
    }

    public String toString() {
	if (bind)
	    return "(" + name + ": "  + type + ")";
	else
	    return type.toString();
    }

    public boolean getBind() {return bind;}
    public String getName() {return name;}
    public Type getType() {return type;}
}
