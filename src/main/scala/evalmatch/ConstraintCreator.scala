package evalmatch

import evalmatch.domain._
import scalpi.constraint._
import scalpi.constraint.ConstraintType._
import scala.collection.immutable.HashSet

class ConstraintCreator(maxToWrite: Int, minToReceive: Int) { 

  def createMaxToWriteConstraints(pairs: List[EvaluationPair]): List[Constraint] = { 

    def createCoeffecients(nonZeroIndices: List[Int]): List[Double] = { 
      var zeroes = pairs.map(_ => 0.0);

      nonZeroIndices.foldLeft(zeroes){ 
	(zeroes, index) => zeroes.updated(index, 1.0)
      }
    }

    val writers = pairs.foldLeft(List[Employee]()){(writers, pair) => { 
       pair.writer :: writers
      }}.distinct

    val nestedVariableIndices = for(writer <- writers) yield pairs.filter{
      case EvaluationPair(aWriter, _, _, _) if writer == aWriter => true
      case _ => false
    }.map {_.index}

    for(variableIndices <- nestedVariableIndices) 
      yield Constraint(createCoeffecients(variableIndices), LT, maxToWrite)
  }
  
  def createMinToReceiveConstraints(pairs: List[EvaluationPair]): List[Constraint] = { 
    def createCoeffecients(nonZeroIndices: List[Int]): List[Double] = { 
      var zeroes = pairs.map(_ => 0.0);

      nonZeroIndices.foldLeft(zeroes){ 
	(zeroes, index) => zeroes.updated(index, 1.0)
      }
    }

    val receivers = pairs.foldLeft(List[Employee]()){(receivers, pair) => { 
      pair.receiver :: receivers
    }}.distinct

    val nestedVariableIndices = for(receiver <- receivers) yield pairs.filter{
      case EvaluationPair(_, aReceiver, _, _) if receiver == aReceiver => true
      case _ => false
    }.map {_.index}

    for(variableIndices <- nestedVariableIndices) 
      yield Constraint(createCoeffecients(variableIndices), GT, minToReceive)
  }

}
