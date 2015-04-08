package com.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import akka.actor.{Actor, ActorLogging, Props, ActorRef, Cancellable}

import scala.concurrent.duration._
// import system.dispatcher


abstract class SparkBatch(sc:SparkContext, sqlContext:SQLContext) extends Actor with ActorLogging {
  import Events._
  val settings = new Settings
  import settings._

  import sqlContext._

  def receive:Actor.Receive = maintainState

  def maintainState:Actor.Receive = {
    case SparkBatchShuttingDown =>
      self ! SparkBatchDown
    case SparkBatchStart =>
      self ! SparkBatchRunning
    case SparkBatchStop =>
      sc.stop
      self ! SparkBatchShuttingDown
    case SparkBatchDown =>
    case SparkBatchUninitialized =>
    case SparkBatchInitialized =>
    case SparkBatchRunning =>
      batch(sc, sqlContext)
      self ! SparkBatchDown
  }

  // template for the batch process
  def batch(spk:SparkContext, sql:SQLContext):Unit
}


