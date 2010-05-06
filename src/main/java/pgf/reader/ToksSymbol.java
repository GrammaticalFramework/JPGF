package pgf.reader;

public class ToksSymbol extends Symbol {
    String[] toks ;
    
    /**
     * Constructor
     */
    public ToksSymbol(String[] _toks) {
        toks = _toks;
    }

    /**
     * Accessors
     */
    public String [] tokens() {
        return this.toks;
    }
    
    public boolean isTerminal() { return true; }
    
    public String toString()
    {
        String s = "Tokens : ";
        for(int i=0; i<toks.length; i++)
            s+=(" "+toks[i]);  
        return s;	   
    }
}
