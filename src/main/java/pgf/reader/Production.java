package pgf.reader;

public abstract class Production {

    private int sel;
    protected int fId;
    
    public Production(int selector, int fId) {
	this.sel = selector;
	this.fId = fId;
    }

    public int getCategory() {
        return this.fId;
    }
    
    public int range() { return this.fId; }
    
    public abstract String toString();
    
    // Domain is the domain of the concrete function
    public abstract int[] domain();

    public int getSel() {return sel;}
    public int getFId() {return fId;}

}
