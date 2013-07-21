/* Activity.scala
 * Copyright (C) 2012 Grégoire Détrez
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
