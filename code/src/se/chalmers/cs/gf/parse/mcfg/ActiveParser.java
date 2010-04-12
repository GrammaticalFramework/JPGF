/*
 * ActiveParser.java
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
package se.chalmers.cs.gf.parse.mcfg;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

import se.chalmers.cs.gf.parse.Token;
import se.chalmers.cs.gf.util.FileUtil;
import se.chalmers.cs.gf.util.MultiMap;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.util.Pair.pair;

import static se.chalmers.cs.gf.parse.mcfg.Record.rec;

public class ActiveParser {
	
	private List<Rule> emptyRules;
	/** Rules starting with terminals. */
	private MultiMap<String,Rule> terminalRules;
	/** Rules starting with non-terminals. */
	private MultiMap<Category,Rule> nonTerminalRules;

	public ActiveParser(Grammar grammar) {
		emptyRules = new ArrayList<Rule>();
		terminalRules = new MultiMap<String,Rule>();
		nonTerminalRules = new MultiMap<Category,Rule>();

		for (Rule r : grammar.getRules()) {
			Rule.Symbol l = r.getLeftCorner();
			if (l == null) {
				emptyRules.add(r);
			} else if (l.isTerminal()) {
				Rule.Terminal t = (Rule.Terminal)l;
				terminalRules.put(t.getValue(), r);
			} else {
				Rule.Projection p = (Rule.Projection)l;
				nonTerminalRules.put(p.getCategory(), r);
			}
		}
	}


	public static void main(String[] args) throws java.io.IOException {
		if (args.length != 3) {
			System.err.println("Usage: java ActiveParser <mcfgm file> <grammar name> <input string>");
			System.exit(1);
		}

		String grammarFile = args[0];
		String grammarName = args[1];
		String input = args[2];

		MCFGGrammars gs = MCFGGrammars.readMCFGM(grammarFile);
		Grammar g = gs.getGrammar(grammarName);

		ActiveParser p = new ActiveParser(g);
		se.chalmers.cs.gf.parse.lex.Lexer lexer 
			= new se.chalmers.cs.gf.parse.lex.SimpleLexer();
		List<Token> ts = lexer.lex(input);
		State s = p.parse(ts);
		System.out.println("Active items:");
		for (ActiveItem i : s.getActive()) {
			System.out.println(i);
		}
		System.out.println("Passive items:");
		Collection<PassiveItem> ps = s.getAllPassive();
		for (PassiveItem i : ps) {
			System.out.println(i);
		}

		System.out.println("Number of active items:  " + s.getActive().size());
		System.out.println("Number of passive items: " + ps.size());

		Range entire = new Range(0, ts.size());
		PassiveItem found = new PassiveItem(g.getStartCat(), 
						    rec(pair("s",entire)));
		if (s.getPassive(g.getStartCat()).contains(found)) {
			System.out.println("Parse succeeded!");
		} else {
			System.out.println("Parse failed!");
		}
	}


	public State parse(List<Token> input) {
		State s = new State();
		boolean changed;
		predictEmpty(s);
		predictTerminal(input, s);
		do {
			changed = false;
			if (predict(s))
				changed = true;
			if (completeAndConvert(s))
				changed = true;
			if (scan(input, s))
				changed = true;
			if (combine(s))
				changed = true;
		} while (changed);
		return s;
	}

	/**
	 *  Do prediction for all empty rules.
	 */
	private void predictEmpty(State s) {
		for (Rule r : emptyRules)
			s.addActive(new ActiveItem(r));
	}

	/**
	 *  Do prediction for all rules that start with a terminal which is
	 *  in the input.
	 */
	private void predictTerminal(List<Token> input, State s) {
		Set<Token> tokens = new HashSet<Token>(input);
		for (Token t : tokens) {
			if (t.isWord()) {
				for (Rule r : terminalRules.get(t.getValue()))
					s.addActive(new ActiveItem(r));
			} else {
				throw new RuntimeException(
					"The MCFG parser does not yet support literals.");
			}
		}
	}

	/**
	 *  Do prediction for all rules that start with a non-terminal, such 
	 *  that there is already a passive item with that non-terminal.
	 */
	private boolean predict(State s) {
		Set<ActiveItem> newActive = new HashSet<ActiveItem>();
		for (Category c : nonTerminalRules.keySet())
			if (!s.getPassive(c).isEmpty())
				for (Rule r : nonTerminalRules.get(c))
					newActive.add(new ActiveItem(r));
		return s.addActive(newActive);
	}

	/**
	 *  Complete and convert. These are combined since they share so
	 *  much code.
	 */
	private boolean completeAndConvert(State s) {
		Set<PassiveItem> newPassive = new HashSet<PassiveItem>();
		Set<ActiveItem> newActive = new HashSet<ActiveItem>();
		for (ActiveItem a : s.getActive()) {
			if (a.getCurrentRest().isEmpty()) {
				Record<Range> foundRows = a.getFoundRows().copy();
				foundRows.put(a.getCurrentField(), a.getCurrentRange());
				if (a.getRemainingRows().isEmpty()) {
					// convert: turn a finished active item into
					// a passive item
					newPassive.add(new PassiveItem(a.getRule().getCategory(), foundRows));
				} else {
					// complete: move on to the next row
					Record<List<Rule.Symbol>> rem = a.getRemainingRows().copy();
					Pair<String,List<Rule.Symbol>> p = rem.removeFirst();
					ActiveItem i = new ActiveItem(a.getRule(), foundRows, p.fst,
								      Range.EMPTY, p.snd, rem,
								      a.getDaughters());
					newActive.add(i);
				}
			}
		}
		boolean changed = false;
		if (s.addPassive(newPassive))
			changed = true;
		if (s.addActive(newActive))
			changed = true;
		return changed;
	}

	/**
	 *  Scan: handle items looking for terminals.
	 */
	private boolean scan(List<Token> input, State s) {
		Set<ActiveItem> newActive = new HashSet<ActiveItem>();
		for (ActiveItem a : s.getActive()) {
			if (!a.getCurrentRest().isEmpty()) {
				Rule.Symbol sym = a.getCurrentRest().get(0);
				if (sym.isTerminal()) {
					Rule.Terminal t = (Rule.Terminal)sym;
					for (Range r : rangeRestriction(t, input)) {
						ActiveItem i = a.step(r);
						if (i != null) 
							newActive.add(i);
					}
				}
			}
		}
		return s.addActive(newActive);
	}

	/**
	 *  Combine passive items with active items looking for them.
	 */
	private boolean combine(State s) {
		Set<ActiveItem> newActive = new HashSet<ActiveItem>();
		for (ActiveItem a : s.getActive()) {
			if (!a.getCurrentRest().isEmpty()) {
				Rule.Symbol sym = a.getCurrentRest().get(0);
				if (!sym.isTerminal()) {
					Rule.Projection pr = (Rule.Projection)sym;
					int i = pr.getArg();
					Record<Range> gi = a.getDaughters().get(i);
					Category b = pr.getCategory();
					for (PassiveItem p : s.getPassive(b)) {
						Record<Range> g = p.getRanges();
						Range r = g.lookup(pr.getLabel());
						if (gi.isEmpty()) {
							ActiveItem x = a.step(r, i, g);
							if (x != null) 
								newActive.add(x);
						} else if (gi.equals(g)) {
							ActiveItem x = a.step(r);
							if (x != null) 
								newActive.add(x);
						} 
						// else not adding, since not (Gi <= G')
					}
				}
			}
		}
		return s.addActive(newActive);
	}


	//
	// Parser state
	//

	private static class State {
		private MultiMap<Category,PassiveItem> passive;
		private Set<ActiveItem> active;
		public State() {
			this.passive = new MultiMap<Category,PassiveItem>();
			this.active = new HashSet<ActiveItem>();
		}
		public Set<PassiveItem> getPassive(Category c) { return passive.get(c); }
		public Collection<PassiveItem> getAllPassive() { return passive.values(); }

		public Set<ActiveItem> getActive() { return active; }
		public boolean addPassive(PassiveItem item) {
			return passive.put(item.getCategory(), item);
		}
		public boolean addPassive(Collection<PassiveItem> items) {
			boolean changed = false;
			for (PassiveItem item : items)
				if (addPassive(item))
					changed = true;
			return changed;
		}
		public boolean addActive(ActiveItem item) {
			return active.add(item);
		}
		public boolean addActive(Collection<ActiveItem> items) {
			return active.addAll(items);
		}
	}

	//
	// Items
	//

	public static class PassiveItem {
		private Category category;
		private Record<Range> ranges;
		public PassiveItem(Category category, Record<Range> ranges) {
			this.category = category;
			this.ranges = ranges;
		}
		public Category getCategory() { return category; }
		public Record<Range> getRanges() { return ranges; }

		public boolean equals(Object o) {
			return o instanceof PassiveItem && equals((PassiveItem)o);
		}
		public boolean equals(PassiveItem p) {
			return category.equals(p.category) 
				&& ranges.equals(p.ranges);
		}
		public int hashCode() {
			return category.hashCode() + ranges.hashCode();
		}

		public String toString() {
			return "[" + category + "; " + ranges + "]";
		}
	}

	public static class ActiveItem {
		private Rule rule;
		private Record<Range> foundRows;
		private String currentField;
		private Range currentRange;
		private List<Rule.Symbol> currentRest;
		private Record<List<Rule.Symbol>> remainingRows;
		private List<Record<Range>> daughters;

		public ActiveItem(Rule rule) {
			this.rule = rule;
			this.foundRows = new Record<Range>();
			Record<List<Rule.Symbol>> rows = rule.getRhs().copy();
			Pair<String,List<Rule.Symbol>> e = rows.removeFirst();
			this.currentField = e.fst;
			this.currentRange = Range.EMPTY;
			this.currentRest = e.snd;
			this.remainingRows = rows;
			int n = rule.getChildCats().size();
			this.daughters = new ArrayList<Record<Range>>(n);
			for (int i = 0; i < n; i++)
				this.daughters.add(new Record<Range>());
		}

		public ActiveItem(Rule rule, Record<Range> foundRows, String currentField,
				  Range currentRange, List<Rule.Symbol> currentRest, 
				  Record<List<Rule.Symbol>> remainingRows, 
				  List<Record<Range>> daughters) {
			assert (rule != null);
			assert (foundRows != null);
			assert (currentField != null);
			assert (currentRange != null);
			assert (currentRest != null);
			assert (remainingRows != null);
			assert (daughters != null);

			this.rule = rule;
			this.foundRows = foundRows;
			this.currentField = currentField;
			this.currentRange = currentRange;
			this.currentRest = currentRest;
			this.remainingRows = remainingRows;
			this.daughters = daughters;
		}

		public Rule getRule() { return rule; }
		public Record<Range> getFoundRows() { return foundRows; }
		public String getCurrentField() { return currentField; }
		public Range getCurrentRange() { return currentRange; }
		public List<Rule.Symbol> getCurrentRest() { return currentRest; }
		public Record <List<Rule.Symbol>> getRemainingRows() { return remainingRows; }
		public List<Record<Range>> getDaughters() { return daughters; }

		/** Move the dot one step to the right in the list of symbols,
		    marking the symbol as found in the given range. This returns
		    a new ActiveItem, and does not change this one. */
		public ActiveItem step(Range r) {
			Range newRange = concat(currentRange, r);
			if (newRange == null)
				return null;

			int n = currentRest.size();
			List<Rule.Symbol> newCurrentRest = new ArrayList<Rule.Symbol>(n-1);
			for (int i = 1; i < n; i++) {
				newCurrentRest.add(currentRest.get(i));
			}
			return new ActiveItem(rule, foundRows, currentField, newRange,
					      newCurrentRest, remainingRows, daughters);
		}

		public ActiveItem step(Range r, int i, Record<Range> g) {
			ActiveItem a = step(r);
			if (a == null)
				return null;
			a.daughters = new ArrayList<Record<Range>>(a.daughters);
			a.daughters.set(i,g);
			return a;
		}

		public boolean equals(Object o) {
			return o instanceof ActiveItem && equals((ActiveItem)o);
		}
		public boolean equals(ActiveItem o) {
			return rule.equals(o.rule) // FIXME: could use ==?
				&& foundRows.equals(o.foundRows)
				&& currentField.equals(o.currentField)
				&& currentRange.equals(o.currentRange)
				&& currentRest.equals(o.currentRest)
				&& remainingRows.equals(o.remainingRows)
				&& daughters.equals(o.daughters);
		}
		public int hashCode() {
			return rule.hashCode() 
				+ foundRows.hashCode() 
				+ currentField.hashCode()
				+ currentRange.hashCode() 
				+ currentRest.hashCode() 
				+ remainingRows.hashCode() 
				+ daughters.hashCode();
		}

		public String toString() {
			return "[" + rule + "; " + foundRows + ", " 
				+ currentField + "=" + currentRange + "." + currentRest
				+ ", " + remainingRows + "; " + daughters + "]";
		}
	}


        //
        // Ranges
        //
        
        public static class Range {
		public static final Range EMPTY = new EmptyRange(); 
                private int start, end;
                public Range(int start, int end) { 
                        this.start = start;
                        this.end = end;
                }
		public boolean isEmpty() { return false; }
		public int getStart() {	return start; }
		public int getEnd() { return end; }

		public boolean equals(Object o) {
			return o instanceof Range && equals((Range)o);
		}
		public boolean equals(Range o) {
			return start == o.start && end == o.end;
		}
		public int hashCode() {
			return (start << 16) ^ end;
		}
		public String toString() {
			return "(" + start + "," + end + ")";
		}

		private static class EmptyRange extends Range {
			public EmptyRange() { super(-1,-1); }
			public boolean isEmpty() { return true; }
			public int getStart() {
				throw new IllegalStateException("getStart() on empty range");
			}
			public int getEnd() { 
				throw new IllegalStateException("getEnd() on empty range");
			}
			public String toString() {
				return "<empty>";
			}
		}
        }

        /** Return a range which is the concatenation
         *  of two ranges. 
         *  @return null if the ranges are not adjacent.
         */
        public static Range concat(Range r1, Range r2) {
		if (r1.isEmpty()) {
			return r2;
		} else if (r2.isEmpty()) {
			return r1;
		} else if (r1.getEnd() == r2.getStart()) {
			return new Range(r1.getStart(),r2.getEnd());
		} else {
			return null;
		}
        }

	private static List<Range> rangeRestriction(Rule.Terminal t, List<Token> input) {
		int i = 0;
		List<Range> rs = new ArrayList<Range>();
		for (Token it : input) {
			if (t.matches(it)) {
				rs.add(new Range(i, i+1));
			}
			i++;
		}
		return rs;
	}

}
