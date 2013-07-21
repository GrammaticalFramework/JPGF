/* MagnetController.java
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
package org.grammaticalframework.examples.PhraseDroid;

import se.fnord.android.layout.PredicateLayout;
import android.widget.TextView;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.*;
import android.content.Context;
import java.util.Vector;

class MagnetController implements View.OnTouchListener {
    private final PredicateLayout mLayout;
    private final Context mContext;
    private final Vector<String> mMagnets = new Vector<String>();
    private final OnClickListener mOnClickListener;

    public MagnetController(PredicateLayout layout,
			    OnClickListener listener) {
	this.mLayout = layout;
	this.mContext = layout.getContext();
	this.mOnClickListener = listener;
    }

    public void addMagnet(String word) {
	this.mMagnets.add(word);
	TextView t = new TextView(this.mContext);
	t.setText(word);
	applyMagnetStyles(t);
	t.setOnTouchListener(this);
	this.mLayout.addView(t, new PredicateLayout.LayoutParams(3, 3));
    }

    public void removeAllMagnets() {
        mLayout.removeAllViews();
	mMagnets.clear();
    }
    
    public void replaceMagnets(String[] words) {
	this.removeAllMagnets();
	for (String w : words)
	    this.addMagnet(w);
    }
    
    public String[] getMagnets() {
	return this.mMagnets.toArray(new String[this.mMagnets.size()]);
    }

    public int size() {
        return this.mMagnets.size();
    }

    private void applyMagnetStyles(TextView view) {
        view.setTextColor(Color.BLACK);
        view.setBackgroundColor(Color.WHITE);
        view.setSingleLine(true);
        view.setPadding(10, 10, 10, 10);
        view.setClickable(true);
    }

    //OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
	if (this.mOnClickListener != null && 
	    event.getAction() == MotionEvent.ACTION_UP) {
	    String word = ((TextView) view).getText().toString();
	    this.mOnClickListener.onClick(this, word);
	    return true;
	}    
	return false;
    }


    public interface OnClickListener {
	public abstract void onClick(MagnetController magnets, String item);
    }

}
