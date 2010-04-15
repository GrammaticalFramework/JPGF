package org.gf;

public class Category {
	private String name;
	private int	dimention;
	private boolean fresh;
	
	public Category(String n, int d)
	{
		name = n;
		dimention = d;
	}
	
	public Category() {}
	
	static public Category generateFreshCategory()
	{
		return new Category();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDimention()
	{
		return dimention;
	}
	
	public boolean isFresh()
	{
		return fresh;
	}
}
