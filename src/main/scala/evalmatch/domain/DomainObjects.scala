package evalmatch.domain

case class Employee(name: String)
case class Application(receiver: Employee, writers: List[Employee])
case class Result(writer: Employee, receivers: List[Employee])
case class EvaluationPair(writer: Employee, receiver: Employee, importance: Double, index: Int)

