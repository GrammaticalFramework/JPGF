package org.grammaticalframework.examples.PhraseDroid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view.View
import _root_.android.content.res.Resources
import _root_.android.app.ProgressDialog
// Debuging

import _root_.org.grammaticalframework.reader.{NewReader, PGF, Concrete}
import _root_.org.grammaticalframework.parser.{Parser}
import _root_.org.grammaticalframework.linearizer.Linearizer
import _root_.org.grammaticalframework.Trees.PrettyPrinter

class MainActivity extends TTSActivity {

  var currentText = ""
  var resultView:TextView = null;
  var mPGFThread:PGFThread = null;
  var progress:ProgressDialog = null;

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    // Setup TTS
    setupLanguage()
    // Setup UI
    setContentView(R.layout.main)

    // Setup the thread for the pgf
    progress = ProgressDialog.show(this, "", 
				   "Loading Grammar. Please wait...", true);
    this.mPGFThread = new PGFThread(this)
    this.mPGFThread.start()

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val speakButton =
      findViewById(R.id.speak_button).asInstanceOf[Button]
    resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // setup translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
	setText("Translating...", false)
        val phrase = phraseField.getText.toString
        mPGFThread.translate(phrase)
      }
    })
    // setup speak action
    speakButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = say(currentText)
    })
  }

  def setText(text:String, sayable:Boolean) = {
    if (sayable)
      this.currentText = text
    else
      this.currentText = ""
    runOnUiThread(new Runnable() {
      def run() = resultView.setText(text)
    })
  }

  override def onLanguageIsReady()= {}
  
  def onPgfError(errmsg:String) = {}
  def onPgfReady() = {
    this.progress.dismiss();
  }
}
