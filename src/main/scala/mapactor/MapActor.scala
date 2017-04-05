package mapactor

import akka.NotUsed
import akka.actor.Actor

class MapActor extends Actor {
  def receive = receive(Map.empty)

  private def receive(store: Map[String, String]): Receive = {
    case Insert(key, value) => {
      if (store.contains(key)) {
        sender() ! Left(DuplicateKey)
      } else {
        context.become(receive(store + (key -> value)))
        sender() ! Right(NotUsed)
      }
    }
    case Get(key) => {
      sender() ! Right(store.get(key))
    }
  }
}

case class Insert(key: String, value: String)
case class Inserted(key: String, value: String)
case object DuplicateKey

case class Get(key: String)