package com.spark

import akka.actor.ActorRef

object Events {

  // Batch events
  sealed trait SparkBatchEvent extends Serializable

  case object SparkBatchDown extends SparkBatchEvent
  case object SparkBatchShuttingDown extends SparkBatchEvent
  case class SparkBatchStart(a:ActorRef) extends SparkBatchEvent
  case object SparkBatchStop extends SparkBatchEvent
  case object SparkBatchUninitialized extends SparkBatchEvent
  case object SparkBatchInitialized extends SparkBatchEvent
  case object SparkBatchRunning extends SparkBatchEvent


  // Stream events
  sealed trait SparkStreamEvent extends Serializable

  case object SparkStreamDown extends SparkStreamEvent
  case object SparkStreamShuttingDown extends SparkStreamEvent
  case object SparkStreamStart extends SparkStreamEvent
  case object SparkStreamStop extends SparkStreamEvent
  case object SparkStreamUninitialized extends SparkStreamEvent
  case object SparkStreamInitialized extends SparkStreamEvent
  case object SparkStreamRunning extends SparkStreamEvent
}
