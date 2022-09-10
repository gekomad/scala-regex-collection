name      := "scala-regex-collection"
publishTo := sonatypePublishTo.value

import org.scalajs.linker.interface.{ESVersion, ModuleSplitStyle}

lazy val scalaJs = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    version                         := "2.0.1",
    scalaVersion                    := "2.13.15",
    crossScalaVersions              := Seq("2.12.20", "2.13.15", "3.5.2"),
    organization                    := "com.github.gekomad",
    scalaJSUseMainModuleInitializer := false,
    scalaJSLinkerConfig ~= (_.withESFeatures(_.withESVersion(ESVersion.ES2018))),
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("scalaJs")))
    },
    scalacOptions ++= Seq("-Xfatal-warnings"),
    libraryDependencies += "org.scala-js"      %%% "scalajs-dom"          % "2.8.0",
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time"      % "2.6.0",
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0",
    libraryDependencies += "org.scalameta"     %%% "munit"                % "1.0.2" % Test
  )

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
