package com.app

import akka.actor.{ActorSystem, PoisonPill, Props}

import com.common.CommonSettings
import com.spark.{Settings => SparkSettings}
import com.spark._
import com.cassandra._

import org.apache.spark.streaming.StreamingContext
import com.common.Events._
import com.spark.Events._


object ApplicationMain extends App {

  val settings = new CommonSettings
  import settings._
  val sparkSettings = new SparkSettings
  import sparkSettings._

  import CassandraSparkContext._

  val system = ActorSystem(settings.AppName)
  val rootActor = system.actorOf(Props(new ActorManager(Map(
      "system" -> system,
      "settings" -> settings,
      // "sc" -> sc,
      "ssc" -> ssc
      // "ssql" -> sqlContext
    ))), "actor-manager")

  system.registerOnTermination {
    rootActor ! SparkStreamStop
    rootActor ! SparkBatchStop
    rootActor ! PoisonPill
  }
  system.awaitTermination()
}
