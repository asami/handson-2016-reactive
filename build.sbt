name := "rws-sample"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked")

val scalazVersion = "7.2.0"

libraryDependencies ++= Seq(
//  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz.stream" %% "scalaz-stream" % "0.8.5",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3-1"
)

initialCommands in console := "import scalaz_, Scalaz._"
