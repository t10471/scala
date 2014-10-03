name := "sbt-sample"
 
version := "0.1"
 
organization := "org.sample"
 
scalaVersion := "2.11.2"
 
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.0"
libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.0",
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
