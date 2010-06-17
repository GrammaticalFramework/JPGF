package org.grammaticalframework.examples.PhraseDroid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view._
import _root_.android.content.res.Resources
import _root_.android.app.ProgressDialog
// Debuging

import _root_.org.grammaticalframework.reader.{NewReader, PGF, Concrete}
import _root_.org.grammaticalframework.parser.{Parser}
import _root_.org.grammaticalframework.linearizer.Linearizer
import _root_.org.grammaticalframework.Trees.PrettyPrinter

class MainActivity extends TTSActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    // Setup languages
    this.setupLanguages(Language.ENGLISH, Language.FRENCH)

    // Get pointers to the ui elements
    resultView = findViewById(R.id.result_view).asInstanceOf[TextView]


    // setup speak action
    findViewById(R.id.speak_button).asInstanceOf[Button].setOnClickListener(
      new View.OnClickListener() {
	def onClick(v:View) = say(currentText)
      })
  }

}
