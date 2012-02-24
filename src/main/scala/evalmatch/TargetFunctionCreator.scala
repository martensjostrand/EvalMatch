package evalmatch

import evalmatch.domain._
import scalpi.targetfunction._

class TargetFunctionCreator { 
  def createTargetFunction(pairs: List[EvaluationPair]): TargetFunction = { 
    // pairs.map{p=> println("Importance:" + p.importance)}
    // val weights = pairs.map{p => p.importance}
    // Use -(N-p.importance) where N is greater than max importance.
    val maxImportance = pairs.map(_.importance).sortWith(_ > _).head + 10.0
    val weights = pairs.map{p => -(maxImportance - p.importance)}
    Maximize(weights)
  }
}
