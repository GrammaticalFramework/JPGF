package pgf.reader;

public abstract class Symbol implements java.io.Serializable {
   int sel;
	public abstract String toString();
    public abstract <R,A> R accept(Symbol.Visitor<R,A> v, A arg);
	public interface Visitor <R,A> {
	    public R visit(reader.ArgConstSymbol p, A arg);
	    public R visit(reader.ToksSymbol p, A arg);
	    public R visit(reader.AlternToksSymbol p, A arg);

	  }
}
