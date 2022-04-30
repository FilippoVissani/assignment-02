import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "ast-analyzer",
      libraryDependencies += "com.github.javaparser" % "javaparser-symbol-solver-core" % "3.24.2",
      libraryDependencies += "io.vertx" % "vertx-core" % "4.2.6",
      // Add dependency on ScalaFX library
      libraryDependencies += "org.scalafx" %% "scalafx" % "17.0.1-R26"
  )

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
// Add JavaFX dependencies
libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
    }
    Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % "17.0.1" classifier osName)
}
