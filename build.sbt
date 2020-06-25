name := "scala-regex-collection"

version := "1.0.1"

scalaVersion := "2.13.2"
organization := "com.github.gekomad"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.3.0-SNAP2" % Test

crossScalaVersions := Seq("2.11.12", "2.12.8", "2.13.2")

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

// js
enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := false

//lazy val bar =
//// select supported platforms
//  crossProject(JSPlatform, JVMPlatform)
//    .crossType(CrossType.Pure) // [Pure, Full, Dummy], default: CrossType.Full
//    .settings()
//    .jsSettings(/* ... */) // defined in sbt-scalajs-crossproject
//    .jvmSettings(/* ... */)
//
//
//// Optional in sbt 1.x (mandatory in sbt 0.13.x)
//lazy val barJS     = bar.js
//lazy val barJVM    = bar.jvm
//
