/*
 * ModuleType.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.gfcutil;

import se.chalmers.cs.gf.GFC.Absyn.*;

public enum ModuleType { 
        ABSTRACT, CONCRETE, RESOURCE, TRANSFER; 

        public static ModuleType getModuleType(ModType t) {
                return t.accept(new GetModuleType(), null);
        }

        /**
         *  Gets the type of a module.
         */
        private static class GetModuleType implements ModType.Visitor<ModuleType,Object> { 
                public ModuleType visit(MTAbs p, Object arg) { return ABSTRACT; }
                public ModuleType visit(MTCnc p, Object arg) { return CONCRETE; }
                public ModuleType visit(MTRes p, Object arg) { return RESOURCE; }
                public ModuleType visit(MTTrans p, Object arg) { return TRANSFER; }
        }

}
