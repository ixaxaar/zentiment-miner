package com.app

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Time, Seconds, StreamingContext}
import org.apache.spark.streaming.twitter._
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._

import com.spark._
import com.cassandra._


class TwitterDump(ssc:StreamingContext) extends SparkStreaming(ssc:StreamingContext) with Serializable {

  println("/////////////////////////////////////")
  println("// Starting Spark Streaming Server //")
  println("/////////////////////////////////////")

  val consumerKey = "pJE7QBo3ugrorjCJQwYZGd4Gu"
  val consumerSecret = "5x0IAkaBQpTK1KkOLBIEPm1jk7knZR4Z1I0nUjqCqcTT8EZWXU"
  val accessToken = "331481500-bK4vy1jEm6u5MSduyXCNmXCpjypbMDPYbujed6I1"
  val accessTokenSecret = "CmLrSHc46GrJVFpLzZINKYtEdLtNheyVUiM83r41RqJ8h"

  System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
  System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
  System.setProperty("twitter4j.oauth.accessToken", accessToken)
  System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

  val keyspace = "twitter"
  val table = "dump"

  override def stream(ssc:StreamingContext):Unit = {
    val stream = TwitterUtils.createStream(ssc, None)
      .map{t => (t.getCreatedAt, t.getText)}
      .foreachRDD(rdd => {
        rdd.saveToCassandra(keyspace, table)
      })
  }
}
