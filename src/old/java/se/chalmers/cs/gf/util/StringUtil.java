/*
 * StringUtil.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
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
package se.chalmers.cs.gf.util;

import se.chalmers.cs.gf.GFException;

import java.util.*;

/**
 *  Some string utility methods.
 */
public class StringUtil {

        private StringUtil() {};

        public static String join(CharSequence glue, Iterable<?> xs) {
                StringBuilder sb = new StringBuilder();
                Iterator<?> it = xs.iterator();
                while (it.hasNext()) {
                        sb.append(it.next());
                        if (it.hasNext())
                                sb.append(glue);
                }
                return sb.toString();
        }

        public static String[] splitAtCommas(String in) {
                if (in == null)
                        return new String[0];
                String[] ss = in.split("\\s*,\\s*");
                List<String> l = new ArrayList<String>(ss.length);
                for (String s : ss)
                        if (s.length() > 0)
                                l.add(s);
                return l.toArray(new String[l.size()]);
        }

        public static String singleUnquote(String s) {
                if (s.length() < 2 
                    || s.charAt(0) != '\'' 
                    || s.charAt(s.length()-1) != '\'')
                        throw new GFException("Badly formatted single-quote escaped string: \"" + s + "\"");

                // FIXME: inefficient, recompiles regexps and 
		// creates lots of String objects
                s = s.substring(1,s.length()-1);
                s = s.replaceAll("\\\\\\\\","\\\\");
                s = s.replaceAll("\\\\'","'");
                return s;
        }

	public static String singleQuote(String s) {
                // FIXME: inefficient, recompiles regexps and 
		// creates lots of String objects
		s = s.replaceAll("\\\\","\\\\\\\\");
                s = s.replaceAll("'","\\\\'");
		return "'" + s + "'";
	}

}
