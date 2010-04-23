package example.hello;

import android.R.style;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class HelloAndroid extends Activity {
  EditText mTextField = null;
  TableLayout mLL = null;
  Button mButton_dial = null;
  TextView mTextView = null;
  ScrollView mScrollView = null;
  AutoCompleteTextView mDDList = null;
  String[] options = new String[] {"Foods", "Grammar"};
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    mLL = new TableLayout(this);
    mLL.setBackgroundColor(Color.WHITE);
    mTextField = new EditText(this);
    mTextField.setHighlightColor(Color.YELLOW);
    mLL.addView(mTextField);
    mButton_dial = new Button(this);
    mButton_dial.setText("Parse");
    mLL.addView(mButton_dial);
    mScrollView = new ScrollView(this);
    mTextView = new TextView(this);
    mScrollView.addView(mTextView);
    mLL.addView(mScrollView);
    mDDList = new AutoCompleteTextView(this);
    mButton_dial.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        parseEntry();
      }
    });

    setContentView(mLL);
  }

  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_ENTER) {
      parseEntry();
      return true;
    }
    return false;
  }

  public void parseEntry(){
	  if(!mTextField.getText().toString().equals("")){
      try {	  
      mTextView.setText("Parse tree of " + mTextField.getText().toString() + " here ! ");
      for(int i =0 ; i <30; i++)
    	  mTextView.append("\n"+ i+":abc");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else mTextView.setText("Please enter a phrase ! ");
  }
}

