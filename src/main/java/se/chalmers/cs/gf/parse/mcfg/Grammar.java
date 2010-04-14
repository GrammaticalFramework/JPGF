/*
 * Grammar.java
 * Copyright (C) 2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.parse.mcfg;

import java.util.ArrayList;
import java.util.List;

public class Grammar {

	private String name;
	private Category start;
	private List<Rule> rules;

	public Grammar(String name, Category start, List<Rule> rules) {
		this.name = name;
		this.start = start;
		this.rules = rules;
	}

	public Grammar(String name, Category start) {
		this(name, start, new ArrayList<Rule>());
	}

	public String getName() { return name; }
	public Category getStartCat() { return start; }

	public void addRule(Rule r) {
		rules.add(r);
	}

	/**
	 *  Get the list of rules in the grammar. 
	 *  Do not modify the returned list.
	 */
	public List<Rule> getRules() {
		return rules;
	}

}
