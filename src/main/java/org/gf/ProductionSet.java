package org.gf;

class ProductionSet extends MultiDictionary<Category, Production> 
{	
	public ProductionSet(Production[] prods)
	{
		for (int i = 0; i < prods.length; i++)
		{
			Production p = prods[i];
			add(p.getLeftHandSide(), p);
		}
	}

	public Production[] getAll(Category bd) {
		// TODO Auto-generated method stub
		return null;
	}

	public Production[] getAll(Category bd) {
		// TODO Auto-generated method stub
		return null;
	}
}
