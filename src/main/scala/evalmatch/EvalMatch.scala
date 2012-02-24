package evalmatch

import evalmatch.domain._
import evalmatch.io._
import scalpi.variable.BinaryVariable
import scalpi.targetfunction._
import scalpi._
import scala.collection.immutable.HashSet
import scalpglpk._

object EvalMatchRunner { 
  def main(args: Array[String]) {
    if(args.size != 4) { 
      println("Run with: EvalmatchRunner inputFile outputFile maxToWrite minToReceive")
      println("Example: EvalmatchRunner /usr/me/inputFile.txt /usr/me/outputFile.txt 7 2")
    } else { 
      val maxToWrite = args(2).toInt
      val minToReceive = args(3).toInt
      println("Using maxToWrite: " + maxToWrite)
      println("Using minToReceive: " + minToReceive)
      println("Using applications from: " + args(0))
      println("Writing retults to: " + args(1))
      
      val matcher = new EvalMatch(maxToWrite,minToReceive)
      val iohandler = new DefaultIOHandler
      val applications = iohandler.parse(args(0))
      val results = matcher.findMatch(applications)
      iohandler.write(results, args(1))

    }
  }
}

class EvalMatch(maxToWrite: Int, minToReceive: Int) {

  // Configuration:
  // val maxToWrite = 1
  // val minToReceive = 0
  val solver = GLPKSolver

  // Building blocks:
  val pairGenerator = new PairGenerator()
  val variableCreator = new VariableCreator()
  val targetFunctionCreator = new TargetFunctionCreator()
  val constraintCreator = new ConstraintCreator(maxToWrite, minToReceive)

  def findMatch(applications: List[Application]): List[Result] = { 
    val pairs = pairGenerator.createPairs(applications)
    val problemDescription = createProblemDescription(pairs)
    val solution = solver.solve(problemDescription)
    createResultList(solution, pairs)
  }

  private def createResultList(solution: ProblemSolution, pairs: List[EvaluationPair]) = { 
    val trueIndexes = for((value, index) <- solution.variableValues.zipWithIndex; if value.getValue == true) yield index 
    val acceptedPairs = trueIndexes.map(index => pairs(index))
    val perWriter = acceptedPairs.groupBy(_.writer)
    val results = for((writer, pairsByWriter) <- perWriter ) yield Result(writer, pairsByWriter.map(_.receiver)) 
    results.toList
  }

  private def createProblemDescription(pairs: List[EvaluationPair]) = { 
    val variables = variableCreator.createVariables(pairs)
    val targetFunction = targetFunctionCreator.createTargetFunction(pairs)

    // Create the maxwrite and min receive constraints.
    val writeConstraints = constraintCreator.createMaxToWriteConstraints(pairs)
    val receiveConstraints = constraintCreator.createMinToReceiveConstraints(pairs)
    val constraints = writeConstraints ::: receiveConstraints

    // Create and return the problem:
    ProblemDescription(targetFunction, constraints, variables)
  }
}
