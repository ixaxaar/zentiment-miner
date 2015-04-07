
import sbt._
import sbt.Keys._


object Dependencies {
  import Versions._

  object Compile {

    val akkaStream        = "com.typesafe.akka"   %% "akka-stream-experimental"           % AkkaStreams
    val akkaHttpCore      = "com.typesafe.akka"   %% "akka-http-core-experimental"        % AkkaStreams
    val akkaActor         = "com.typesafe.akka"   %% "akka-actor"                         % Akka
    val akkaCluster       = "com.typesafe.akka"   %% "akka-cluster"                       % Akka
    val akkaRemote        = "com.typesafe.akka"   %% "akka-remote"                        % Akka
    val akkaSlf4j         = "com.typesafe.akka"   %% "akka-slf4j"                         % Akka
    val logback           = "ch.qos.logback"      % "logback-classic"                     % Logback
    val slf4jApi          = "org.slf4j"           % "slf4j-api"                           % Slf4j
    val sparkML           = "org.apache.spark"    %% "spark-mllib"                        % Spark
    val deepLearning4j    = "org.deeplearning4j"  % "deeplearning4j-core"                 % DeepLearning4j
    val twitter4j         = "org.twitter4j"       % "twitter4j"                           % Twitter4j
    val cassandraDriver   = "com.datastax.cassandra" % "cassandra-driver-core"            % CassandraDriver
    val sparkCassandra    = "com.datastax.spark"  %% "spark-cassandra-connector"          % SparkCassandra
    val sparkCassandraEmb = "com.datastax.spark"  %% "spark-cassandra-connector-embedded" % SparkCassandra
    val twitterSpark      = "org.apache.spark"    % "spark-streaming-twitter_2.10"        % TwitterSpark
  }

  object Test {
    val akkaTestKit     = "com.typesafe.akka"     %% "akka-testkit"                       % Akka      % "test,it"
    val scalatest       = "org.scalatest"         %% "scalatest"                          % ScalaTest % "test,it"
    val specs2            = "org.specs2"          %% "specs2"                             % Specs2       % "test"
  }

  import Compile._

  val dl4j = Seq(deepLearning4j)

  val akka = Seq(akkaStream, akkaHttpCore, akkaActor, akkaCluster, akkaRemote, akkaSlf4j)

  val logging = Seq(logback, slf4jApi)

  val spark = Seq(sparkML, twitterSpark) ++ logging ++ akka

  val cassandra = Seq(cassandraDriver, sparkCassandra) ++ spark

  val test = Seq(Test.akkaTestKit, Test.scalatest)

  val common = akka ++ logging
}
