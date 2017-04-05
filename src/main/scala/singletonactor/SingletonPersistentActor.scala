package singletonactor

import akka.persistence.PersistentActor

class SingletonPersistentActor extends PersistentActor {
  override def persistenceId = "singleton-persistent"

  var state = Map.empty[String, String]

  override def receiveRecover = {
    case Inserted(key, value) => state = state + (key -> value)
  }

  override def receiveCommand = {
    case Insert(key, value) => {
      if (state.contains(key)) {
        sender() ! Left(DuplicateKey)
      } else {
        persist(Inserted(key, value)) { inserted =>
          state = state + (inserted.key -> inserted.value)
          sender() ! Right(inserted)
        }
      }
    }
    case Get(key) => {
      sender() ! Right(state.get(key))
    }
  }
}

case class Insert(key: String, value: String)
case class Inserted(key: String, value: String)
case object DuplicateKey

case class Get(key: String)