package com.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Milliseconds, StreamingContext}


// There can be only one context per JVM
object SparkContexts {
  val settings = new Settings
  import settings._

  val sparkConf = new SparkConf().setAppName(AppName).setMaster(sparkMaster)
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext._

  val ssc = new StreamingContext(sparkConf, Milliseconds(granularity))
}
