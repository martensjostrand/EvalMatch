package evalmatch

import org.specs2.mutable._
import scalpi.constraint.Constraint
import scalpi.constraint.ConstraintType._

import evalmatch.domain._

class ConstraintCreatorSpec extends Specification { 

  "A ConstraintCreator" should { 
    val maxToWrite = 3
    val minToReceive = 3
    val constraintCreator = new ConstraintCreator(maxToWrite, minToReceive)

    val e1 = Employee("e1")
    val e2 = Employee("e2")
    val e3 = Employee("e3")
    val p12 = EvaluationPair(e1, e2, 0.0, 0)
    val p13 = EvaluationPair(e1, e3, 0.0, 1)

    "create correct max to write constraints" in { 
      val constraints = constraintCreator.createMaxToWriteConstraints(List(p12, p13))
      constraints must be equalTo List(Constraint(List(1, 1), LT, maxToWrite))
    }

    "create correct min to receive constraints" in { 
      val constraints = constraintCreator.createMinToReceiveConstraints(List(p12, p13))
      constraints must be equalTo List(Constraint(List(0,1), GT, minToReceive), Constraint(List(1,0), GT, minToReceive))
    }
  }
}
