package evalmatch

import org.specs2.mutable._
import evalmatch.domain._

class PairGeneratorSpec extends Specification { 

  "A PairGenerator" should { 
    val pairGenerator = new PairGenerator()
    
    "create pairs from multiple applications" in { 
      val e1 = Employee("e1")
      val e2 = Employee("e2")
      val a1 = Application(e1, List(e2))
      val a2 = Application(e2, List(e1))

      val pairs = pairGenerator.createPairs(List(a1, a2))
      pairs must be equalTo List(EvaluationPair(e1, e2, 0.0, 0), EvaluationPair(e2, e1, 0.0, 1))
    }

    "create an empty list of pairs when input is empty" in { 
      val pairs = pairGenerator.createPairs(List())
      pairs must be equalTo List()
    }

    "ignore double pair defenitions" in { 
      val e1 = Employee("e1")
      val e2 = Employee("e2")
      val a1 = Application(e1, List(e2))
      val a2 = Application(e1, List(e2))

      val pairs = pairGenerator.createPairs(List(a1, a2))
      pairs must be equalTo List(EvaluationPair(e1, e2, 0.0, 0))
    }
  }

}
