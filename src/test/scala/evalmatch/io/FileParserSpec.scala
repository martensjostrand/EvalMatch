package evalmatch.io

import org.specs2.mutable._
import evalmatch.domain._

import evalmatch.domain._
import scala.io.Source

import java.io.File

class FileParserSpec extends Specification { 
  "FileParser" should { 
    val parser = new DefaultIOHandler()
    "Parse file" in { 
      val applications = parser.parse("src/test/resources/testData.txt")
      
      val app1 = Application(Employee("Marten Sjostrand"), List(WriterInfo(Employee("Kalle Svensson"), 120), WriterInfo(Employee("Sven Karlsson"), 90)))
      val app2  = Application(Employee("Kalle Svensson"), List(WriterInfo(Employee("Marten Sjostrand"), 90), WriterInfo(Employee("Sven Karlsson"), 78)))
      val app3  = Application(Employee("Sven Karlsson"), List(WriterInfo(Employee("Marten Sjostrand"), 90), WriterInfo(Employee("Kalle Svensson"), 67)))
      val expected = List(app1, app2, app3)
      applications must be equalTo expected
    }
    "Write data" in { 
      val file = "target/testOutput.txt"
      new File(file).delete

      val r1 = Result(Employee("e1"), List(Employee("e2"), Employee("e3")))
      val r2 = Result(Employee("e2"), List(Employee("e1"), Employee("e3")))
      parser.write(List(r1,r2) , file)
      
      val lines = Source.fromFile(file).getLines.toList
      val expectedLines = List("e1: e2, e3","e2: e1, e3")
      new File(file).delete
      lines must be equalTo expectedLines
    }
  }
}
