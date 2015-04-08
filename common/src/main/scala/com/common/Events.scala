package com.common

object Events {

  sealed trait StateEvent extends Serializable

  case object Down extends StateEvent
  case object ShuttingDown extends StateEvent
  case object Start extends StateEvent
  case object Uninitialized extends StateEvent
  case object Initialized extends StateEvent
  case object Running extends StateEvent

  sealed trait AppEvents extends Serializable
  case class PingMessage(text: String) extends AppEvents
  case class PongMessage(text: String) extends AppEvents
}
