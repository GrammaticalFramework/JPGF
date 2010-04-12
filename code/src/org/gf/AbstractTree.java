package org.gf;

class AbstractTree {
	Category cat;
	AbstractTree[] child;
	
	public AbstractTree(Category cat_, AbstractTree[] child_)
	{
		cat = cat_;
		child = child_;
	}

    // Overriding toString 
    public String toString( ) {
        String s = "(" + cat.toString() + " ";
        for (int i = 0 ; i < child.length ; i++)
        {
        	s += " ";
        	s += child[i].toString();
        }
        return s + ")";
    }

}
