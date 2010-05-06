package pgf.reader;

public abstract class Production {
   private int sel;
   private int fId;
	public abstract String toString();
	public Production(int selector, int fId) {
		this.sel = selector;
		this.fId = fId;
    }
public int getSel() {return sel;}
public int getFId() {return fId;}
	  }
