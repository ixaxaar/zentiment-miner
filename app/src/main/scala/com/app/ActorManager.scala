package com.app

import akka.actor.{Actor, ActorLogging, Props, ActorRef, ActorSystem}
import akka.cluster.Member
import com.common.cluster.ClusterSupervisor

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.StreamingContext

import com.common.cluster._
import com.common.CommonSettings
import com.common.Events._
import com.spark.Events._


class ActorManager(args:Map[String, Any]) extends ClusterSupervisor {
  val settings = args("settings").asInstanceOf[CommonSettings]
  // val sc = args("sc").asInstanceOf[SparkContext]
  val ssc = args("ssc").asInstanceOf[StreamingContext]
  // val ssql = args("ssql").asInstanceOf[SQLContext]
  val system = args("system").asInstanceOf[ActorSystem]

  import settings._
  import Events._

  val spark = context.actorOf(Props(new TwitterDump(ssc)), "spark-batch-linear-regression")

  override def preStart():Unit = {
    super.preStart()
    cluster.joinSeedNodes(Vector(cluster.selfAddress))
  }

  override def initialize():Unit = {
    super.initialize()
    log.info("/////////////////////")
    log.info("// Starting nodes //")
    log.info("/////////////////////")
    spark ! SparkStreamStart

    context become actorReceive
  }

  def actorReceive:Actor.Receive = {
    case m:SparkBatchEvent => spark.forward(m)
    case m:SparkStreamEvent => spark.forward(m)
  }

  self ! Start
}
