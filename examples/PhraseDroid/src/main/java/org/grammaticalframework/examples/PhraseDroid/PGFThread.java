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

class PGFThread extends Thread {
    static private int TRANSLATE = 3;
    private MainActivity activity;
    public Handler mHandler;        // an Handler to receive messages
    
   PGFThread(MainActivity activity) {
      this.activity = activity;
    }
    
    public void run() {
	try {
	    InputStream is =
		this.activity.getResources().openRawResource(R.raw.phrasebook);
	    long begin_time = System.currentTimeMillis();
	    PGF pgf = PGF.readFromInputStream(is);
	    long end_time = System.currentTimeMillis();
	    String sourceLang = (String)activity.getResources().getText(R.string.source_concrete);
	    final Translator trans =
		new Translator(pgf, sourceLang, "PhrasebookFre");
	    activity.setText("PGF read in " + (end_time - begin_time) + " ms",
			     false);
	    activity.onPgfReady();
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
	    activity.onPgfError(e.toString());
	}
    }
    
    public void translate(String txt) {
        Message msg = Message.obtain();
        msg.what = TRANSLATE;
        msg.obj = txt;
        this.mHandler.sendMessage(msg);
    }
    
}