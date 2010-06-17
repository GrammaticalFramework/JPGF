package org.grammaticalframework.examples.PhraseDroid;

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

public abstract class PhrasedroidActivity extends Activity
    implements TextToSpeech.OnInitListener,
	       View.OnClickListener
{
    // CONSTANTS
    public static final String PREFS_NAME = "PhrasedroidPrefs";
    public static final String TLANG_PREF_KEY = "targetLanguageCode";
    static final int MY_TTS_CHECK_CODE = 2347453;
    static final int MENU_CHANGE_LANGUAGE = 3634543;

    private boolean tts_ready = false;
    private TextToSpeech mTts;
    private Language sLang = Language.ENGLISH;
    private Language tLang = Language.FRENCH;
    protected PGFThread mPGFThread;

    String currentText = "";

    // UI elements
    TextView resultView;

    // ************************************* Activity Lifecycle **************************************
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	// Setup languages
	// FIXME : do Source language
	Locale l = Locale.getDefault();
	Language source = Language.fromCode(l.getLanguage());
	if (source == null)
	    source = Language.ENGLISH;
	// Target language
	String tLangCode = settings.getString(TLANG_PREF_KEY, null);
	Language target = Language.fromCode(tLangCode);
	if (target == null || !Arrays.asList(source.getAvailableTargetLanguages()).contains(target))
	    target = source.getDefaultTargetLanguage();
	this.setupLanguages(source, target);

	// Setup TTS
	startTTSInit();
	// Setup UI
	setContentView(R.layout.main);
	// Get pointers to the ui elements
	resultView = (TextView) findViewById(R.id.result_view);
	// setup translate button
	((Button) findViewById(R.id.translate_button)).setOnClickListener(this);
	// setup speak action
	((Button) findViewById(R.id.speak_button)).setOnClickListener(this);
    }

    // *************************************** Configuration *****************************************
    public void setupLanguages(Language sLang, Language tLang) {
	this.sLang = sLang;
	this.tLang = tLang;
	// Setup the thread for the pgf
	// FIXME : localize the dialog...
	final ProgressDialog progress =
	    ProgressDialog.show(this, "", "Loading Grammar. Please wait...", true);
	mPGFThread = new PGFThread(this, sLang, tLang);
	mPGFThread.onPGFReady(new Runnable() {
		public void run() {
		    runOnUiThread(new Runnable() { public void run() {
			progress.dismiss();

		    }});
		}});
	mPGFThread.start();
	if (this.tts_ready)
	    mTts.setLanguage(this.tLang.locale);
    }

    public void changeTargetLanguage(Language l) {
	this.setupLanguages(this.sLang, l);
    }
    // ***************************************** UI Actions *******************************************
    // needed by View.onClickListener
    public void onClick(View v) {
	if (v == findViewById(R.id.translate_button)) {
	    setText("Translating...", false);
	    String phrase = ((EditText)findViewById(R.id.phrase)).getText().toString();
	    mPGFThread.translate(phrase);
	} else if (v == findViewById(R.id.speak_button))
	    say(currentText);
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


  // *****************************************  ACTIVITY MENU ******************************************

  /* Creates the menu items */
  public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, MENU_CHANGE_LANGUAGE, 0, "Change Language");
      return true;
  }
  
  /* Handles menu item selections */
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case MENU_CHANGE_LANGUAGE:
	  final Language[] tls = this.sLang.getAvailableTargetLanguages();
	  final String[] items = new String[tls.length];
	  int i = 0;
	  for (Language l : tls) {
	      items[i] = l.getName();
	      i++ ;
	  }
	  AlertDialog.Builder builder = new AlertDialog.Builder(this);
	  // FIXME: localize...
	  builder.setTitle("Pick a language");
	  builder.setItems(items, new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int item) {
		      changeTargetLanguage(tls[item]);
		      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		      settings.edit().putString(TLANG_PREF_KEY, tls[item].locale.getLanguage()).commit();
		  }
	      });
	  AlertDialog alert = builder.create();
	  alert.show();
	  return true;
      }
      return false;
  }

    // ********************************************  TTS  ********************************************* 

    public void say(String txt) {
	if (this.tts_ready)
	    this.mTts.speak(txt, TextToSpeech.QUEUE_ADD, null);
    }
    
    // Text-To-Speech initialization is done in three (asychronous) steps coresponding
    // to the three methods below :
    
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
	    mTts.setLanguage(this.tLang.locale);
	}
    }
}
