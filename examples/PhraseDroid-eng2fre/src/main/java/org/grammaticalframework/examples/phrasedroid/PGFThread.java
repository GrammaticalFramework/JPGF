/* PGFThread.java
 * Copyright (C) 2010 Grégoire Détrez
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.grammaticalframework.examples.phrasedroid;

import org.grammaticalframework.PGF;
import org.grammaticalframework.PGFBuilder;
import org.grammaticalframework.Parser;
import org.grammaticalframework.parser.ParseState;

import java.io.InputStream;
import java.io.IOException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.util.Log;

/**
 * This is the thread that manage the PGF.
 **/
class PGFThread extends Thread {

    // Logging tag
    static final private String MYTAG = "PhraseDroid";

    // Message actions
    static final private int CLEAR = 1;
    static final private int SCAN = 2;
    static final private int TRANSLATE = 3;

    private PhrasedroidActivity activity;
    public Handler mHandler;        // an Handler to receive messages
    private String sLang;         // Source language
    private String tLang;         // Target language
    private Runnable onPGFReady;
    private Runnable onPGFError;

    // State
    private ParseState mParseState;
    
    /** 
     * @param activity the activity that started the thread and on which the 
     * results will be displayed.
     * @param sLang the source language
     * @param tLang the target language
     **/
    public PGFThread(PhrasedroidActivity activity,
		     String sLang,
		     String tLang)
    {
	this.activity = activity;
	this.sLang = sLang;
	this.tLang = tLang;
    }
    
    public void run() {
	try {
	    int phrasebook_resource = activity.PGF_RES_ID;
	    InputStream is =
		this.activity.getResources().openRawResource(phrasebook_resource);
	    final long begin_time = System.currentTimeMillis();
	    PGF pgf = PGFBuilder.fromInputStream(is);
	    final long end_time = System.currentTimeMillis();
	    //String sourceLang = (String)activity.getResources().getText(R.string.source_concrete);
	    activity.runOnUiThread(new Runnable() { public void run() {
			Toast.makeText(activity.getApplicationContext(),
				       "PGF read in " +
				       (end_time - begin_time) + " ms",
				       Toast.LENGTH_SHORT).show();
			
	    }});
	    if (this.onPGFReady != null)
		this.onPGFReady.run();
	    Looper.prepare();
	    mHandler = new PGFHandler(pgf, sLang, tLang);
	    this.clear();
	    Looper.loop();
	} catch (IOException e) {
	    if (this.onPGFReady != null) 
		this.onPGFReady.run();
	}
    }
    
    public void onPGFReady(Runnable r) {
	this.onPGFReady = r;
    }
    
    public void translate(String[] txt) {
        Message msg = Message.obtain();
        msg.what = TRANSLATE;
        msg.obj = txt;
        this.mHandler.sendMessage(msg);
    }
    
    public void clear() {
        Message msg = Message.obtain();
        msg.what = CLEAR;
        this.mHandler.sendMessage(msg);
    }
    
    public void scan(String[] txt) {
        Message msg = Message.obtain();
        msg.what = SCAN;
        msg.obj = txt;
        this.mHandler.sendMessage(msg);
    }
    

    private class PGFHandler extends Handler {
	private final PGF mPGF;               // PGF object
	private final String sLang;         // Source language
	private final String tLang;         // Target language
	private ParseState mParseState;
	private Parser mParser;

	public PGFHandler(PGF pgf, String sLang, String tLang) {
	    super();
	    this.mPGF = pgf;
	    this.sLang = sLang;
	    this.tLang = tLang;
	    try {
		this.mParser = new Parser(pgf, sLang);
	    } catch (PGF.UnknownLanguageException e) {
		Log.e(MYTAG, e.toString());
		// FIXME: empty
	    }
	}

	public void handleMessage(Message msg) {
	    switch (msg.what) {
	    case CLEAR:
		this.mParseState = mParser.parse();
		activity.setMagnets(mParseState.predict());
		break;
	    case SCAN:
		String[] words = (String[])msg.obj;
		this.mParseState = mParser.parse(words);
		activity.setMagnets(mParseState.predict());
		break;
	    case TRANSLATE:
		Translator trans =
		    new Translator(mPGF, sLang, tLang);
		String[] txt = (String[])msg.obj;
		String translation = trans.translate(txt);
		activity.setText(translation, true);
		break;
	    }
	}
    }
}
