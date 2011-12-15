package evalmatch

import org.specs2.mutable._
import evalmatch.domain._
import scalpi.variable.BinaryVariable

class VariableCreatorSpec extends Specification { 
  "A VariableCreator" should { 
    val variableCreator = new VariableCreator()
    "create one binary variable per pair" in {
      val e1 = Employee("e1")
      val e2 = Employee("e2")
      val p1 = EvaluationPair(e1, e2, 0.0, 0)
      val p2 = EvaluationPair(e2, e1, 0.0, 1)

      val vars = variableCreator.createVariables(List(p1, p2))
      vars must be equalTo List(BinaryVariable(), BinaryVariable())
    }
  }
}
