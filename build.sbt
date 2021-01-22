name := "DemoProject"

version := "0.1"

scalaVersion := "2.13.4"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.10",
  "com.typesafe.akka" %% "akka-stream" % "2.6.10",
  "com.typesafe.akka" %% "akka-http" % "10.2.2",

  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.2",

  "de.heikoseeberger" %% "akka-http-json4s" % "1.27.0",

  "org.json4s" %% "json4s-ext" % "3.6.6",
  "org.json4s" %% "json4s-jackson" % "3.6.6",
  "org.json4s" %% "json4s-native" % "3.6.6",

  "com.typesafe.akka" %% "akka-http-testkit" % "10.2.2" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.6.10" % Test,
  "org.scalatest" %% "scalatest" % "3.2.3" % Test,

  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0")