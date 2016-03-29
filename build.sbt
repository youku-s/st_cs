lazy val scalatraVersion = "2.3.1"

lazy val root = (crossProject in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    organization := "jp.youkus",
    name := "stcs",
    version := "0.1.0",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
  )
  .settings(
    (Seq(fastOptJS, fullOptJS, packageJSDependencies) map { packageJSKey =>
      crossTarget in(Compile, packageJSKey) := file("./jvm") / "src" / "main" / "webapp" / "js"
    }): _*
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalatra"      %% "scalatra"          % scalatraVersion,
      "org.scalatra"      %% "scalatra-scalate"  % scalatraVersion,
      "org.scalatra"      %% "scalatra-specs2"   % scalatraVersion    % "test",
      "ch.qos.logback"    %  "logback-classic"   % "1.1.3"            % "runtime",
      "org.eclipse.jetty" %  "jetty-webapp"      % "9.2.10.v20150310" % "container",
      "javax.servlet"     %  "javax.servlet-api" % "3.1.0"            % "provided"
    )
  )
  .jvmSettings(jetty(): _*)
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.8.2",
      "com.github.japgolly.scalajs-react" %%% "core" % "0.10.4"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "0.14.3"
        / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
      "org.webjars.bower" % "react" % "0.14.3"
        / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"
    )
  )
lazy val jvm = root.jvm
lazy val js = root.js
