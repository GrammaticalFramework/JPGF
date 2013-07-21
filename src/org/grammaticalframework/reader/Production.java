/* Production.java
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

public abstract class Production {

    private int sel;
    protected int fId;
    
    public Production(int selector, int fId) {
	this.sel = selector;
	this.fId = fId;
    }

    public int getCategory() {
        return this.fId;
    }
    
    public int range() { return this.fId; }
    
    public abstract String toString();
    
    // Domain is the domain of the concrete function
    public abstract int[] domain();

    public int getSel() {return sel;}
    public int getFId() {return fId;}

}
