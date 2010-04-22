import org.scalacheck._
import Gen._
import Arbitrary.arbitrary
import pgf.intermediateTrees._

object IntermediateTreeGenerator {
  
  val genLiteral = for {
    v <- arbitrary[String]
  } yield new Literal(v)

  val genLambdaVariable = for {
    b <- arbitrary[Boolean]
    n <- alphaStr
  } yield (b,n)

  def genLambdaBody(vars:List[String]): Gen[Tree] = 
    oneOf(genLiteral, genApplication(vars))
  
  def genLambda(vars:List[String]): Gen[Lambda] = sized { size => 
    for {
      newvars <- listOf1(genLambdaVariable)
      body <- resize(size - 1,genLambdaBody(vars))
    } yield new Lambda(newvars, body)
  }

  def genApplication(vars:List[String]):Gen[Tree] = Gen.sized { size => 
    for {
      name <- alphaStr
      j <- choose(0,size)
      args <- resize(j, listOf1(resize(size - j, genTree(vars))))
    } yield new Application(name, args)
  }
  
  def genVar(vars:List[String]):Gen[Tree] = for {
    i <- choose(0,vars.length - 1)
  } yield new Variable(vars(i))

  def genTree(vars:List[String]): Gen[Tree] =  Gen.sized { size => 
    if (size > 0)
      vars match {
	case Nil => 
	  oneOf(genLiteral, genApplication(vars), genLambda(vars))
	case _ => 
	  oneOf(genLiteral, genLambda(vars), genApplication(vars), genLambda(vars))
      }
    else
      vars match {
	case Nil => 
	  oneOf(genLiteral)
	case _ => 
	  oneOf(genLiteral)
      }
  }

  implicit def arbTree: Arbitrary[Tree] = Arbitrary(genTree(Nil))
}
