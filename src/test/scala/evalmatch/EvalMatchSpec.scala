package evalmatch

import org.specs2.mutable._
import evalmatch.domain._

class EvalMatchSpec extends Specification { 
  "EvalMatch" should {
    "Create pairs" in { 
      val e1 = Employee("e1");
      val e2 = Employee("e2");
      val e3 = Employee("e3");
      val a1 = Application(e1, List(e2, e3))
      val a2 = Application(e2, List(e1, e3))

      val res = EvalMatch.findMatch(List(a1, a2))
      res must be equalTo Nil
    }

  }
}
