package entryactor

import akka.persistence.PersistentActor

class MapEntryActor extends PersistentActor {
  override def persistenceId: String = self.path.name

  var state: Option[String] = None

  override def receiveRecover: Receive = {
    case Inserted(value) => state = Some(value)
  }

  override def receiveCommand: Receive = {
    case Insert(value) => {
      if (state.isEmpty) {
        persist(Inserted(value)) { inserted =>
          state = Some(inserted.value)
          sender() ! Right(inserted)
        }
      } else {
        sender() ! Left(DuplicateKey)
      }
    }
    case Get => sender() ! state
  }
}

case class Insert(value: String)
case class Inserted(value: String)
case object DuplicateKey

case object Get