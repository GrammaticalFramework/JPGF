/* AppResult.java
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

import org.grammaticalframework.reader.CncFun;

public class AppResult {
    private CncFun cncFun;
    private CncType cncType;
    private Vector<CncType> cncTypes;
    
    public AppResult(CncFun _cncFun, CncType _cncType, Vector<CncType> _cncTypes)
    {
	cncFun = _cncFun; cncType = _cncType; cncTypes = _cncTypes;
    }
    public CncFun getCncFun() {return cncFun;}
    public CncType getCncType() {return cncType;}
    public Vector<CncType> getCncTypes() {return cncTypes;}
}
