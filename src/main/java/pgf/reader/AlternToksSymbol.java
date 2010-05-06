package pgf.reader;

public class AlternToksSymbol extends Symbol {
    String[] toks;
    Alternative[] alts;
    
    public AlternToksSymbol(String[] _toks, Alternative[] _alts) { 
        toks = _toks; alts = _alts; 
    }
    
    public boolean isTerminal() { return true; }
    
    public String toString()
    {
        String s = "Tokens : "+toks + " Alternatives : "+alts;
        return s;	   
    };
    
		}


