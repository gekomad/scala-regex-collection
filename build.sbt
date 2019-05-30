name := "scala-regex-collection"

version := "1.0.0-RC1"

scalaVersion := "2.13.0-M5"
organization := "com.github.gekomad"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0-SNAP11" % Test


crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.12.8","2.13.0-RC2")

publishTo := sonatypePublishTo.value

