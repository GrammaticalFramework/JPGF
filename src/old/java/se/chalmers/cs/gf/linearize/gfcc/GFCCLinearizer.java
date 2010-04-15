/*
 * GFCCLinearizer.java
 * Copyright (C) 2006, Bjorn Bringert (bringert@cs.chalmers.se)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.linearize.gfcc;

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.GFCC.AbstractVisitor;
import se.chalmers.cs.gf.GFCC.ComposVisitor;
import se.chalmers.cs.gf.GFCC.Absyn.*;
import se.chalmers.cs.gf.linearize.Linearizer;
import se.chalmers.cs.gf.parse.Token;
import static se.chalmers.cs.gf.linearize.LinearizeLogger.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *  Create a linearizer for a concrete GFCC module.
 */
public class GFCCLinearizer implements Linearizer {

	private String name;

	private Map<String,Term> lins;

	public GFCCLinearizer(Concrete c) {
		this((Cnc)c);
	}

	public GFCCLinearizer(Cnc c) {
		lins = new HashMap<String,Term>();
		
		name = c.cid_;
		for (CncDef d : c.listcncdef_) {
			Lin l = (Lin)d;
			lins.put(l.cid_, l.term_);
		}
	}

	public List<Token> linearize(Tree t) {
                log.fine("Linearizing with " + getName());
                Term v = t.accept(new TreeLinearizer(), null);
                log.finer("Done: " + v);
                return termToTokens(v);
        }

	public List<Token> linearizeWithRanges(Tree t) {
		log.fine("Linearizing with " + getName());
		Term v = t.accept(new TreeLinearizerWithRanges(), null);
		log.finer("Done: " + v);
                return termToTokens(v);
	}

        public String getName() {
                return name;
        }

        public boolean tryThisOne() {
                return true;
        }
	
	private Term lookup(String name) {
		Term term = lins.get(name);
		return term == null ? new TM() : term;
	}

	private Term interpret(Term t, Term[] args) {
		return t.accept(interpreter, args);
	}

	private Term select(Term arr, Term index) {
		return index.accept(indexVisitor, arr);
	}

	private Term select_int(Term arr, Integer index) {
		return arr.accept(selectVisitor, index);
	}

	private List<Token> termToTokens(Term term) {
		return term.accept(termToTokenVisitor, new LinkedList<Token>());
	}


        /**
         *  Linearizes an abstract syntax tree by recursively applying 
         *  the linearization rules for each function.
         */
        private class TreeLinearizer 
                implements TreeVisitor<Term,Object> {

                public Term visit(se.chalmers.cs.gf.abssyn.Fun t, Object arg) {
                        Tree[] c = t.getChildren();
                        Term[] args = new Term[c.length];
                        for (int i = 0; i < c.length; i++)
                                args[i] = c[i].accept(this, arg);
                        return interpret(lookup(t.getLabel()), args);
                }

                public Term visit(IntLiteral l, Object arg) {
                        return ss(new K(new KS(l.toString())));
                }

                public Term visit(StringLiteral l, Object arg) {
                        return ss(new K(new KS(l.getValue())));
                }

                public Term visit(MetaVariable v, Object arg) {
                        return new TM();
                }

		private Term ss(Term t) {
			ListTerm xs = new ListTerm();
			xs.add(t);
			return new R(xs);
		}

        }

        private class TreeLinearizerWithRanges 
	        extends TreeLinearizer {

                public Term visit(se.chalmers.cs.gf.abssyn.Fun t, Object arg) {
			return addRanges(t, super.visit(t, arg));
                }

                public Term visit(IntLiteral t, Object arg) {
                        return addRanges(t, super.visit(t, arg));
                }

                public Term visit(StringLiteral t, Object arg) {
                        return addRanges(t, super.visit(t, arg));
                }

                public Term visit(MetaVariable t, Object arg) {
			return addRanges(t, super.visit(t, arg));
                }

		private Term addRanges(Tree t, Term v) {
			return wrapTerm(v, "(", "= " + t.inputRangesToString() + ")");
		}

        }

