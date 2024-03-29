package evalmatch

import org.specs2.mutable._
import evalmatch.domain._

class EvalMatchSpec extends Specification { 
  "EvalMatch" should {
    "Match correctly" in {
      val matcher = new EvalMatch(1, 1)
      val e1 = Employee("e1");
      val e2 = Employee("e2");
      val e3 = Employee("e3");

      val a1 = Application(e1, List(WriterInfo(e2, 1.0), WriterInfo(e3, 1.0)))
      val a2 = Application(e2, List(WriterInfo(e1,1.0), WriterInfo(e3,1.0)))

      val res = matcher.findMatch(List(a1, a2))

      res must not be equalTo(Nil)
    }

  }
}
