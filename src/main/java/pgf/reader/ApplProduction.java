package pgf.reader;

public class ApplProduction extends Production {
    private CncFun function;
    private int[] domain;
    
    public ApplProduction(int fId, CncFun function, int[] domain) {
        super(0, fId);
        this.function = function;
        this.domain = domain; 
    }

    public CncFun function() {
        return this.function;
    }

    public int[] domain() {
        return this.domain;
    }

    public CncFun getFunction() {
	return function;
    }
    
    public int[] getArgs() {
        return this.domain;
    }
    
    public String toString() {
        // String ss =  "Fuction : "+ function + " Arguments : ["; 
        // for(int i=0; i<domain.length; i++)
        //     ss+=(" " + domain[i]);
        // ss+="]";
        // return ss;
        String s = "";
        s += this.fId;
        s += " -> ";
        s += this.function.name();
        s += "[ ";
        for(int c : this.domain)
            s+= c + " ";
        s += "]";
        return s;
    };   
    
    public boolean equals(Object o)
    {if (o instanceof ApplProduction)
    	{ApplProduction newo = (ApplProduction) o;
    	if(!newo.getFunction().equals(function))
    	   return false;
    	if(domain.length != newo.getArgs().length) return false;
    	for(int i=0; i<domain.length; i++)
    	 if(domain[i] != newo.getArgs()[i]) return false;
    	return true;
    	}
    return false;
    }
}
