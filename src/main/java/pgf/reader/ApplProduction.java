package reader;

public class ApplProduction extends Production {
    CncFun function;
    int[] args;
    
    public ApplProduction(CncFun function, int[] _args) { 
	this.function = function;
	args = _args; 
    }
    
    public String toString() {
	String ss =  "Fuction : "+ function + " Arguments : ["; 
	for(int i=0; i<args.length; i++)
	    ss+=(" "+args[i]);
	ss+="]";
	return ss;
    };   
}
