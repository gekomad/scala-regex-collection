name := "scala-regex-collection"

version := "0.1.0-M1"

scalaVersion := "2.13.0-M5"
organization := "com.github.gekomad"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.6-SNAP6" % Test


crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.12.8","2.13.0-M5")

publishTo := sonatypePublishTo.value

