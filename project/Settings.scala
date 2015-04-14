

import sbt._
import sbt.Keys._

object Settings extends Build {
  import Versions._

  lazy val buildSettings = Seq(
    name := "zentiment-miner",
    normalizedName := "zentiment-miner",
    organization := "com.common",
    organizationHomepage := Some(url("http://www.github.com/ixaxaar/zentiment-miner")),
    scalaVersion := Versions.Scala,
    homepage := Some(url("https://github.com/ixaxaar/zentiment-miner"))
  )

  val parentSettings = buildSettings ++ Seq(
    publishArtifact := false,
    publish := {}
  )

  lazy val defaultSettings =  sigarSettings ++ Seq(
    autoCompilerPlugins := true,
    scalacOptions ++= Seq("-encoding", "UTF-8", s"-target:jvm-${Versions.JDK}", "-feature", "-language:_", "-deprecation", "-unchecked", "-Xlint"),
    javacOptions in Compile ++= Seq("-encoding", "UTF-8", "-source", Versions.JDK, "-target", Versions.JDK, "-Xlint:deprecation", "-Xlint:unchecked"),
    run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)),
    ivyLoggingLevel in ThisBuild := UpdateLogging.Quiet,
    parallelExecution in ThisBuild := false,
    parallelExecution in Global := false
  )

  lazy val sigarSettings = Seq(
    unmanagedSourceDirectories in (Compile,run) += baseDirectory.value.getParentFile / "sigar",
    javaOptions in run ++= {
      System.setProperty("java.library.path", file("./sigar").getAbsolutePath)
      Seq("-Xms128m", "-Xmx1024m")
    })
}
