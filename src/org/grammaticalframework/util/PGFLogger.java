/*
 * GFLogger.java
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
package org.grammaticalframework.util;

import java.util.logging.*;

/**
 *  General logging class. 
 */
public class PGFLogger {

	private Logger logger;

	public PGFLogger(String packageName) {
		this.logger = Logger.getLogger(packageName);
	}

	public void severe(String s) {
		log(Level.SEVERE, s);
	}

	public void warning(String s) {
		log(Level.WARNING, s);
	}

	public void fine(String s) {
		log(Level.FINE, s);
	}

	public void finer(String s) {
		log(Level.FINER, s);
	}

	public void finest(String s) {
		log(Level.FINEST, s);
	}

	public boolean finestIsLoggable() {
		return isLoggable(Level.FINEST);
	}

	public void error(Throwable t) {
		LogRecord r = new LogRecord(Level.SEVERE, "Throw");
		r.setThrown(t);
		logger.log(r);
	}

	public void log(Level l, String s) {
		logger.log(l, s);
	}

	private boolean isLoggable(Level l) {
		return logger.isLoggable(l);
	}

}
