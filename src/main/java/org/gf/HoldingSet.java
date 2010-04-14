package org.gf;

import java.util.*;
	
class HoldingSet {
	private ArrayList<Dictionary<Category,ArrayList<List<Pair<ParseItem,Integer>>>>> storage;
	
	public HoldingSet()
	{
		storage = new ArrayList<Dictionary<Category,ArrayList<List<Pair<ParseItem,Integer>>>>>();
	}
	
	@SuppressWarnings("unchecked")
	public Pair<ParseItem,Integer>[] getAll(int j, Category N, int d)
	{
		Dictionary<Category,ArrayList<List<Pair<ParseItem,Integer>>>> S_j = storage.get(j);
		if (S_j == null)
			return new Pair[] {};
		ArrayList<List<Pair<ParseItem,Integer>>> arr = S_j.get(N);
		if (arr == null)
			return new Pair[] {};
		List<Pair<ParseItem,Integer>> l = arr.get(d);
		if (l == null)
			return new Pair[] {};
		return (Pair<ParseItem, Integer>[]) l.toArray();
	}
	
	//@SuppressWarnings("unchecked")
	public Triple<ParseItem, Integer, Integer>[] getAll(int j, Category N)
	{
		Dictionary<Category,ArrayList<List<Pair<ParseItem,Integer>>>> S_j = storage.get(j);
		if (S_j == null)
			return new Triple[0];
		ArrayList<List<Pair<ParseItem,Integer>>> arr = S_j.get(N);
		if (arr == null)
			return new Triple[0];

		int resultLength = 0;
		for (int d = 0 ; d < arr.size() ; d++) 
			resultLength += arr.get(d).size();
		Triple<Integer,ParseItem, Integer>[] result = new Triple[resultLength];
		int currIndex = 0;
		for (int d = 0 ; d < arr.size() ; d++) {
			for (int k = 0; k < arr.get(d).size(); k++ ) {
				Pair<ParseItem,Integer> p = arr.get(d).get(k);
				result[currIndex] = new Triple<Integer,ParseItem, Integer>(new Integer(d), p.fst, p.snd );
				currIndex++;
			}
		}
		return result;
	}

	
	public boolean has(Category N, int l)
	{
		// TODO
		return false;
	}
	
	public void add(Category N, int l, ParseItem x, int d) 
	{
		// TODO
	}

	public boolean has(Category bd, int r, ParseItem x, int d) {
		// TODO Auto-generated method stub
		return false;
	}

	public void add(int k, Category bd, int r, ParseItem x, int d) {
		// TODO Auto-generated method stub
		
	}

}
