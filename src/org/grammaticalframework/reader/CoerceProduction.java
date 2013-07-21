/* CoerceProduction.java
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

public class CoerceProduction extends Production {
    private  int initId;

    public CoerceProduction(int fId, int _initId) {
	super(1, fId);
	initId = _initId;
    }

    public int getInitId() {return initId;}

    public int[] domain() {
        return new int[] {this.initId};
    }

    public String toString(){
        return "Coercion(" + this.fId + " -> " +initId + ")";
    }

    public boolean equals(Object o)
        {if(o instanceof CoerceProduction)
        	return ((CoerceProduction) o).initId == initId;
        return false;}

}


