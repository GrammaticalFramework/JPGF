/*
 * ClassUtil.java
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
 *  Some utilties for classes and objects.
 */
public class ClassUtil {

        public static Object instantiate(String className) {
                try {
                        Class<?> c = Class.forName(className);
                        return c.newInstance();
                } catch (ClassNotFoundException ex) {
                        throw new GFException("Error instantiating " 
                                              + className + ": " 
                                              + ex.getMessage(), ex);
                } catch (InstantiationException ex) {
                        throw new GFException("Error instantiating " 
                                              + className + ": " 
                                              + ex.getMessage(), ex);
                } catch (IllegalAccessException ex) {
                        throw new GFException("Error instantiating " 
                                              + className + ": " 
                                              + ex.getMessage(), ex);
                }
        }

        public static List<Object> instantiateAll(String[] classes) {
                List<Object> cs = new LinkedList<Object>();
                for (String n : classes) {
                        try {
                                cs.add(instantiate(n));
                        } catch (GFException ex) {
                                System.err.println(ex.toString());
                        }
                }
                return cs;
        }

}