	private Term wrapTerm(Term t, String before, String after) {
		throw new UnsupportedOperationException("GFCCLinearizer.wrapTerm");
	}


	/** Interprets a GFCC term given the values of the $1..$n variables */
	private ComposVisitor<Term[]> interpreter
          	= new ComposVisitor<Term[]>() {
		
		/** Selection */
		public Term visit(P p, Term[] args) {
			return select(p.term_1.accept(this, args),
				      p.term_2.accept(this, args));
		}

		/** Argument variable */
		public Term visit(V p, Term[] args) {
			return args[p.integer_];
		}

		/** Named constant */
		public Term visit(F p, Term[] args) {
			return lookup(p.cid_).accept(this, args);
		}

	};


	/** Visits the index of a selection expression. */
	private Term.Visitor<Term,Term> indexVisitor
		= new AbstractVisitor<Term,Term>() {

		public Term visit(C index, Term arr) { 
			return select_int(arr, index.integer_);
		}
		
		public Term visit(RP index, Term arr) { 
			return select(arr,index.term_1);
		}

		public Term visit(TM index, Term arr) { 
			return select_int(arr, 0);
		}

	};

	/** Visits the array of a selection expression. */
	private Term.Visitor<Term,Integer> selectVisitor
		= new AbstractVisitor<Term,Integer>() {

		public Term visit(R arr, Integer index) { 
			return arr.listterm_.get(index);
		}

		public Term visit(W arr, Integer index) { 
			return new W(arr.string_, select_int(arr.term_, index));
		}

		public Term visit(TM arr, Integer index) { 
			return new TM();
		}	

        };
 

	private Term.Visitor<List<Token>,List<Token>> termToTokenVisitor
	       = new AbstractVisitor<List<Token>,List<Token>>() {

		       /** Record, pick the first */
		       public List<Token> visit(R p, List<Token> rest) {
			       if (p.listterm_.isEmpty()) {
				       // FIXME: fail?
				       return rest;
			       } else {
				       return p.listterm_.get(0).accept(this, rest);
			       }
		       }

		       /** Sequence */
		       public List<Token> visit(S p, List<Token> rest) {
			       ListIterator<Term> it = p.listterm_.listIterator(p.listterm_.size());
			       while (it.hasPrevious()) {
				       rest = it.previous().accept(this, rest);
			       }
			       return rest;
		       }

		       public List<Token> visit(K p, List<Token> rest) {
			       return p.tokn_.accept(this,rest);
		       }

		       public List<Token> visit(KP p, List<Token> rest) {
			       List<String> ss;

			       ss = p.liststring_;

			       ListIterator<String> it = ss.listIterator(ss.size());
			       while (it.hasPrevious()) {
				       rest = cons(new Token(it.previous()), rest);
			       }

			       return rest;
		       }

		       public List<Token> visit(KS p, List<Token> rest) {
			       return cons(new Token(p.string_), rest);
		       }

		       public List<Token> visit(TM p, List<Token> rest) {
			       return cons(new Token("?"), rest);
		       }

		       public List<Token> visit(W p, List<Token> rest) {
			       List<Token> ts = p.term_.accept(this, rest);
			       String t = ts.isEmpty() ? "" : ts.remove(0).getValue();
			       return cons(new Token(p.string_ + t), ts);
		       }
		       
		       /** Variants */
		       public List<Token> visit(FV p, List<Token> rest) {
			       if (p.listterm_.isEmpty()) {
				       // FIXME: fail?
				       return rest;
			       } else {
				       // FIXME: just picks first variant
				       return p.listterm_.get(0).accept(this, rest);
			       }
		       }


		       private List<Token> cons(Token t, List<Token> rest) {
			       rest.add(0,t);
			       return rest;
		       }

	};

}
