import org.scalacheck._
import Gen._
import Arbitrary.arbitrary
import pgf.abstractTrees._

object AbstractTreeGenerator {
  
  val genLiteral = for {
    v <- arbitrary[String]
  } yield new Literal(v)

  val genFunction = for {
    name <- arbitrary[String]
  } yield new Function(name)

  def genLambda(vars:Int): Gen[Lambda] = sized { size => 
    for {
      bindType <- arbitrary[Boolean]
      varName <- alphaStr
      body <- resize(size - 1,genTree(vars + 1))
    } yield new Lambda(bindType, varName, body)
  }

  def genApplicable(vars : Int):Gen[Tree] = Gen.sized { size =>
    oneOf(genFunction, genLambda(vars))
  }

  def genApplication(vars:Int):Gen[Tree] = Gen.sized { size => 
    for {
      fun <- genApplicable(vars)
      arg <- resize(size - 1, genTree(vars))
    } yield new Application(fun, arg)
  }
  
  def genVar(vars:Int):Gen[Tree] = for {
    i <- choose(0,vars - 1)
  } yield new Variable(i)

  def genTree(vars:Int): Gen[Tree] =  Gen.sized { size => 
    if (size > 0)
      if (vars > 0)
	oneOf(genLiteral, genLambda(vars), 
	      genApplication(vars), genVar(vars))
      else
	oneOf(genLiteral, genApplication(vars), genLambda(vars))
    else
      if (vars > 0)
	oneOf(genLiteral, genVar(vars))
      else
	oneOf(genLiteral)
  }

  implicit def arbTree: Arbitrary[Tree] = Arbitrary(genTree(0))
}
