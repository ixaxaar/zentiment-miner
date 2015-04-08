package com.app

import akka.actor.{Actor, ActorLogging, Props, ActorRef}
import akka.cluster.Member
import com.common.cluster.ClusterSupervisor

import com.common.cluster._
import com.common.CommonSettings
import com.common.Events._
import com.spark.Events._


class ActorManager(settings:CommonSettings) extends ClusterSupervisor {
  import settings._
  import Events._

  // val ping = context.actorOf(Props(new PingActor(settings)), "ping-actor")
  // val pong = context.actorOf(Props(new PongActor(settings)), "pong-actor")

  val ping = context.actorOf(Props(new PingActor), "ping-actor")
  val pong = context.actorOf(Props(new PongActor), "pong-actor")
  val spark = context.actorOf(Props(new SparkApp("spark")), "spark-batch-linear-regression")

  override def preStart():Unit = {
    super.preStart()
    cluster.joinSeedNodes(Vector(cluster.selfAddress))
  }

  override def initialize():Unit = {
    super.initialize()
    log.info("/////////////////////")
    log.info("// Starting nodes //")
    log.info("/////////////////////")
    // ping ! Start
    spark ! SparkBatchStart

    context become actorReceive
  }

  def actorReceive:Actor.Receive = {
    case m:PingMessage => ping.forward(m)
    case m:PongMessage => pong.forward(m)
  }

  self ! Start
  // def handleRegistration()
}
