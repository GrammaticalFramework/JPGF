/*
 * Test.java
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

package se.chalmers.cs.gf.transfer;

import se.chalmers.cs.gf.abssyn.*;
import se.chalmers.cs.gf.translate.*;
import se.chalmers.cs.gf.Core.Absyn.*;

import java.util.*;
import java.io.*;

public class Test {

        public static void main(String[] args) throws IOException {
                if (args.length != 5) {
                        System.err.println("Usage: java " + Test.class.getName()
                                           + " <properties resource> <from lang> <to lang> <trc file> <transfer exp>");
                        System.exit(2);
                }

                Translator t = TranslatorFactory.createTranslator(args[0]);
                String fromLang = args[1];
                String toLang = args[2];
                String trc = args[3];
                String trE = args[4];
		Exp trExp = new EVar(trE);

		Env env = new ModuleEnv(ModuleEnv.loadModule(trc), new Primitives());

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = in.readLine()) != null) {
                        System.out.println("Input: " + line);
                        Collection<Tree> ts = t.parse(fromLang, line);
                        for (Tree tree : ts) {
                                System.out.println("Tree:  " + tree);
				Tree tree2 = ApplyTransfer.applyTransfer(trExp, tree, env);
                                System.out.println("Transferred tree:  " + tree2);
                                String lin = t.linearize(toLang, tree2);
                                System.out.println("Output : " + lin );
                        }
                        System.out.println("---");
                }
        }

}
