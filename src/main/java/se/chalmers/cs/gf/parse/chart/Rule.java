/*
 * Rule.java
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
package se.chalmers.cs.gf.parse.chart;

import se.chalmers.cs.gf.abssyn.*;

import java.util.*;

public abstract class Rule {

        private Category cat;


        public Rule(Category cat) {
                this.cat = cat;
        }

        /**
         *  Get the left hand side of the rule.
         */
        public Category getCategory() {
                return cat;
        }

        /**
         *  Get the function name use to construct an abstract syntax
         *  tree from this rule.
         */
        public abstract String getFun();

        /**
         *  Get the number of terminals or non-terminal on the RHS.
         */ 
        public abstract int getSize();

}
