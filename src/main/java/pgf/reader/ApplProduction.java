package pgf.reader;

public class ApplProduction extends Production {
    // the index of the function in the functions array
    // FIXME : why not a pointer to the function object itself ??
    int ind;
    // list of arguments. Each integer is a forest id
    int[] args;
    
    public ApplProduction(int _ind, int[] _args) { ind = _ind; args = _args; }
    
    public String toString() {
	String ss =  "Index : "+ind + " Arguments : ["; 
	for(int i=0; i<args.length; i++)
	    ss+=(" "+args[i]);
	ss+="]";
	return ss;
    };
    
    public <R,A> R accept(reader.Production.Visitor<R,A> v, A arg) { 
	return v.visit(this, arg);
    }
    
    public boolean equals(Object o) {
	if (this == o) return true;
	if (o instanceof reader.ApplProduction) {
	    return true;
	    // Why ?
	}
	return false;
    }
}
