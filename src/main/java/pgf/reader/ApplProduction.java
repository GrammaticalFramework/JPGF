package pgf.reader;

public class ApplProduction extends Production {
    private CncFun function;
    private int[] args;
    
    public ApplProduction(int fId, CncFun function, int[] _args) {
	super(0, fId);
	this.function = function;
	args = _args; 
    }

    public CncFun function() {
	return this.function;
    }
    
    public String toString() {
	String ss =  "Fuction : "+ function + " Arguments : ["; 
	for(int i=0; i<args.length; i++)
	    ss+=(" "+args[i]);
	ss+="]";
	return ss;
    };   
}
