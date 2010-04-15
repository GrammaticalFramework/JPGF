package org.gf;

import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ArrayList;

class MultiDictionary<K,V> {
	private Dictionary<K,List<V>> storage;
	
	public MultiDictionary()
	{
		storage = new Hashtable<K,List<V>>();
	}
	
	public void add(K key, V value)
	{
		List<V> xs = storage.get(key);
		if (xs == null) 
			xs = new ArrayList<V>();
		else if (xs.contains(value))
			return;
		xs.add(value);
		storage.put(key, xs);
	}

	public void remove(K key, V value)
	{
		List<V> xs = storage.get(key);
		if (xs == null) 
			return;
		xs.remove(value);
		storage.put(key, xs);
	}
}
