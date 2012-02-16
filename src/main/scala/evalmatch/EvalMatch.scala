package evalmatch

import evalmatch.domain._
import scalpi.variable.BinaryVariable
import scalpi.targetfunction._
import scalpi._
import scala.collection.immutable.HashSet
import scalpglpk._

object EvalMatch {

  // Configuration:
  val maxToWrite = 100
  val minToReceive = 0
  val solver = GLPKSolver
    //DefaultProblemSolver

  // Building blocks:
  val pairGenerator = new PairGenerator()
  val variableCreator = new VariableCreator()
  val targetFunctionCreator = new TargetFunctionCreator()
  val constraintCreator = new ConstraintCreator(maxToWrite, minToReceive)

  def findMatch(applications: List[Application]): List[Result] = { 
    val pairs = pairGenerator.createPairs(applications)
    val problemDescription = createProblemDescription(pairs)
    println("Problem description is:\n" + problemDescription)
    val solution = solver.solve(problemDescription)
    println("Solution is:\n" + solution + "\n")
    createResultList(solution, pairs)
  }

  private def createResultList(solution: ProblemSolution, pairs: List[EvaluationPair]) = { 
    val trueIndexes = for((value, index) <- solution.variableValues.zipWithIndex; if value.getValue == true) yield index 
    println("trueIndexes: " + trueIndexes);
    val acceptedPairs = trueIndexes.map(index => pairs(index))
    println("acceptedPairs: " + acceptedPairs)
    val perWriter = acceptedPairs.groupBy(_.writer)
    println("perWriter: " + perWriter)
    val results = for((writer, pairsByWriter) <- perWriter ) yield Result(writer, pairsByWriter.map(_.receiver)) 
    println("results: " + results)
    results.toList
  }

  private def createProblemDescription(pairs: List[EvaluationPair]) = { 
    // Create one binary variable for each pair.
    val variables = variableCreator.createVariables(pairs)

    // Create a target function with the importance of each pair.
    val targetFunction = targetFunctionCreator.createTargetFunction(pairs)
    println("target function: " + targetFunction)
    // Create the maxwrite and min receive constraints.
    val writeConstraints = constraintCreator.createMaxToWriteConstraints(pairs)
    val receiveConstraints = constraintCreator.createMinToReceiveConstraints(pairs)
    val constraints = writeConstraints ::: receiveConstraints

    // Create and solve the problem:
    ProblemDescription(targetFunction, constraints, variables)
    
  }
}
