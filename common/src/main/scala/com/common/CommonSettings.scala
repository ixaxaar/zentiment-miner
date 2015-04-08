package com.common


import com.typesafe.config.{Config, ConfigFactory}


final class CommonSettings(conf: Option[Config] = None) extends Serializable {
  val AppName = "akka-cluster-boilerplate"
  // or replace with config files parser, whatever
}
