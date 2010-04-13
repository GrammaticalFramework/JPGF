package reader;

public abstract class Literal implements java.io.Serializable {
   int sel;
	public abstract String toString();
    public abstract <R,A> R accept(Literal.Visitor<R,A> v, A arg);
	public interface Visitor <R,A> {
	    public R visit(reader.StringLiteral p, A arg);
	    public R visit(reader.IntLiteral p, A arg);
	    public R visit(reader.FloatLiteral p, A arg);

	  }
}
