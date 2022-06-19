import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"


lazy val root = (project in file("."))
  .settings(
    name := "Scalix2",
    libraryDependencies += "org.json4s" %% "json4s-ast" % "4.0.5",
    libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.5"
  )
