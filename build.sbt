enablePlugins(JavaServerAppPackaging)
enablePlugins(LinuxPlugin)
enablePlugins(DebianPlugin)
enablePlugins(JDebPackaging)
enablePlugins(SystemVPlugin)
  
name := "Chat Demo"
scalaVersion := "2.11.8"
version := "0.1.0"
maintainer := "Aleksey Fomkin <aleksey.fomkin@gmail.com>"
packageSummary := "Simple chat web application"
packageDescription := "Korolev based demo application for my speak at FPConf Moscow 2016"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.9",
  "com.github.fomkin" %% "korolev-server" % "0.0.6",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

