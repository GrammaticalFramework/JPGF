package reader;

public abstract class Production implements java.io.Serializable {
   int sel;
   int fId;
	public abstract String toString();
    public abstract <R,A> R accept(Production.Visitor<R,A> v, A arg);
	public interface Visitor <R,A> {
	    public R visit(reader.ApplProduction p, A arg);
	    public R visit(reader.CoerceProduction p, A arg);

	  }
}
