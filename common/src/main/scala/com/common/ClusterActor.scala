package com.common.cluster


import akka.actor.{Actor, ActorLogging}
import akka.cluster.{Metric, NodeMetrics, Cluster, MemberStatus, Member}
import akka.cluster.ClusterEvent._


abstract class ClusterActor extends Actor with ActorLogging {
  val cluster = Cluster(context.system)

  override def preStart():Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def postStop():Unit = {
    cluster.unsubscribe(self)
  }

  def receive:Actor.Receive = {
    case MemberUp(member) =>
      log.info("Member {} joined cluster", member.address)
      handleRegistration(member)
    case MemberExited(member) =>
      log.info("Member {} exited", member.address)
    case MemberRemoved(member, previousStatus) =>
      log.info("Member {} removed after {}", member.address, previousStatus)
    case UnreachableMember(member) =>
      log.warning("Member unreachable {}", member)
    case state:CurrentClusterState =>
      state.members.filter(_.status == MemberStatus.Up) foreach handleRegistration
  }

  def handleRegistration(member:Member):Unit
}

