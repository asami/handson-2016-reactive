package answer.reactive

import scalaz._, Scalaz._  
import scalaz.concurrent.Task  
import scalaz.stream._

object Step1 {
  def main(args: Array[String]) {
    val in = args(0)
    val out = args(1)
    val t = converter(in, out)
    t.run
  }

  def converter(in: String, out: String): Task[Unit] =
    converterProcess(in, out).run

  def converterProcess(in: String, out: String): Process[Task, Unit] =
    io.linesR(in).pipe(text.utf8Encode).to(io.fileChunkW(out))
}
