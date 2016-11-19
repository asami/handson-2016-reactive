package handson.reactive
  
import scalaz.concurrent.Task
import scalaz.stream._  

object EventProcessor {
  val q = async.unboundedQueue[String]

  val eventStream: Process[Task, String] = q.dequeue
}
