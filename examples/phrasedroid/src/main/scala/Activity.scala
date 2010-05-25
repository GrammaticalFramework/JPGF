package se.chalmers

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view.View
//import android.view.Menu._


class MainActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
        val phrase = phraseField.getText.toString
        resultView.setText("Your phrase was : " + phrase)
      }
    })
  }
}
