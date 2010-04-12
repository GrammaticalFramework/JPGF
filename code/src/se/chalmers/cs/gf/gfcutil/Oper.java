/*
 * Oper.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
import se.chalmers.cs.gf.GFException;

import java.util.*;
import java.io.Serializable;

/**
 *  Represents a GFC oper.
 */
public class Oper implements Definition, Serializable {
        
        private String name;

        private Term term;

        public Oper(String name, Term term) {
                this.name = name;
		this.term = term;
        }

        /**
         *  Get the name of this oper.
         */
        public String getName() {
                return name;
        }

        /**
         *  Get the value of this oper.
         */
        public Term getTerm() {
                return term;
        }

        public boolean equals(Object o) {
                return o instanceof Oper && equals((Oper)o);
        }

        public boolean equals(Oper f) {
                return name.equals(f.name) 
                        && term.equals(f.term);
        }

        public int hashCode() {
                return name.hashCode() + term.hashCode();
        }

}
