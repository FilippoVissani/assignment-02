import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "project",
      libraryDependencies += "com.github.javaparser" % "javaparser-symbol-solver-core" % "3.25.5",
      libraryDependencies += "io.vertx" % "vertx-core" % "4.2.6",
      libraryDependencies += "com.googlecode.json-simple" % "json-simple" % "1.1.1",
      libraryDependencies += "io.reactivex.rxjava3" % "rxjava" % "3.1.4"

)
