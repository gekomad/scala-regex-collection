name := "scala-regex-collection"

version := "2.0.1"

scalaVersion := "3.5.2"
organization := "com.github.gekomad"

libraryDependencies += "org.scalameta" %% "munit" % "1.0.2" % Test
scalacOptions ++= Seq("-Xfatal-warnings")
crossScalaVersions := Seq("2.12.20", "2.13.15", "3.5.2")


//sonatype

publishTo := sonatypePublishToBundle.value
logLevel  := Level.Debug

pomExtra :=
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
    <developers>
      <developer>
        <id>gekomad</id>
        <name>Giuseppe Cannella</name>
        <url>https://github.com/gekomad</url>
      </developer>
    </developers>
    <scm>
      <url>https://github.com/gekomad/scala-regex-collection</url>
      <connection>scm:git:https://github.com/gekomad/scala-regex-collection</connection>
    </scm>
    <url>https://github.com/gekomad/scala-regex-collection</url>
