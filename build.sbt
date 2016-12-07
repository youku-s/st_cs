import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

lazy val scalatraVersion = "2.4.0"
lazy val ScalaVersion = "2.11.8"
lazy val root = (crossProject in file("."))
  .settings(
    organization := "jp.youkus",
    name := "stcs",
    version := "0.1.0",
    scalaVersion := ScalaVersion,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    ivyScala := ivyScala.value map {
      _.copy(overrideScalaVersion = true)
    }
  )
  .jsSettings(
    (Seq(fastOptJS, fullOptJS, packageJSDependencies) map { packageJSKey =>
      crossTarget in(Compile, packageJSKey) := file("./jvm") / "src" / "main" / "webapp" / "js"
    }): _*
  )
  .jvmSettings(ScalatraPlugin.scalatraSettings)
  .jvmSettings(scalateSettings)
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % scalatraVersion,
      "org.scalatra" %% "scalatra-scalate" % scalatraVersion,
      "org.scalatra" %% "scalatra-json" % scalatraVersion,
      "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "compile;container",
      "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "compile;container",
      "org.scalikejdbc" %% "scalikejdbc" % "2.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.0",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "mysql" % "mysql-connector-java" % "5.1.38",
      "com.lihaoyi" %% "upickle" % "0.4.3",
      "com.heroku.sdk" % "heroku-jdbc" % "0.1.1",
      "postgresql" % "postgresql" % "9.4.1208-jdbc42-atlassian-hosted"
    )
  )
  .jsSettings(
    persistLauncher := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.github.japgolly.scalajs-react" %%% "core" % "0.11.3",
      "com.lihaoyi" %%% "upickle" % "0.4.3"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.3.2"
        / "react-with-addons.js"
        minified "react-with-addons.min.js"
        commonJSName "React",

      "org.webjars.bower" % "react" % "15.3.2"
        / "react-dom.js"
        minified "react-dom.min.js"
        dependsOn "react-with-addons.js"
        commonJSName "ReactDOM",

      "org.webjars.bower" % "react" % "15.3.2"
        / "react-dom-server.js"
        minified "react-dom-server.min.js"
        dependsOn "react-dom.js"
        commonJSName "ReactDOMServer"
    )
  )
lazy val shared = project
  .settings(
    organization := "jp.youkus",
    name := "stcs_share",
    version := "0.1.0",
    scalaVersion := ScalaVersion,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    ivyScala := ivyScala.value map {
      _.copy(overrideScalaVersion = true)
    },
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % "2.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.0"
    )
  )
lazy val jvm = root.jvm
  .settings(
    scalaJSProjects := Seq(js),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    mainClass in(Compile, run) := Some("JettyLauncher"),
    scalateTemplateConfig in Compile := {
      Seq(
        TemplateConfig(
          file(".") / "jvm" / "src" / "main" / "webapp" / "WEB-INF" / "views",
          Seq.empty,
          Seq.empty,
          Some("templates")
        )
      )
    }
  )
  .enablePlugins(SbtWeb, JettyPlugin, JavaAppPackaging)
  .dependsOn(shared)

lazy val js = root.js
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(shared)

onLoad in Global := (Command.process("project rootJVM", _: State)) compose (onLoad in Global).value
