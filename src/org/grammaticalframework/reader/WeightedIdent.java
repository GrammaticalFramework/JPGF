package org.grammaticalframework.reader;

public class WeightedIdent {
    private final Double weight;
    private final String ident;

    public WeightedIdent(String i, Double weight) {
	this.ident = i;
	this.weight = weight;
    }

    public String ident() {
	return this.ident;
    }

    public double weight() {
	return this.weight;
    }

}