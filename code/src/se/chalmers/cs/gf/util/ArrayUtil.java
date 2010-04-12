/*
 * ArrayUtil.java
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

import java.util.*;

/**
 *  Some array utility methods.
 */
public class ArrayUtil {

        private ArrayUtil() {};

        public static int[] toIntArray(Collection<Integer> xs) {
                int[] ys = new int[xs.size()];
                Iterator<Integer> it = xs.iterator();
                int i = 0;
                while (it.hasNext())
                        ys[i++] = it.next();
                return ys;
        }

}
