package org.gf;

import java.util.*;

import se.chalmers.cs.pgf.parse.pmcfg.RHSItem;

public class Production {
	public Category lhs;
	public Category[] domain;
	public RHSItem[][] rhs;
	
	public Production(Category l, Category[] domain_, RHSItem[][] r)
	{
		lhs = l;
		rhs = r;
		domain = Arrays.copyOf(domain_, domain_.length);
	}
	
	public Production(Production pr)
	{
		this(pr.lhs, pr.domain, pr.rhs);
	}
	
	public RHSItem[][] getRightHandSide()
	{
		return rhs;
	}
	
	public Category getLeftHandSide()
	{
		return lhs;
	}
	
}
