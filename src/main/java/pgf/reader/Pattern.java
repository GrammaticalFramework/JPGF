package pgf.reader;

public abstract class Pattern implements java.io.Serializable {
	   int sel;
	
	    public abstract <R,A> R accept(Pattern.Visitor<R,A> v, A arg);
	 
	    public abstract String toString();
	    
	    public interface Visitor <R,A> {
		    public R visit(reader.AppPattern p, A arg);
		    public R visit(reader.VarPattern p, A arg);
		    public R visit(reader.VarAsPattern p, A arg);
		    public R visit(reader.WildCardPattern p, A arg);
		    public R visit(reader.LiteralPattern p, A arg);
		    public R visit(reader.ImpArgPattern p, A arg);
		    public R visit(reader.InaccPattern p, A arg);
		   

		  }
	}