/* ApplProduction.java
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

public class ApplProduction extends Production {
    public final CncFun function;
    public final int[] domain;
    
    public ApplProduction(int fId, CncFun function, int[] domain) {
        super(0, fId);
        this.function = function;
        this.domain = domain; 
    }

    public CncFun function() {
        return this.function;
    }

    public int[] domain() {
        return this.domain;
    }

    public CncFun getFunction() {
	return function;
    }
    
    public int[] getArgs() {
        return this.domain;
    }
    
    public String toString() {
        // String ss =  "Fuction : "+ function + " Arguments : ["; 
        // for(int i=0; i<domain.length; i++)
        //     ss+=(" " + domain[i]);
        // ss+="]";
        // return ss;
        String s = "";
        s += this.fId;
        s += " -> ";
        s += this.function.name();
        s += "[ ";
        for(int c : this.domain)
            s+= c + " ";
        s += "]";
        return s;
    };   
    
    public boolean equals(Object o)
    {if (o instanceof ApplProduction)
    	{ApplProduction newo = (ApplProduction) o;
    	if(!newo.getFunction().equals(function))
    	   return false;
    	if(domain.length != newo.getArgs().length) return false;
    	for(int i=0; i<domain.length; i++)
    	 if(domain[i] != newo.getArgs()[i]) return false;
    	return true;
    	}
    return false;
    }
}
