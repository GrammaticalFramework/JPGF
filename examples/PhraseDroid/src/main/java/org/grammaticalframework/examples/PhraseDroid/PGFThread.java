package org.grammaticalframework.examples.PhraseDroid;

import org.grammaticalframework.reader.PGF;
import org.grammaticalframework.reader.Concrete;
import org.grammaticalframework.parser.Parser;
import org.grammaticalframework.linearizer.Linearizer;
import java.io.InputStream;
import java.io.IOException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
/**
 * This is the thread that manage the PGF.
 **/
class PGFThread extends Thread {
    static private int TRANSLATE = 3;
    private PhrasedroidActivity activity;
    public Handler mHandler;        // an Handler to receive messages
    private Language sLang;         // Source language
    private Language tLang;         // Target language
    private Runnable onPGFReady;
    private Runnable onPGFError;
    
    /** 
     * @param activity the activity that started the thread and on which the results will be displayed.
     * @param sLang the source language
     * @param tLang the target language
     **/
    public PGFThread(PhrasedroidActivity activity, Language sLang, Language tLang) {
	this.activity = activity;
	this.sLang = sLang;
	this.tLang = tLang;
    }
    
    public void run() {
	try {
	    int phrasebook_resource = Language.getPGFResource(this.sLang, this.tLang);
	    if (phrasebook_resource == -1)
		throw new RuntimeException("PGF not foud for languages " + sLang + " and " + tLang);
	    InputStream is =
		this.activity.getResources().openRawResource(phrasebook_resource);
	    final long begin_time = System.currentTimeMillis();
	    PGF pgf = PGF.readFromInputStream(is);
	    final long end_time = System.currentTimeMillis();
	    String sourceLang = (String)activity.getResources().getText(R.string.source_concrete);
	    final Translator trans =
		new Translator(pgf, sLang.concrete, tLang.concrete);
	    activity.runOnUiThread(new Runnable() { public void run() {
			Toast.makeText(activity.getApplicationContext(),
				       "PGF read in " + (end_time - begin_time) + " ms",
				       Toast.LENGTH_SHORT).show();
			
	    }});
	    if (this.onPGFReady != null)
		this.onPGFReady.run();
	    Looper.prepare();
	    mHandler = new Handler() {
		    public void handleMessage(Message msg) {
			if (msg.what == TRANSLATE) {
			    String txt = (String)msg.obj;
			    String translation = trans.translate(txt);
			    activity.setText(translation, true);
			}
		    }
		};
	    Looper.loop();
	} catch (IOException e) {
	    if (this.onPGFReady != null) 
		this.onPGFReady.run();
	}
    }

    public void onPGFReady(Runnable r) {
	this.onPGFReady = r;
    }
    
    public void translate(String txt) {
        Message msg = Message.obtain();
        msg.what = TRANSLATE;
        msg.obj = txt;
        this.mHandler.sendMessage(msg);
    }
    
}