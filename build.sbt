name := "scala-regex-collection"

version := "2.0.0"

scalaVersion := "3.0.0-RC2"
organization := "com.github.gekomad"

libraryDependencies += "org.typelevel" %% "discipline-scalatest" % "2.1.3" % Test
crossScalaVersions := Seq("2.12.8", "2.13.5", "3.0.0-RC2")

scalacOptions ++= Seq("-deprecation", "-language:postfixOps", "-feature", "-unchecked", "-Xfatal-warnings")

publishTo := sonatypePublishTo.value
