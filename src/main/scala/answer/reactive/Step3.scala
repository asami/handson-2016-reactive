package answer.reactive

import scalaz._, Scalaz._  
import scalaz.concurrent.Task  
import scalaz.stream._

object Step3 {
  def main(args: Array[String]) {
    val in = args(0)
    val out = args(1)
    val t = converter(in, out)
    t.run
  }

  def converter(in: String, out: String): Task[Unit] =
    converterProcess(in, out).run

  def converterProcess(in: String, out: String): Process[Task, Unit] =
    io.linesR(in).
      map(toRecord).
      chunk(1000).map(groupByYear).pipe(process1.unchunk).
      map(toYear).
      pipe(text.utf8Encode).to(io.fileChunkW(out))

  def toRecord(s: String): Vector[String] = s.split(",").toVector

  def toYear(record: Vector[String]): String =
    record.lift(1) getOrElse "Unknown"

  def groupByYear(records: Vector[Vector[String]]): Vector[Vector[String]] = {
    case class Z(years: Set[String] = Set.empty, result: Vector[Vector[String]] = Vector.empty) {
      def +(rhs: Vector[String]) =
        rhs.lift(1).fold(this) { year =>
          if (years.contains(year))
            this
          else
            Z(years + year, result :+ rhs)
        }
    }
    records.foldLeft(Z())(_ + _).result
  }
}
