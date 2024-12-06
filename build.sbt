name := "RTest"

organization := "com.ridetrends"

version := "0.1"

scalaVersion := "2.13.8" // Use the latest stable version of Scala 2.13, if possible

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.2",
  "org.apache.spark" %% "spark-core" % "3.3.0",
  "org.apache.spark" %% "spark-sql" % "3.3.0",
  "org.scalatest" %% "scalatest" % "3.2.10" % Test, // Updated to match Scala 2.13
  "com.github.mrpowers" %% "spark-fast-tests" % "1.2.0" % Test // Use a compatible version with Scala 2.13 and Spark 3.3
)
