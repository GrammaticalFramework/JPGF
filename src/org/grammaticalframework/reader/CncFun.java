/* CncFun.java
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

public class CncFun {
    private String name;
    private Sequence[] sequences;
    
    public CncFun(String _name, Sequence[] seqs)
    {
        name = _name;
        this.sequences = seqs;
    }
    
    /**
     * Accessors
     */
    public String name() {
        return this.name;
    }
    
    public Sequence[] sequences() {
        return this.sequences;
    }
    
    public Sequence sequence(int index) {
        return this.sequences[index];
    }
    
    public Symbol symbol(int seqIndex, int symbIndex) {
        return this.sequences[seqIndex].symbol(symbIndex);
    }
    
    public int size() {
        return this.sequences.length;
    }
    
    public String toString()
    {
        String ss = "Name : "+name + " , Indices : ";
        for(int i=0; i < sequences.length; i++)
            ss+=(" " + sequences[i]);
        return ss;
    }
// *************
// private String name;
// private int[] inds;

// public CncFun(String _name, int[] _inds)
// {name = _name;
//  inds = _inds;
// }

// public String toString()
// {String ss = "Name : "+name + " , Indices : ";
// for(int i=0; i<inds.length; i++)
//  ss+=(" "+inds[i]);
// return ss;
// }

// public String getName(){return name;}
// public int[] getInds(){return inds;}

// ^ ^ ^ ^ ^ ^ ^
}
