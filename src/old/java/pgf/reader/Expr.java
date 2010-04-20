package reader;

public abstract class Expr implements java.io.Serializable {
	   int sel;
		public abstract String toString();
	    public abstract <R,A> R accept(Expr.Visitor<R,A> v, A arg);
		public interface Visitor <R,A> {
		    public R visit(reader.LiteralExp p, A arg);
		    public R visit(reader.LambdaExp p, A arg);
		    public R visit(reader.AppExp p, A arg);
		    public R visit(reader.MetaExp p, A arg);
		    public R visit(reader.AbsNameExp p, A arg);
		    public R visit(reader.VarExp p, A arg);
		    public R visit(reader.TypedExp p, A arg);
		    public R visit(reader.ImplExp p, A arg);
		   
		  }
	}