package reader;

public abstract class Expr implements java.io.Serializable {
	   int sel;
		public abstract String toString();
	    public abstract <R,A> R accept(Expr.Visitor<R,A> v, A arg);
		public interface Visitor <R,A> {
		    public R visit(reader.LiteralExp p, A arg);
		    //public R visit(reader.IntLiteral p, A arg);
		    //public R visit(reader.FloatLiteral p, A arg);

		  }
	}