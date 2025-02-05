name      := "scala-regex-collection"
 
import org.scalajs.linker.interface.{ESVersion, ModuleSplitStyle}

lazy val scalaJs = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    version                         := "2.0.2",
    scalaVersion                    := "2.13.18",
    crossScalaVersions              := Seq("2.12.21", "2.13.18", "3.3.7"),
    organization                    := "com.github.gekomad",
    scalaJSUseMainModuleInitializer := false,
    scalaJSLinkerConfig ~= (_.withESFeatures(_.withESVersion(ESVersion.ES2018))),
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("scalaJs")))
    },
    scalacOptions ++= Seq("-Xfatal-warnings"),
    libraryDependencies += "org.scala-js"      %%% "scalajs-dom"          % "2.8.1",
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time"      % "2.6.0",
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0",
    libraryDependencies += "org.scalameta"     %%% "munit"                % "1.2.0" % Test
  )

//sonatype
import xerial.sbt.Sonatype._ 
sonatypeCredentialHost := "central.sonatype.com" 
sonatypeRepository := "https://central.sonatype.com/api/v1/publisher"

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
