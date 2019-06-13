name := "scala-regex-collection"

version := "1.0.0"

scalaVersion := "2.13.0"
organization := "com.github.gekomad"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.12.8","2.13.0")

publishTo := sonatypePublishTo.value

