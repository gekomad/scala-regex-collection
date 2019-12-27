name := "scala-regex-collection"

version := "1.0.1"

scalaVersion := "2.13.1"
organization := "com.github.gekomad"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M2" % Test

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.6", "2.12.8", "2.13.0", "2.13.1")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:postfixOps",
  "-feature",
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-explaintypes", // Explain type errors in more detail.
  "-Xfatal-warnings"
)

publishTo := sonatypePublishTo.value

