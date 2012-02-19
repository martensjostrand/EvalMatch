package evalmatch.io
import evalmatch.domain._
import scala.io.Source
import java.io._

trait ApplicationReader { 
  def parse(path: String): List[Application]
}

trait ResultWriter{
  def write(results: List[Result], file: String): Unit
}

class DefaultIOHandler() extends ApplicationReader with ResultWriter{ 

  override def parse(path: String): List[Application] = { 

    def parseLine(line: String) : Application = { 

      def parseWriterInfo(writerInfo: String) = { 
	val splitted = writerInfo.split(",");
	WriterInfo(Employee(splitted(0).trim), splitted(1).toDouble)
      }

      val splited = line.split(":");
      val receiver = Employee(splited(0).trim)
      val writerInfos = for(writerInfo <- splited(1).split(";")) yield parseWriterInfo(writerInfo)
      Application(receiver, writerInfos.toList)
    }

    val lines = Source.fromFile(path).getLines.toList
    for(line <- lines) yield parseLine(line)
  }
  
  def write(results: List[Result], file: String): Unit = { 
    def formatResult(result: Result): String = { 
      result.writer.name + ": " + result.receivers.map(_.name).mkString(", ")
    }

    val writer = new BufferedWriter(new FileWriter(file));

    for(line <- results.map(formatResult)){ 
      writer.write(line)
      writer.newLine()
    }
    writer.close()
  }
  
  
  
}
