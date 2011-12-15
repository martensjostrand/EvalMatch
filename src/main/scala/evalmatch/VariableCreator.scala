package evalmatch

import scalpi.variable.BinaryVariable
import evalmatch.domain._

class VariableCreator {

  def createVariables(pairs: List[EvaluationPair]) = { 
    pairs.map{ _ => BinaryVariable()}
  }
}
