/*
 * GFCMModules.java
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
package se.chalmers.cs.gf.gfcutil;

import se.chalmers.cs.gf.GFC.*;
import se.chalmers.cs.gf.GFC.Absyn.*;
import se.chalmers.cs.gf.GFException;
import se.chalmers.cs.gf.util.*;

import java.util.*;
import java.io.*;

/**
 *  Contains a collection of GFC modules which share an abstract syntax.
 */
public class GFCMModules {

        /**
         *  Maps module names to modules.
         */
        private Map<String,GFCModule> map;

        /**
         *  The name of the main abstract module.
         */
        private String mainAbstract;

        /**
         *  The names of the main concrete modules.
         */
        private List<String> mainConcrete;

        public GFCMModules(Canon gfc) {
                this.map = new TreeMap<String,GFCModule>();
                this.mainAbstract = null;
                this.mainConcrete = new LinkedList<String>();

                // FIXME: the Canon visitor is broken because of a name clash,
                // that's why we do type case here.

                if (gfc instanceof Gr) {
                        Gr gr = (Gr)gfc;
                        for (Module m : gr.listmodule_)
                                add(new GFCModule((Mod)m, map));
                        // Set main abstract module to first abstract module
                        for (GFCModule gm : map.values()) {
                                if (gm.getType() == ModuleType.ABSTRACT) {
                                        mainAbstract = gm.getName();
                                        break;
                                }
                        }
                        // Set main concrete modules to all concrete modules 
                        // for the main abstract module
                        for (GFCModule m : map.values()) {
                                if (m.getType() == ModuleType.CONCRETE 
                                    && m.getAbstract().equals(mainAbstract))
                                        mainConcrete.add(m.getName());
                        }
                } else {
                        MGr mgr = (MGr)gfc;
                        for (Module m : mgr.listmodule_)
                                add(new GFCModule((Mod)m, map));
                        // Set main abstract module from header
                        mainAbstract = mgr.ident_;
                        // Set main concrete modules from header
                        for (String m : mgr.listident_)
                                mainConcrete.add(m);
                }
        }


        /**
         *  Gets a module by name.
         */
        public GFCModule getModule(String name) {
                return map.get(name);
        }

        /**
         *  Gets the main abstract module.
         */
        public GFCModule getMainAbstractModule() {
                return getModule(mainAbstract);
        }

        /**
         *  Gets the main concrete modules, i.e. those that are concrete syntaxes
         *  for the main abstract module.
         */
        public List<GFCModule> getMainConcreteModules() {
                List<GFCModule> l = new ArrayList<GFCModule>(mainConcrete.size());
                for (String n : mainConcrete)
                        l.add(getModule(n));
                return l;
        }

        /**
         *  Add a GFCModule to this collection of modules.
         */
        private void add(GFCModule module) {
                String name = module.getName();
                if (map.containsKey(name))
                        throw new GFException("Module " 
                                                   + name + " already exists.");
                                
                map.put(name, module);
        }

        /**
         *  Load a GFCM grammar from a file.
         *
         *  @param file Filename.
         */
        public static GFCMModules loadGFCM(String file) 
                throws FileNotFoundException, IOException {

                return loadGFCM(FileUtil.openFile(file));
        }

        /**
         *  Load a GFCM grammar from a Reader.
         */
        public static GFCMModules loadGFCM(Reader input) throws IOException {
//                System.err.println("Parsing GFCM file");
                Canon gfc = GFCParser.parseCanon(input);
//                System.err.println("Done parsing GFCM file");
                input.close();
                return new GFCMModules(gfc);
        }

        /**
         *  Load a gzipped GFCM grammar from a file.
         */
/*
        public static GFCMModules loadGFCM_GZ(String file) throws FileNotFoundException, IOException {
                return loadGFCM(new java.util.zip.GZIPInputStream(new FileInputStream(file)));
        }
*/
}
