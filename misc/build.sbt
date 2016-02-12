// sbtの便利な使い方
// http://qiita.com/kawachi/items/8dfeb73f9110a117f9eb
name := "misc"
 
version := "0.1"
 
organization := "org.sample"
 
scalaVersion := "2.11.2"
 
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.0"
libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.0",
  "com.github.sstone" % "amqp-client_2.11" % "1.4",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.11",
  "junit" % "junit" % "4.10" % "test"
)
 
javacOptions ++= Seq("-source", "1.7", "-target", "1.7", "-encoding", "UTF-8")
 
scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8")
 
fork := true
 
initialCommands in console := """println("***** Console Start! *****")"""
//initialCommands in console := "import org.sample._"
 
mainClass in Compile := Some("org.sample.HelloWorldScala")
//mainClass in (Compile, packageBin) := Some("org.sample.Main")
//mainClass in (Compile, run) := Some("org.sample.Main")
 
crossPaths := false
