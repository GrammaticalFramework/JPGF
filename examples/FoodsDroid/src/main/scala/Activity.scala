package org.grammaticalframework.examples.FoodsDroid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.widget._
import _root_.android.view.View
import _root_.android.content.res.Resources

import _root_.org.grammaticalframework.reader.{Concrete}
import _root_.org.grammaticalframework.{Linearizer, Parser, PGF, PGFBuilder}
import _root_.org.grammaticalframework.Trees.PrettyPrinter

class MainActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Read the pgf
    val pgf_is = this.getResources().openRawResource(R.raw.foods)
    val pgf = PGFBuilder.fromInputStream(pgf_is)
    val parser = new Parser(pgf, "FoodsEng")
    val linearizer = new Linearizer(pgf, pgf.concrete("FoodsIta"))

    // Get pointers to the ui elements
    val phraseField = findViewById(R.id.phrase).asInstanceOf[EditText]
    val translateButton =
      findViewById(R.id.translate_button).asInstanceOf[Button]
    val resultView = findViewById(R.id.result_view).asInstanceOf[TextView]

    // translate action
    translateButton.setOnClickListener( new View.OnClickListener() {
      def onClick(v:View) = {
      val phrase = phraseField.getText.toString
        resultView.setText(translate(parser,linearizer,phrase))
      }
    })
  }

  def translate(parser:Parser, linearizer:Linearizer, txt:String):String = {
    val tokens = txt.split(" ")
    val trees = parser.parse(tokens).getTrees
    var s = ""

    trees.foreach( t => {
      s += linearizer.linearizeString(t)
      //renderLin(linearizer.linearize(t).elementAt(0)).toArray.mkString(" ")
    })
    return s
  }
}
