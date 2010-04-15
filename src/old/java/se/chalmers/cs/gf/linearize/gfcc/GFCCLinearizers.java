/*
 * GFCCLinearizers.java
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
package se.chalmers.cs.gf.linearize.gfcc;

import se.chalmers.cs.gf.GFCC.Absyn.*;
import se.chalmers.cs.gf.GFCC.GFCCParser;
import se.chalmers.cs.gf.linearize.Linearizers;
import se.chalmers.cs.gf.util.*;

import java.io.IOException;
import java.io.Reader;

/**
 *  Create linearizers for each concrete module in a parsed GFCC file.
 */
public class GFCCLinearizers extends Linearizers {

	public GFCCLinearizers(Grammar g) {
                for (Concrete c : ((Grm)g).listconcrete_) {
			addLinearizer(new GFCCLinearizer(c));
                }
	}


        public static GFCCLinearizers readGFCC(Reader in) throws IOException {
                Grammar gs = GFCCParser.parse(in);
                in.close();
                return new GFCCLinearizers(gs);
        }

        public static GFCCLinearizers readGFCC(String file) throws IOException {
                return readGFCC(FileUtil.openFile(file));
        }


}
