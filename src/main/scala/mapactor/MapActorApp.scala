package mapactor

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source
import akka.util.Timeout
import common.RandomSupport

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.io.StdIn.readLine

object MapActorApp extends RandomSupport {

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem("simple-akka")
  implicit val materializer = ActorMaterializer()

  val map = system.actorOf(Props[MapActor])

  private val Attempts = 2500000
  private val KeySize  = 5000000

  private def populate(map: ActorRef) = {
    val result = Source(1 to Attempts)
      .mapAsync(1)(_ => map ? Insert(randomKey(KeySize), randomValue()))
      .runWith(Sink.ignore)

    val start = System.currentTimeMillis()
    Await.ready(result, Duration.Inf)
    val end = System.currentTimeMillis()
    println(s" Populating done in ${end - start} ms.")
  }

  private def query(map: ActorRef) = {
    val result = Source(1 to KeySize)
      .mapAsync(1)(_ => map ? Get(randomKey(KeySize)))
      .runWith(Sink.ignore)

    val start = System.currentTimeMillis()
    Await.ready(result, Duration.Inf)
    val end = System.currentTimeMillis()
    println(s" Querying done in ${end - start} ms.")
  }

  readLine("Press enter to start the benchmark...")
  populate(map)
  query(map)
  readLine("Press enter to exit...")
  system.terminate()
}
