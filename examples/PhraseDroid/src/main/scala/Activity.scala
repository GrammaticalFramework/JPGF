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

class MainActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val speakButton =
      findViewById(R.id.speak_button).asInstanceOf[Button]
    val resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // Read the pgf
    val begin_time = System.currentTimeMillis()
    val pgf_is = this.getResources().openRawResource(R.raw.two)
    val pgf = PGF.readFromInputStream(pgf_is)
    val parser = new Parser(pgf.concrete("PhrasebookEng"))
    val linearizer = new Linearizer(pgf, pgf.concrete("PhrasebookFre"))
    val end_time = System.currentTimeMillis()
    resultView.setText("PGF read in " + (end_time - begin_time) + " ms")

    // translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
      val phrase = phraseField.getText.toString
        resultView.setText(translate(parser,linearizer,phrase))
      }
    })
//     // Speak action
//     translateButton.setOnClickListener( new View.OnClickListener() {
//       def onClick(v:View) = {
//       val phrase = phraseField.getText.toString
//         resultView.setText(parse(parser,phrase))
//       }
//     })
  }

  def parse(parser:Parser, txt:String):String = {
    val begin_time = System.currentTimeMillis()
    val tokens = txt.split(" ")
    parser.parse(tokens)
    val end_time = System.currentTimeMillis()
    val parse_time = end_time - begin_time
    val trees = parser.getTrees
    var s = ""
    trees.map(PrettyPrinter.print).foreach(s += _)
    s+="("+parse_time+" ms)"
    return s
  }

  def translate(parser:Parser, linearizer:Linearizer, txt:String):String = {
    val begin_time = System.currentTimeMillis()
    val tokens = txt.split(" ")
    parser.parse(tokens)
    val trees = parser.getTrees
    val inter_time = System.currentTimeMillis()
    var s = ""
    trees.foreach( t => {
      s += linearizer.renderLin(linearizer.linearize(t)).toArray.mkString(" ")
    })
    val end_time = System.currentTimeMillis()
    return s
  }

}
