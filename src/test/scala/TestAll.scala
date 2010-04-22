import org.scalacheck._

object TestAll {
  object AllSpecifications extends Properties("ScalaCheck") {
    include(TreeConversionSpecification)
    //include(TreeConversionSpecification2)
  }

  def main(args: Array[String]) {
    AllSpecifications.check
    1
  }
}
