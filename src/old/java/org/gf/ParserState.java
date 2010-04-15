package org.gf;
import java.util.*;

public class ParserState {
	
	private HoldingSet S;
	private ParseAgenda A;
	private ProductionSet P;

	public ParserState(HoldingSet s2, ProductionSet p, ParseAgenda c) {
		// TODO Auto-generated constructor stub
		S = s2;
		A = c;
		P = p;
	}
	
	static public ParserState initialParserState(Production[] prods)
	{
		HoldingSet S = new HoldingSet();
		ParseAgenda A = new ParseAgenda();
		for (int i = 0; i < prods.length; i++)
		{
			Production p = prods[i];
			A.add(new ParseItem(0,p,1,0));
		}
		
		ProductionSet P = new ProductionSet(prods);
		
		return new ParserState(S, P, A);
	}

	public Queue<ParseItem> getAgenda()
	{
		return A;
	}
	
	public HoldingSet getS ()
	{
		return S;
	}
}

