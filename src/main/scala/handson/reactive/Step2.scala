package handson.reactive

import scalaz._, Scalaz._  
import scalaz.concurrent.Task  
import scalaz.stream._

object Step2 {
  def main(args: Array[String]) {
    val in = args(0)
    val out = args(1)
    val player = args(2)
    val t = converter(in, out, player)
    t.run
  }

  def converter(in: String, out: String, player: String): Task[Unit] =
    converterProcess(in, out, player).run

  def converterProcess(in: String, out: String, player: String): Process[Task, Unit] =
    io.linesR(in).
      map(toRecord).filter(isPlayer(player)).map(toYear).
      pipe(text.utf8Encode).to(io.fileChunkW(out))

  def toRecord(s: String): Vector[String] = s.split(",").toVector

  def isPlayer(player: String)(record: Vector[String]): Boolean = ???

  def toYear(record: Vector[String]): String = ???
}
