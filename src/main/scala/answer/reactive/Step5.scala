package answer.reactive

import scalaz._, Scalaz._  
import scalaz.concurrent.Task  
import scalaz.stream._
import scala.concurrent.Future  
import scala.concurrent.ExecutionContext.Implicits.global
import scalax.io._
import scalax.io.JavaConverters._
import java.io.File

object Step5 {
  def main(args: Array[String]) {
    val in = args(0)

    val stream = EventProcessor.eventStream
    build(stream)
    stimulus(in)
  }

  def build(stream: Process[Task, String]) {
    Future {
      val t = converterProcess(stream).to(io.printLines(System.out))
      t.run.run
    }  
  }  

  def converterProcess(source: Process[Task, String]): Process[Task, String] =
    source.map(toRecord).pipe(toYear)

  def toRecord(s: String): Vector[String] = s.split(",").toVector

  def toYear: Process1[Vector[String], String] = ???

  def stimulus(in: String) {
    val queue = EventProcessor.q
    val input = new File(in).asInput
    input.lines() foreach { line =>
      queue.enqueueOne(line).run
      Thread.sleep(1000)
    }
  }
}
