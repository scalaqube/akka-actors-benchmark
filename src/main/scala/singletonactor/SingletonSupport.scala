package singletonactor

import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.actor.Props
import akka.cluster.singleton.ClusterSingletonManager
import akka.cluster.singleton.ClusterSingletonManagerSettings
import akka.cluster.singleton.ClusterSingletonProxy
import akka.cluster.singleton.ClusterSingletonProxySettings

trait SingletonSupport {

  def createSingleton(implicit system: ActorSystem) = {

    val singletonManagerProps = ClusterSingletonManager.props(
      singletonProps = Props[SingletonPersistentActor],
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)
    )
    val manager = system.actorOf(singletonManagerProps)

    val proxyProps = ClusterSingletonProxy.props(
      singletonManagerPath = manager.path.toStringWithoutAddress,
      settings = ClusterSingletonProxySettings(system)
    )

    system.actorOf(proxyProps)
  }
}