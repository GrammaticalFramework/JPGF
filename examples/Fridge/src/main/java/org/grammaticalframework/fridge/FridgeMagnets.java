/* FridgeMagnets.java
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
package org.grammaticalframework.fridge;

import java.util.Arrays;

import android.os.*;
import android.app.*;
import android.content.*;
import android.text.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import android.graphics.*;
import se.fnord.android.layout.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.Vector;

import org.grammaticalframework.PGF;
import org.grammaticalframework.PGFBuilder;
import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.Parser;

public class FridgeMagnets extends Activity {
    /** Called when the activity is first created. */
    String[] words = new String[0];
    Vector<String> sentence = new Vector<String>();

    private Controller controller = new Controller();
    private EditText searchBox = null;
    private PGF mPGF;
    private Parser mParser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	// Setting up the pgf
	try {
	    InputStream is =
		this.getResources().openRawResource(R.raw.foods);
	    mPGF = PGFBuilder.fromInputStream(is);
	    mParser = new Parser(mPGF, "FoodsEng");
	} catch (IOException e) {}
	catch (PGF.UnknownLanguageException e) {}
        refreshBagOfWords(null);
        View main = findViewById(R.id.main_view);
        main.setFocusableInTouchMode(true);
        main.setOnKeyListener(controller);
	Button clearButton = (Button)findViewById(R.id.clear_button);
	clearButton.setOnClickListener( new View.OnClickListener() {
		public void onClick(View v) {
		    clear();
		}
	    });

    }

    private void applyMagnetStyles(TextView view) {
        view.setTextColor(Color.BLACK);
        view.setBackgroundColor(Color.WHITE);
        view.setSingleLine(true);
        view.setPadding(10, 10, 10, 10);
        view.setClickable(true);
    }
    
    private void clear() {
	PredicateLayout l = (PredicateLayout) findViewById(R.id.magnets_sentence);
        l.removeAllViews();
	sentence = new Vector<String>();
	refreshBagOfWords(null);
    }
	
    private void refreshBagOfWords(String prefix) {
        PredicateLayout l = (PredicateLayout)findViewById(R.id.magnets_bag);
        l.removeAllViews();
	ParseState ps = mParser.parse(this.sentence.toArray(new String[this.sentence.size()]));
	this.words = ps.predict();
        Arrays.sort(words);
        for (int i = 0; i < words.length; i++) {
	    if (prefix != null && !words[i].startsWith(prefix))
		continue;
	    
	    TextView t = new TextView(this);
            t.setText(words[i]);
            t.setOnTouchListener(controller);
	    applyMagnetStyles(t);
            l.addView(t, new PredicateLayout.LayoutParams(1, 1));
        }		
    }
    
    private void addWord(String word) {
	PredicateLayout l = (PredicateLayout) findViewById(R.id.magnets_sentence);
	
	TextView t = new TextView(this);
	t.setText(word);
	applyMagnetStyles(t);
	l.addView(t, new PredicateLayout.LayoutParams(3, 3));
	this.sentence.add(word);
	this.refreshBagOfWords(null);
    }
    
    private void showSearchBox() {
    	if (searchBox != null)
	    return;
    	
	PredicateLayout l = (PredicateLayout) findViewById(R.id.magnets_sentence);
	
	EditText edit = new EditText(this);
	edit.setInputType(InputType.TYPE_CLASS_TEXT |
			  InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
	edit.addTextChangedListener(controller);
	edit.setOnKeyListener(controller);
	applyMagnetStyles(edit);
	
	l.addView(edit, new PredicateLayout.LayoutParams(
							 ViewGroup.LayoutParams.WRAP_CONTENT,
							 ViewGroup.LayoutParams.WRAP_CONTENT,
							 3, 3));
	edit.requestFocus();
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.showSoftInput(edit, 0);
	
	searchBox = edit;
    }
    
    private void hideSearchBox() {
    	if (searchBox == null)
	    return;
	
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
	
	PredicateLayout l = (PredicateLayout) findViewById(R.id.magnets_sentence);		
	l.removeView(searchBox);
	
	refreshBagOfWords(null);
	
	searchBox = null;
    }
    
    private class Controller implements View.OnKeyListener, View.OnTouchListener, TextWatcher {
	
    	@Override
	public boolean onKey(View view, int keyCode, KeyEvent event) {
	    if (event.getAction() == KeyEvent.ACTION_DOWN) {    			
		if (searchBox == null && keyCode == KeyEvent.KEYCODE_SEARCH) {
		    showSearchBox();
		    return true;
		} else if (searchBox != null && keyCode == KeyEvent.KEYCODE_SEARCH) {
		    hideSearchBox();
		    return true;
		}
	    }
	    return false;
        }
    	
    	@Override
	public boolean onTouch(View view, MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_UP) {
		hideSearchBox();
		addWord(((TextView) view).getText().toString());
		return true;
	    }
            
	    return false;
    	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}
	
	@Override
        public void onTextChanged(CharSequence text, int arg1, int arg2, int arg3) {
	    refreshBagOfWords(text.toString());
	}
    }
}
