package entryactor

import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.sharding.ClusterSharding
import akka.cluster.sharding.ClusterShardingSettings
import akka.cluster.sharding.ShardRegion

trait ShardingSupport {

  def startSharding(implicit system: ActorSystem) = {
    ClusterSharding(system).start(
      typeName = "map-entry",
      entityProps = Props[MapEntryActor],
      settings = ClusterShardingSettings(system),
      extractEntityId = extractEntityId(),
      extractShardId = extractShardId(13)
    )
  }

  private def extractEntityId(): ShardRegion.ExtractEntityId = {
    case Envelope(entityId, msg) => (entityId, msg)
  }

  private def extractShardId(numberOfShards: Int): ShardRegion.ExtractShardId = {
    case Envelope(entityId, _) => (entityId.hashCode % numberOfShards).toString
  }
}

case class Envelope[A](key: String, message: A)
