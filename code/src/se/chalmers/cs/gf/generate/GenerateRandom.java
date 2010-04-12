/*
 * GenerateRandom.java
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
package se.chalmers.cs.gf.generate;

import se.chalmers.cs.gf.abssyn.Tree;
import se.chalmers.cs.gf.GFCC.GFCCParser;
import se.chalmers.cs.gf.GFCC.Absyn.*;
import se.chalmers.cs.gf.parse.Token;
import se.chalmers.cs.gf.linearize.gfcc.*;
import se.chalmers.cs.gf.linearize.unlex.*;
import se.chalmers.cs.gf.util.FileUtil;
import se.chalmers.cs.gf.util.MultiMap;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GenerateRandom {

	private Random random;

	private MultiMap<String,Fun> rules;

	public GenerateRandom(Grammar g) {
		random = new Random();

		rules = new MultiMap<String,Fun>();

		Abs abs = (Abs)((Grm)g).abstract_;
		for (AbsDef d : abs.listabsdef_) {
			Fun f = (Fun)d;
			String cat = ((Typ)f.type_).cid_;
			rules.put(cat, f);
		}
	}

	public Tree generateTree(String cat) {
		if (cat.equals("Integer")) {
			return new se.chalmers.cs.gf.abssyn.StringLiteral(generateString());
		} else if (cat.equals("String")) {
			return new se.chalmers.cs.gf.abssyn.IntLiteral(generateInt());
		} else {
			Set<Fun> fs = rules.get(cat);
			Fun[] fsa = fs.toArray(new Fun[fs.size()]);
			Fun f = fsa[random.nextInt(fsa.length)];
			List<String> ccats = ((Typ)f.type_).listcid_;
			int arity = ccats.size();
			if (arity > 0) {
				Tree[] cs = new Tree[arity];
				int i = 0;
				for (String ccat : ccats) {
					cs[i++] = generateTree(ccat);
				}
				return new se.chalmers.cs.gf.abssyn.Fun(f.cid_, cs);
			} else {
				return new se.chalmers.cs.gf.abssyn.Fun(f.cid_);
			}
		}
	}
			   
	public List<Tree> generateMany(String cat, int n) {
		List<Tree> ts = new ArrayList<Tree>(n);
		for (int i = 0; i < n; i++) {
			ts.add(generateTree(cat));
		}
		return ts;
	}

	private String generateString() {
		String[] ss = { "x", "y", "foo", "bar" };
		return ss[random.nextInt(ss.length)];
	}

	private int generateInt() {
		return random.nextInt(100000);
	}



	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.err.println("Usage: GenerateRandom <gfcc file> <category> <number>");
			System.exit(2);
		}
		String file = args[0];
		String startcat = args[1];
		int number = Integer.parseInt(args[2]);

		Reader in = FileUtil.readResource(file, "UTF-8");
		Grammar gfcc = GFCCParser.parse(in);
		GFCCLinearizers ls = new GFCCLinearizers(gfcc);
		GenerateRandom gen = new GenerateRandom(gfcc);
		Unlexer unlexer = new SimpleUnlexer();

		for (int i = 0; i < number; i++) {
			Tree t = gen.generateTree(startcat);
			for (String l : ls.listLinearizers()) {
				List<Token> ts = ls.getLinearizer(l).linearize(t);
				String s = unlexer.unlex(ts);
				System.out.println(s);
			}
		}
	}

}
