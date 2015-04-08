package com.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import akka.actor.{Actor, ActorLogging, Props, ActorRef, Cancellable}


abstract class SparkStreaming(name:String) extends Actor with ActorLogging {
  import Events._

  var delay:Int = 2

  // the spark context
  val sparkConf = new SparkConf().setAppName(name)
  val ssc = new StreamingContext(sparkConf, Seconds(2))

  def actorReceive:Actor.Receive = maintainState

  def maintainState:Actor.Receive = {
    case SparkStreamShuttingDown =>
      self ! SparkStreamDown
    case SparkStreamStart =>
      self ! SparkStreamRunning
    case SparkStreamStop =>
      self ! SparkStreamShuttingDown
    case SparkStreamDown =>
    case SparkStreamUninitialized =>
    case SparkStreamInitialized =>
    case SparkStreamRunning =>
      stream(ssc)
  }

  // template for the stream process
  def stream(spk:StreamingContext):Unit
}
