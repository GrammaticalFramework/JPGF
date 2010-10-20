package org.grammaticalframework.reader;

public class LinDef {
    private final int key;
    private final int[] funIds;

    public LinDef (int key, int[] funIds) {
	this.key = key;
	this.funIds = funIds;
    }
}