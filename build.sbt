name := "scala-cache"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= {
  Seq(
    "org.slf4j" % "slf4j-api" % "1.7.7",
    "org.specs2" %% "specs2" % "2.4.2" % "test"
  )
}