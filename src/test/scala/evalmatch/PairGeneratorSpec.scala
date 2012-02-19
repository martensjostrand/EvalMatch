package evalmatch

import org.specs2.mutable._
import evalmatch.domain._

class PairGeneratorSpec extends Specification { 

  "A PairGenerator" should { 
    val pairGenerator = new PairGenerator()
    
    "create pairs from multiple applications" in { 
      val e1 = Employee("e1")
      val e2 = Employee("e2")
      val e3 = Employee("e3")

      val a1 = Application(e1, List(WriterInfo(e3, 1.0)))
      val a2 = Application(e2, List(WriterInfo(e3,2.0)))

      val pairs = pairGenerator.createPairs(List(a1, a2))
      pairs must be equalTo List(EvaluationPair(e3, e1, 1.0, 0), EvaluationPair(e3, e2, 2.0, 1))
    }

    "create an empty list of pairs when input is empty" in { 
      val pairs = pairGenerator.createPairs(List())
      pairs must be equalTo List()
    }

    "ignore double pair defenitions" in { 
      val e1 = Employee("e1")
      val e2 = Employee("e2")
      val a1 = Application(e1, List(WriterInfo(e2, 1.0)))
      val a2 = Application(e1, List(WriterInfo(e2,1.0)))

      val pairs = pairGenerator.createPairs(List(a1, a2))
      pairs must be equalTo List(EvaluationPair(e2, e1, 1.0, 0))
    }
  }

}
