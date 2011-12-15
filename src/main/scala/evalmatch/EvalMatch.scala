package evalmatch

import evalmatch.domain._
import scalpi.variable.BinaryVariable
import scalpi.targetfunction._
import scalpi._
import scala.collection.immutable.HashSet

object EvalMatch {

  // Configuration:
  val maxToWrite = 3
  val minToReceive = 2
  val solver = DefaultProblemSolver

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
    // Create one binary variable for each pair.
    val variables = variableCreator.createVariables(pairs)

    // Create a target function with the importance of each pair.
    val targetFunction = targetFunctionCreator.createTargetFunction(pairs)

    // Create the maxwrite and min receive constraints.
    val writeConstraints = constraintCreator.createMaxToWriteConstraints(pairs)
    val receiveConstraints = constraintCreator.createMinToReceiveConstraints(pairs)
    val constraints = writeConstraints ::: receiveConstraints

    // Create and solve the problem:
    ProblemDescription(targetFunction, constraints, variables)
  }
}
