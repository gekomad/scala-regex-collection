name := "scala-regex-collection"

version := "0.0.1"

scalaVersion := "2.12.8"
organization := "com.github.gekomad"
libraryDependencies += "org.scalatest"      %% "scalatest"  % "3.0.5"  % Test

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.12.8")

publishTo := sonatypePublishTo.value

