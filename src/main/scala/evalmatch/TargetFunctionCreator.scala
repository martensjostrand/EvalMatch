package evalmatch

import evalmatch.domain._
import scalpi.targetfunction._

class TargetFunctionCreator { 
  def createTargetFunction(pairs: List[EvaluationPair]): TargetFunction = { 
    pairs.map{p=> println("Importance:" + p.importance)}
    // val weights = pairs.map{p => p.importance}
    // Use -(N-p.importance) where N is greater than max importance.
    val weights = pairs.map{p => 1.0}
    Maximize(weights)
  }
}
