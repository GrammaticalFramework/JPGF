package org.grammaticalframework.examples.PhraseDroid;

import se.fnord.android.layout.PredicateLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.*;
import android.text.Html;
import java.util.Locale;
import java.util.Arrays;
import android.util.Log;


public abstract class PhrasedroidActivity extends Activity
    implements TextToSpeech.OnInitListener,
                View.OnClickListener
{
    
    // Logging
    private static final boolean DBG = true;
    private static final String TAG = "PhraseDroid";

    // Preference Keys
    public static final String PREFS_NAME = "PhrasedroidPrefs";
    public static final String TLANG_PREF_KEY = "targetLanguageCode";
    public static final String SLANG_PREF_KEY = "sourceLanguageCode";

    // TTS Intent code
    static final int MY_TTS_CHECK_CODE = 2347453;

    // Activity menu codes
    static final int MENU_CHANGE_SLANGUAGE = 1;
    static final int MENU_CHANGE_TLANGUAGE = 2;
    static final int MENU_SWITCH_LANGUAGES = 3;

    // Dialogs ids
    static final int DIALOG_LANGS_ID = 1;

    private boolean tts_ready = false;
    private TextToSpeech mTts;
    private Language sLang = Language.ENGLISH;
    private Language tLang = Language.FRENCH;
    protected PGFThread mPGFThread;

    // Magnets
    private MagnetController phraseMagnets;
    private MagnetController wordsMagnets;
    
    // Dialogs
    // TODO localization
    private ProgressDialog progress;

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
        ((Button) findViewById(R.id.speak_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.delete_button)).setOnClickListener(this);
        // Setup languages
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // FIXME : do Source language
        Locale l = Locale.getDefault();
        Language source = Language.fromCode(l.getLanguage());
        if (source == null)
            source = Language.ENGLISH;
        // Target language
        String tLangCode = settings.getString(TLANG_PREF_KEY, null);
        Language target = Language.fromCode(tLangCode);
        if (target == null || 
            !Arrays.asList(source.getAvailableTargetLanguages()).contains(target))
            target = source.getDefaultTargetLanguage();
        this.setupLanguages(source, target);

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
        ((Button) findViewById(R.id.delete_button)).setEnabled(true);
        this.mPGFThread.scan(w);
    }

    public void rmWord() {
	// remove all clickable magnets
        this.wordsMagnets.removeAllMagnets();
	// get the current words of the phrase
        String[] oldPhrase = this.phraseMagnets.getMagnets();
	// create a new phrase with all of them but the last one
	String[] newPhrase = new String[oldPhrase.length - 1];
	for (int i = 0 ; i < oldPhrase.length - 1;i++) {
            newPhrase[i] = oldPhrase[i];
	}
        this.phraseMagnets.replaceMagnets(newPhrase);
        ((Button) findViewById(R.id.delete_button)).setEnabled(false);
	// parse this new phrase in one batch
        this.mPGFThread.parse(newPhrase);
    }

    public void clearPhrase() {
        this.wordsMagnets.removeAllMagnets();
        this.phraseMagnets.removeAllMagnets();
        ((Button) findViewById(R.id.delete_button)).setEnabled(false);
        this.mPGFThread.clear();
    }
    
    public void new_parse_result(final String[] magnets, Translation t) {
        this.setMagnets(magnets);
        if (t != null)
            this.setText(t.text, true);
        else
            this.setText("", false);
        if (this.phraseMagnets.size() > 0)
            runOnUiThread(new Runnable() {public void run() {
                ((Button) findViewById(R.id.delete_button)).setEnabled(true);
            }});
    }
    
    public void pgf_ready() {
        if (this.progress != null)
            runOnUiThread(new Runnable() { public void run() {
                progress.dismiss();
            }});
    }
    // *************************** Configuration ******************************
    public void setupLanguages(Language sLang, Language tLang) {
        this.sLang = sLang;
        this.tLang = tLang;
        // Setup the thread for the pgf
        // FIXME : localize the dialog...
        this.progress = ProgressDialog.show(this, "", "Loading Grammar. Please wait...", true);
        mPGFThread = new PGFThread(this, sLang, tLang);
        mPGFThread.start();
        if (this.tts_ready)
            mTts.setLanguage(this.tLang.locale);
        this.setText("", false);
        this.wordsMagnets.removeAllMagnets();
        this.phraseMagnets.removeAllMagnets();
        // We write the names of the language in the titlebar
        this.setTitle(Html.fromHtml(getString(R.string.app_name) + " (" + sLang.getName()+" &#8594; "+tLang.getName() + ")"));
    }

    public void changeTargetLanguage(Language l) {
        this.setupLanguages(this.sLang, l);
    }
    // ***************************** UI Actions *******************************
    // needed by View.onClickListener
    public void onClick(View v) {
        if (v == findViewById(R.id.speak_button)) {
            say(currentText);
        } else if (v == findViewById(R.id.delete_button))
              rmWord();
    }

    public void setText(String t, boolean sayable) {
        final String text = t;
        final boolean enableTTS = sayable;
        if (sayable)
            this.currentText = text;
        else
            this.currentText = "";
        runOnUiThread(new Runnable() {
                public void run() {
                    resultView.setText(text);
                    ((Button) findViewById(R.id.speak_button)).setEnabled(enableTTS);
                }
            });
    }

    public void setMagnets(final String[] magnets) {
        Arrays.sort(magnets);
        runOnUiThread(new Runnable() {public void run() {
            wordsMagnets.replaceMagnets(magnets);
        }});
    }

    // *****************************  ACTIVITY MENU *****************************

    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }

    /* Handles menu item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	case R.id.menu_clear:
            clearPhrase();
            return true;
	case R.id.menu_switch_languages:
	    this.setupLanguages(this.tLang, this.sLang);
            return true;
	case R.id.menu_change_languages:
            showDialog(DIALOG_LANGS_ID);
            return true;
	}
	return false;
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
          case DIALOG_LANGS_ID:
            Context mContext = this; //getApplicationContext();
	    AlertDialog.Builder builder;
	    AlertDialog alertDialog;

	    LayoutInflater inflater =
		(LayoutInflater)mContext
		.getSystemService(LAYOUT_INFLATER_SERVICE);
	    View layout =
		inflater.inflate(R.layout.languages,
				 (ViewGroup) findViewById(R.id.setlang_root));

	    // We get pointers to the two spinners
            final Spinner ts = (Spinner)layout.findViewById(R.id.tlang_spinner);
            final Spinner ss = (Spinner)layout.findViewById(R.id.slang_spinner);

	    // We build the dialog
	    builder = new AlertDialog.Builder(mContext);
	    builder.setView(layout)
		.setCancelable(true)
		.setPositiveButton("Save",
				   new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
					   setupLanguages((Language)ss.getSelectedItem(),
							  (Language)ts.getSelectedItem());
				       }
				   })
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			    dialog.cancel();
			}
		    });

	    alertDialog = builder.create();
            dialog = alertDialog;
            //dialog.setTitle("Languages");
            // Populatting
            final Language languages[] = Language.values();
	    ArrayAdapter<Language> adapter = 
                new ArrayAdapter<Language>( this,
					   android.R.layout.simple_spinner_item,
					   languages );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ts.setAdapter(adapter);
            ss.setAdapter(adapter);
            break;
          default:
            dialog = null;
        }
        return dialog;
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
            mTts.setLanguage(this.tLang.locale);
        }
    }
}
