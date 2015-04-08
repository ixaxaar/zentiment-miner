package com.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{StreamingContext}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import akka.actor.{Actor, ActorLogging, Props, ActorRef, Cancellable}


abstract class SparkStreaming(ssc:StreamingContext) extends Actor with ActorLogging {
  import Events._

  def receive:Actor.Receive = maintainState

  def maintainState:Actor.Receive = {
    case SparkStreamShuttingDown =>
      self ! SparkStreamDown
    case SparkStreamStart =>
      log.info("/////////////////////////////////////")
      log.info("// Starting Spark Streaming Server //")
      log.info("/////////////////////////////////////")
      stream(ssc)
      ssc.checkpoint("./checkpoint")
      ssc.start()
      self ! SparkStreamRunning
    case SparkStreamStop =>
      ssc.stop(stopSparkContext=false, stopGracefully = true)
      self ! SparkStreamShuttingDown
    case SparkStreamDown =>
    case SparkStreamUninitialized =>
    case SparkStreamInitialized =>
    case SparkStreamRunning =>
      ssc.awaitTermination()
  }

  // template for the stream process
  def stream(ssc:StreamingContext):Unit
}

