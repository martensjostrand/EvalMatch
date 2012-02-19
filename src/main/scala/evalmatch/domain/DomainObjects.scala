package evalmatch.domain

case class Employee(name: String)
case class WriterInfo(writer: Employee, importance: Double)
case class Application(receiver: Employee, writerInfos: List[WriterInfo])
case class Result(writer: Employee, receivers: List[Employee])
case class EvaluationPair(writer: Employee, receiver: Employee, importance: Double, index: Int)

