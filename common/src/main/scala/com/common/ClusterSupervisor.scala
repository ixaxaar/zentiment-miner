package com.common.cluster

import java.util.concurrent.TimeoutException
import akka.actor._
import akka.pattern.gracefulStop
import akka.actor.SupervisorStrategy._
import akka.util.Timeout
import akka.cluster.Member
import scala.concurrent.duration._



abstract class ClusterSupervisor extends ClusterActor {

  import com.common.Events._

  // Exception handling strategy
  override val supervisorStrategy =
    OneForOneStrategy() {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalStateException    => Restart
      case _: IllegalArgumentException => Stop
      case _: TimeoutException         => Escalate
      case _: Exception                => Escalate
    }

  override def preStart():Unit = {
    super.preStart()
    log.info("Starting at ", cluster.selfAddress)
  }

  override def postStop(): Unit = {
    super.postStop()
    log.info("Node {} state change \t\t Normal -> Shutting down", cluster.selfAddress)
    cluster.leave(self.path.address)
    gracefulShutdown()
  }

  // Bubble received messages from bottom to top
  override def receive = uninitialized orElse actorReceive orElse super.receive

  override def handleRegistration(member:Member):Unit = ()

  // Initialize actor
  def uninitialized():Actor.Receive = {
    case Start => initialize()
  }

  def initialize():Unit = {
    log.info(s"Node state change \t\t Uninitialized -> Initialized")
    log.info("Node state change \t\t Initialized -> Running")
    context.system.eventStream.publish(Initialized)
  }

  // Template for actors to implement
  def actorReceive: Actor.Receive

  // Gracefull shut down all children actors
  def gracefulShutdown():Unit = {
    val timeout = Timeout(5.seconds)
    context.children.foreach(gracefulStop(_, timeout.duration))
    log.info("Node state change \t\t Shutting down -> Down")
  }
}
