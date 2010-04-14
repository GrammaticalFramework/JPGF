/*
 * ModuleEnv.java
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
package se.chalmers.cs.gf.transfer;

import se.chalmers.cs.gf.Core.Absyn.*;
import se.chalmers.cs.gf.Core.CoreParser;
import se.chalmers.cs.gf.util.FileUtil;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ModuleEnv extends Env {

    private Map<String,Exp> defs;

	public ModuleEnv(Module m, Env prev) {
	    defs = new HashMap<String,Exp>();
		addAll(prev);
		for (Decl d : m.listdecl_) {
			if (d instanceof DataDecl) {
				DataDecl dd = (DataDecl)d;
				add(dd.cident_, new Value.VCons(dd.cident_));
				for (ConsDecl cd : dd.listconsdecl_) {
					add(cd.cident_, 
					    new Value.VCons(cd.cident_));
				}				
			} else if (d instanceof ValueDecl) {
				ValueDecl vd = (ValueDecl)d;
				defs.put(vd.cident_, vd.exp_);
			}
		}
	}

	public Value lookup(String name) {
	    Exp e = defs.get(name);
	    if (e == null) {
		return super.lookup(name);
	    }
	    return InterpretCore.eval(e, this);
	}

	public static Module loadModule(Reader input)
		throws IOException {
		
                Module m = CoreParser.parseModule(input);
                input.close();
		return m;
	}

        public static Module loadModule(String file) 
                throws FileNotFoundException, IOException {

                return loadModule(FileUtil.openFile(file));
        }

	public String toString() {
	    return super.toString() + defs.toString();
	}

}
