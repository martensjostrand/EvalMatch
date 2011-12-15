package evalmatch

import evalmatch.domain._
import scalpi.targetfunction._

class TargetFunctionCreator { 
  def createTargetFunction(pairs: List[EvaluationPair]): TargetFunction = { 
    val weights = pairs.map{p => p.importance}
    Maximize(weights)
  }
}
