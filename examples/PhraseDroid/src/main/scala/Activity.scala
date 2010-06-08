package org.grammaticalframework.examples.PhraseDroid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view.View
import _root_.android.content.res.Resources

import _root_.org.grammaticalframework.reader.{NewReader, PGF, Concrete}
import _root_.org.grammaticalframework.parser.{Parser}
import _root_.org.grammaticalframework.linearizer.Linearizer
import _root_.org.grammaticalframework.Trees.PrettyPrinter

class MainActivity extends TTSActivity {

  var currentText = ""
  var resultView:TextView = null;
  var mPGFThread:PGFThread = null;

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    setupLanguage()

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val speakButton =
      findViewById(R.id.speak_button).asInstanceOf[Button]
    resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // Read the pgf
    val begin_time = System.currentTimeMillis()
    val pgf_is = this.getResources().openRawResource(R.raw.two)
    val pgf = PGF.readFromInputStream(pgf_is)
    val end_time = System.currentTimeMillis()
    resultView.setText("PGF read in " + (end_time - begin_time) + " ms")
    val mTranslator = new Translator(pgf, "PhrasebookEng", "PhrasebookFre")
    this.mPGFThread = new PGFThread(this, mTranslator)
    this.mPGFThread.start()
    
    // setup translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
        val phrase = phraseField.getText.toString
        mPGFThread.translate(phrase)
      }
    })
    // setup speak action
    speakButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = say(currentText)
    })
  }

  def setTranslation(translation:String) = {
      this.currentText = translation
      this.resultView.setText(currentText)
  }

  override def onLanguageIsReady()= {}
}
