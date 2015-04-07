package com.cassandra

import com.datastax.spark.connector._
import com.typesafe.config.{Config, ConfigFactory}


final class Settings(conf: Option[Config] = None) extends Serializable {

  val AppName = "akka-cluster-boilerplate"

  // hadoop config
  val sparkMaster = "local[4]"

  // spark streaming context granularity in milliseconds
  val granularity = 500

  // Settings for cassandra and cassandra-spark-connector
  val host = "127.0.0.1"
  val native_port = "9042"
  val conf_factory = "com.datastax.spark.connector.cql.DefaultConnectionFactory"
  val timeout_ms = "5000"
  val reconnection_delay_ms_min = "1000"
  val reconnection_delay_ms_max = "60000"
  val local_dc = "datacenter1"
  val username = ""
  val password = ""
  val retry_count = "10"
  val read_timeout_ms = "60000"
  val consistency_level = "LOCAL_ONE"
  val page_row_size = "10000"
  val input_split_size = "10000"
  val batch_size_rows = "auto"
  val batch_size_bytes = (16*1024).toString
  val batch_grouping_key = "partition"
  val concurrent_writes = "5"
  val output_consistency_level = "LOCAL_ONE"
  val batch_buffer_size = "1000"
}
