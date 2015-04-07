package com.cassandra

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._


object CassandraSparkContext {
  val settings = new Settings
  import settings._

  val sparkConf = new SparkConf().setAppName(AppName).setMaster("local[4]")
  sparkConf.set("spark.cassandra.connection.host", host)
  sparkConf.set("spark.cassandra.connection.native.port", native_port)
  sparkConf.set("spark.cassandra.connection.conf.factory", conf_factory)
  sparkConf.set("spark.cassandra.connection.timeout_ms", timeout_ms)
  sparkConf.set("spark.cassandra.connection.reconnection_delay_ms.min", reconnection_delay_ms_min)
  sparkConf.set("spark.cassandra.connection.reconnection_delay_ms.max", reconnection_delay_ms_max)
  sparkConf.set("spark.cassandra.connection.local_dc", local_dc)

  sparkConf.set("spark.cassandra.auth.username", username)
  sparkConf.set("spark.cassandra.auth.password", password)

  sparkConf.set("spark.cassandra.query.retry.count", retry_count)
  sparkConf.set("spark.cassandra.read.timeout_ms", read_timeout_ms)
  sparkConf.set("spark.cassandra.input.consistency.level", consistency_level)
  sparkConf.set("spark.cassandra.input.page.row.size", page_row_size)
  sparkConf.set("spark.cassandra.input.split.size", input_split_size)

  sparkConf.set("spark.cassandra.output.batch.size.rows", batch_size_rows)
  sparkConf.set("spark.cassandra.output.batch.size.bytes", batch_size_bytes)
  sparkConf.set("spark.cassandra.output.batch.grouping.key", batch_grouping_key)
  sparkConf.set("spark.cassandra.output.batch.buffer.size", batch_buffer_size)

  sparkConf.set("spark.cassandra.output.concurrent.writes", concurrent_writes)
  sparkConf.set("spark.cassandra.output.consistency.level", output_consistency_level)

  // val sc = new SparkContext(sparkConf)
  // val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  // import sqlContext._
  val ssc = new StreamingContext(sparkConf, Milliseconds(granularity))
}
