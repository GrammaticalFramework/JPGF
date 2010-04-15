/*
 * TextUnlexer.java
 * Copyright (C) 2004-2006, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.linearize.unlex;

import se.chalmers.cs.gf.linearize.gfvalue.*;
import se.chalmers.cs.gf.parse.Token;

import java.util.List;
import java.util.regex.Pattern;

/**
 *  An unlexer that has some heuristics for where to
 *  insert spaces. Also makes letters after periods,
 *  question marks and exclamation marks upper case.
 */
public class TextUnlexer extends SimpleUnlexer {

	protected String format(Token before, Token t, Token after) {
		String s = t.getValue();
		if (endsSentence(before)) {
			s = firstToUpper(s);
		}
		return s + separator(t,after);
	}

	private String firstToUpper(String s) {
		if (s.length() == 0)
			return s;
		return Character.toUpperCase(s.charAt(0))
			+ s.substring(1);
	}

        private Pattern endsSentencePatt = Pattern.compile("[\\.\\!\\?]$"); 
        private boolean endsSentence(Token t) {
                return t == null || endsSentencePatt.matcher(t.getValue()).find();
        }

}
