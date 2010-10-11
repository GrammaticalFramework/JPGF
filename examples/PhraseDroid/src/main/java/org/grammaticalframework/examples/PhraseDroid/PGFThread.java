package org.grammaticalframework.examples.PhraseDroid;

import org.grammaticalframework.PGF;
import org.grammaticalframework.PGFBuilder;
import org.grammaticalframework.Parser;
import org.grammaticalframework.Linearizer;
import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.Trees.Absyn.Tree;

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

    // Logging
    private static final boolean DBG = true;
    private static final String TAG = "PhraseDroid";

    // Message actions
    static final private int PS_RESET = 1;
    static final private int PS_SCAN = 2;
    static final private int PS_PARSE = 3;

    private PhrasedroidActivity activity;
    public Handler mHandler;        // an Handler to receive messages
    private Language sLang;         // Source language
    private Language tLang;         // Target language
    private Runnable onPGFReady;
    private Runnable onPGFError;

    // State
    private ParseState mParseState;
    
    /** 
     * @param activity the activity that started the thread and on which the results will be displayed.
     * @param sLang the source language
     * @param tLang the target language
     **/
    public PGFThread(PhrasedroidActivity activity,
                     Language sLang,
                     Language tLang)
    {
        this.activity = activity;
        this.sLang = sLang;
        this.tLang = tLang;
    }
    
    public void run() {
        // try {
            int phrasebook_resource =
                Language.getPGFResource(this.sLang, this.tLang);
            if (phrasebook_resource == -1)
                throw new RuntimeException("PGF not found for languages " + sLang + " and " + tLang);
            // InputStream is =
            //     this.activity.getResources().openRawResource(phrasebook_resource);
            // final long begin_time = System.currentTimeMillis();
            // PGF pgf = PGFBuilder.fromInputStream(is);
            // final long end_time = System.currentTimeMillis();
            // String sourceLang = (String)activity.getResources().getText(R.string.source_concrete);
            // final Translator trans =
            //     new Translator(pgf, sLang.concrete, tLang.concrete);
            // activity.runOnUiThread(new Runnable() { public void run() {
            //             Toast.makeText(activity.getApplicationContext(),
            //                            "PGF read in " + (end_time - begin_time) + " ms",
            //                            Toast.LENGTH_SHORT).show();
            //             
            // }});
            // if (this.onPGFReady != null)
            //     this.onPGFReady.run();
            Looper.prepare();
            mHandler = new PGFHandler(phrasebook_resource, sLang, tLang);
            this.clear();
            Looper.loop();
        // } catch (IOException e) {
        //     if (this.onPGFReady != null) 
        //         this.onPGFReady.run();
        // }
    }
    
    public void onPGFReady(Runnable r) {
        this.onPGFReady = r;
    }
    
    public void clear() {
        Message msg = Message.obtain();
        msg.what = PS_RESET;
        this.mHandler.sendMessage(msg);
    }
    
    public void scan(String w) {
        Message msg = Message.obtain();
        msg.what = PS_SCAN;
        msg.obj = w;
        this.mHandler.sendMessage(msg);
    }
    
    public void parse(String[] ws) {
        if (DBG) Log.i(TAG, "PGFThread called for parsing");
        Message msg = Message.obtain();
        msg.what = PS_PARSE;
        msg.obj = ws;
        this.mHandler.sendMessage(msg);
    }
    
    
    /* ***********************************************************************
     * INTERFACE
     */
    
    // Parse a word in the current parsestate
    public void parse(String w) {}
    
    // reset the parse state
    public void resetPS() {}
    
    // The pgf handler is the main class of the thread. It will handle operation on and with the pgf file.
    private class PGFHandler extends Handler {
        private PGF mPGF;               // PGF object
        private Language sLang;         // Source language
        private Language tLang;         // Target language
        private ParseState mParseState;
        private Parser mParser;           // The Parser object to use TODO: we only use it to get the initial parse state. Since this one is expensive to calculate, it could be interesting to cache it and then, we wouldn't need to keep the parser object arround...
        private Linearizer mLinearizer;   // The Linearizer object to use

        /* Constructor */
        public PGFHandler(int pgf_res, Language sLang, Language tLang) {
            super();
            pgf_load(pgf_res, sLang, tLang);
        }

        /**/
        public void handleMessage(Message msg) {
            switch (msg.what) {
              case PS_RESET:
                this.ps_reset();
                break;
              case PS_SCAN:
                String word = (String)msg.obj;
                this.ps_scan(word);
                break;
              case PS_PARSE:
                String[] words = (String[])msg.obj;
                this.ps_parse(words);
                break;
            }
        }
        
        /* *******************************************************************
         * PGF Operations
         */
         
        private void ps_scan(String w) {
            if (DBG) Log.i(TAG, "Scanning token " + w);
            this.mParseState.scan(w);
            Tree[] trees = (Tree[])this.mParseState.getTrees();
            Translation translation = null;
            if (trees.length > 0)
                try {
                    String s = this.mLinearizer.linearizeString(trees[0]);
                    translation = new Translation(s,this.tLang);
                } catch (java.lang.Exception e) {
                    if (DBG) Log.w(TAG, "No translation (Error during linearization) ");
                }
            else {
                if (DBG) Log.w(TAG, "No translation (No tree found) ");
            }
            activity.new_parse_result(mParseState.predict(), translation);
        }

        private void ps_reset() {
            if (DBG) Log.i(TAG, "Reset ParseState");
            this.mParseState = mParser.parse();
            activity.new_parse_result(mParseState.predict(),null);
        }
        
        private void ps_parse(String[] ws) {
            if (DBG) Log.i(TAG, "New ParseState : parsing phrase");
            this.mParseState = mParser.parse(ws);
            activity.new_parse_result(mParseState.predict(),null);
        }
        
        private void pgf_load(int pgf_res, Language source, Language target) {
            if (DBG) Log.i(TAG, "Loading PGF");
            this.sLang = source;
            this.tLang = target;    
            InputStream is =
                activity.getResources().openRawResource(pgf_res);
            final long begin_time = System.currentTimeMillis();
            try {
                this.mPGF = PGFBuilder.fromInputStream(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            final long end_time = System.currentTimeMillis();
            if (DBG) Log.d(TAG, "Grammar loaded in " + (end_time - begin_time) + "ms");
            String sourceLang = (String)activity.getResources().getText(R.string.source_concrete);
            try {
                this.mParser = new Parser(mPGF, sLang.concrete);
                this.mLinearizer = new Linearizer(mPGF, tLang.concrete);
            } catch (PGF.UnknownLanguageException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException("Cannot create the linearizer : " + e);
            }
            activity.pgf_ready();
        }
         
        
        
    }
}
