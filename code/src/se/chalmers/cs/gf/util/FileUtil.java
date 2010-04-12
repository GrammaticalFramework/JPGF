/*
 * FileUtil.java
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

import java.io.*;
import java.util.Properties;

/**
 *  Some utilties for files and property files.
 */
public class FileUtil {

        public static Reader openFile(String filename, String charset) 
                throws IOException, FileNotFoundException {

                return new InputStreamReader(new FileInputStream(filename), charset);
        }

        public static Reader openFile(String filename) 
                throws IOException, FileNotFoundException {

                return openFile(filename, "UTF-8");
        }

        private static InputStream openResource(String name) throws IOException {
                InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(name);
                // try relative to this class, to support the old method of starting
                // resource names with a slash
                if (in == null)
                        in = FileUtil.class.getResourceAsStream(name);
                if (in == null)
                        throw new FileNotFoundException("Resource " + name + " not found.");
                return in;
        }

        public static Reader readResource(String name) throws IOException {
                return readResource(name, "UTF-8");
        }

        public static Reader readResource(String name, String charset) throws IOException {
                InputStream in = openResource(name);
                return new InputStreamReader(in, charset);
        }

        public static Properties readPropertiesResource(String name) throws IOException {
                Properties prop = new Properties();
                InputStream propIn = openResource(name);
                prop.load(propIn);
                propIn.close();
                return prop;
        }

        public static String getProp(Properties prop, String name) {
                String value = prop.getProperty(name);
                if (value == null)
                        throw new GFException("Property " + name + " is not set");
                return value;
        }

        public static String[] getListProp(Properties prop, String name) {
                return StringUtil.splitAtCommas(prop.getProperty(name));
        }

}
