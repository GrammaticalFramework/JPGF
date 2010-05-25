package se.chalmers

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view.View
import _root_.android.content.res.Resources
//import android.view.Menu._
import _root_.org.grammaticalframework.reader.{NewReader, PGF, Concrete}
import _root_.org.grammaticalframework.parser.{Parser}
import _root_.org.grammaticalframework.Trees.PrettyPrinter

class MainActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Read the pgf
    val pgf_is = this.getResources().openRawResource(R.raw.foods)
    val reader = new NewReader
    val pgf = reader.process(pgf_is)
    val parser = new Parser(pgf.concrete("FoodsEng"))

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
        val phrase = phraseField.getText.toString
        resultView.setText(parse(parser,phrase))
      }
    })
  }

  def parse(parser:Parser, txt:String):String = {
    val tokens = txt.split(" ")
    parser.parse(tokens)
    val trees = parser.getTrees
    var s = ""
    trees.map(PrettyPrinter.print).foreach(s += _)
    return s
  }
}
