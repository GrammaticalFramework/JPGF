package org.grammaticalframework.examples.PhraseDroid;

import org.grammaticalframework.reader.PGF;
import org.grammaticalframework.reader.Concrete;
import org.grammaticalframework.parser.Parser;
import org.grammaticalframework.linearizer.Linearizer;
import java.io.InputStream;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class PGFThread extends Thread {
    static private int TRANSLATE = 3;
   private Translator mTranslator; // The pgf object to use
   private MainActivity activity;  // the user that will receive the translations
   public Handler mHandler;        // an Handler to receive messages
    
   PGFThread(MainActivity activity, Translator mTranslator) {
      this.mTranslator = mTranslator;
      this.activity = activity;
    }
    
    public void run() {
        final Translator trans = this.mTranslator;
        Looper.prepare();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == TRANSLATE) {
                    String txt = (String)msg.obj;
		    final String translation = mTranslator.translate(txt);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.setTranslation(translation);
                        }
                    });
                }
            }
        };
        Looper.loop();
    }
    
    public void translate(String txt) {
        Message msg = Message.obtain();
        msg.what = TRANSLATE;
        msg.obj = txt;
        this.mHandler.sendMessage(msg);
    }
    
}