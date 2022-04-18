import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "ast-analyzer",
      libraryDependencies += "com.github.javaparser" % "javaparser-symbol-solver-core" % "3.24.2",
      libraryDependencies += "io.vertx" % "vertx-core" % "4.2.6"
  )