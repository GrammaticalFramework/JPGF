import org.scalacheck._
import PGF.Parsing.{TreeConverter}


// object TreeConversionSpecification extends Properties("TreeConverter") {
//   import IntermediateTreeGenerator._
//   import Prop.forAll
//   import pgf.intermediateTrees.{Tree}

//   property("i2a2i") =
//     forAll ( (t: Tree) => {
//       val tprime = TreeConverter.abstract2intermediate(
// 	TreeConverter.intermediate2abstract(t))
//       //println(tprime)
//       tprime == t
//     }
//     )
// }

// object TreeConversionSpecification2 extends Properties("TreeConverter") {
//   import AbstractTreeGenerator._
//   import Prop.forAll
//   import pgf.abstractTrees.{Tree}

//   property("a2i2a") =
//     forAll ( (t: Tree) => {
//       val tprime = TreeConverter.intermediate2abstract(
// 	TreeConverter.abstract2intermediate(t))
//       //println(tprime)
//       tprime == t
//     }
//     )
// }
