/* RezDesc.java
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
package org.grammaticalframework.linearizer;

import java.util.Vector;

public class RezDesc {
private int fid;
private Vector<CncType> cncTypes;
private Vector<Vector<Vector<BracketedTokn>>> bss;

public RezDesc(int _fid, Vector<CncType> _cncTypes, Vector<Vector<Vector<BracketedTokn>>> _bss)
{fid = _fid; cncTypes = _cncTypes; bss = _bss;}

public int getFid() {return fid;}
public Vector<CncType> getCncTypes(){return cncTypes;}
public Vector<Vector<Vector<BracketedTokn>>> getBracketedTokens() {return bss;}
}

