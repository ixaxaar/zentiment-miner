package com.spark

import com.typesafe.config.{Config, ConfigFactory}


final class Settings(conf: Option[Config] = None) extends Serializable {
  val AppName = "akka-cluster-boilerplate"

  // hadoop config
  val sparkMaster = "local"

  // spark streaming context granularity in milliseconds
  val granularity = 1000
}
