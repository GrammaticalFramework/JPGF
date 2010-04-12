/*
 * LinRule.java
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

import se.chalmers.cs.gf.GFC.*;
import se.chalmers.cs.gf.GFC.Absyn.*;

import java.io.Serializable;

/**
 *  Contains the linearization rule for a function.
 */
public class LinRule implements Definition, Serializable {

        /**
         *  Name of the function that this linearization rule is for.
         */
        private String name;

        /**
         *  The list of arguments to this linearization rule.
         */
        private ListArgVar param;

        /**
         *  The linearization rule body.
         */
        private Term term;

        /**
         *  Construct a linearization rule.
         *
         * @param name The name of the function which this rule is for.
         * @param param The formal arguments of the function.
         * @param term The body of the function, as a GF term.
         */
        public LinRule(String name, ListArgVar param, Term term) {
                this.name = name;
                this.param = param;
                this.term = term;
        }

        /**
         *  Get the name of the function which is lineatized.
         */
        public String getName() {
                return name;
        }

        /**
         *  Get the linearization rule body.
         */
        public Term getTerm() {
                return term;
        }

        /**
         *  Get the number of arguments that this linearization rule
         *  takes (the same the number of arguments to 
         *  the corresponing function).
         */
        public int getArity() {
                return param.size();
        }

        public String toString() {
                return name + " " + param + " = " + term;
        }

}
