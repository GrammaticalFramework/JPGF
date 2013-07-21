/* PhrasedroidActivity.java
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

import se.fnord.android.layout.PredicateLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.*;
import java.util.Locale;
import java.util.Arrays;

public class PhrasedroidActivity extends Activity
    implements TextToSpeech.OnInitListener,
	       View.OnClickListener
{

    // LANGUAGES
    public String SLANG_CONCRETE = "PhrasebookEng";
    public Locale SLANG_LOCALE   = Locale.ENGLISH;
    public String TLANG_CONCRETE = "PhrasebookFre";
    public Locale TLANG_LOCALE   = Locale.FRENCH;
    public int    PGF_RES_ID     = R.raw.phrasebook;
    
    // TTS Intent code
    static final int MY_TTS_CHECK_CODE = 2347453;

    private boolean tts_ready = false;
    private TextToSpeech mTts;
    protected PGFThread mPGFThread;

    // Magnets
    private MagnetController phraseMagnets;
    private MagnetController wordsMagnets;

    String currentText = "";

    // UI elements
    TextView resultView;

    // ************************** Activity Lifecycle **************************
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// Setup UI
	setContentView(R.layout.main);
	// Get pointers to the ui elements
	resultView = (TextView) findViewById(R.id.result_view);
	PredicateLayout p = (PredicateLayout) findViewById(R.id.phrase_magnets);
 	this.phraseMagnets = 
 	    new MagnetController(p, new MagnetController.OnClickListener() {
 		    public void onClick(MagnetController magnets, String item) {
 		    }});
	p = (PredicateLayout) findViewById(R.id.words_magnets);
 	this.wordsMagnets = 
 	    new MagnetController(p, new MagnetController.OnClickListener() {
 		    public void onClick(MagnetController magnets, String item) {
			addWord(item);
 		    }});
	// setup buttons
	((Button) findViewById(R.id.translate_button)).setOnClickListener(this);
	((Button) findViewById(R.id.speak_button)).setOnClickListener(this);
	((Button) findViewById(R.id.clear_button)).setOnClickListener(this);
	// Setup languages
	this.setupLanguages();
	// Setup TTS
	startTTSInit();
    }

    public void onDestroy() {
	super.onDestroy();
	if (mTts != null)
	    mTts.shutdown();
    }

    public void addWord(String w) {
	this.wordsMagnets.removeAllMagnets();
	this.phraseMagnets.addMagnet(w);
	String[] words = this.phraseMagnets.getMagnets();
	this.mPGFThread.scan(words);
    }

    public void clearPhrase() {
	this.wordsMagnets.removeAllMagnets();
	this.phraseMagnets.removeAllMagnets();
	this.mPGFThread.clear();
    }
    // *************************** Configuration ******************************
    public void setupLanguages() {
	// Setup the thread for the pgf
	// FIXME : localize the dialog...
	final ProgressDialog progress =
	    ProgressDialog.show(this, "",
				"Loading Grammar. Please wait...", true);
	mPGFThread = new PGFThread(this, SLANG_CONCRETE, TLANG_CONCRETE);
	mPGFThread.onPGFReady(new Runnable() {
		public void run() {
		    runOnUiThread(new Runnable() { public void run() {
			progress.dismiss();
		    }});
		}});
	mPGFThread.start();
	if (this.tts_ready)
	    mTts.setLanguage(TLANG_LOCALE);
    }

    // ***************************** UI Actions *******************************
    // needed by View.onClickListener
    public void onClick(View v) {
	if (v == findViewById(R.id.translate_button)) {
	    setText("Translating...", false);
	    String[] words = this.phraseMagnets.getMagnets();
	    mPGFThread.translate(words);
	} else if (v == findViewById(R.id.speak_button)) {
	    say(currentText);
	} else if (v == findViewById(R.id.clear_button))
	      clearPhrase();
    }

    public void setText(String t, boolean sayable) {
	final String text = t;
	if (sayable)
	    this.currentText = text;
	else
	    this.currentText = "";
	runOnUiThread(new Runnable() {
		public void run() { resultView.setText(text); }
	    });
    }

    public void setMagnets(final String[] magnets) {
	Arrays.sort(magnets);
	runOnUiThread(new Runnable() {public void run() {
	    wordsMagnets.replaceMagnets(magnets);
	}});
    }

    // ********************************  TTS  *********************************

    public void say(String txt) {
	if (this.tts_ready)
	    this.mTts.speak(txt, TextToSpeech.QUEUE_ADD, null);
    }
    
    // Text-To-Speech initialization is done in three (asychronous) steps 
    // coresponding to the three methods below :
    
    // First : we check if the TTS data is present on the system
    public void startTTSInit() {
	Intent checkIntent = new Intent();
	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	startActivityForResult(checkIntent, MY_TTS_CHECK_CODE);
    }

    // Second: if the data is present, we initialise the TTS engine
    // (otherwise we ask to install it)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == MY_TTS_CHECK_CODE) {
	    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
		// success, create the TTS instance
		mTts = new TextToSpeech(this, this);
	    } else {
		// missing data, install it
		Intent installIntent = new Intent();
		installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		startActivity(installIntent);
	    }
	}
    }
    
    // Finally: once the TTS engine is initialized, we set-up the language.
    public void onInit(int status) {
	if (status == TextToSpeech.SUCCESS) {
	    this.tts_ready = true;
	    mTts.setLanguage(TLANG_LOCALE);
	}
    }
}
