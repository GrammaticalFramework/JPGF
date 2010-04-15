package org.gf;

public class ParserOutput {
	private ParserState state;
	private AbstractTree tree;
	
	public ParserOutput(AbstractTree t, HoldingSet S, ProductionSet P, ParseAgenda C)
	{
		state = new ParserState(S,P,C);
		tree = t;
	}
	
	public ParserState getState()
	{
		return state;
	}
	
	public AbstractTree getTree()
	{
		return tree;
	}
}
