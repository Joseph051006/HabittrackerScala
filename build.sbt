ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.4"

lazy val root = (project in file("."))
  .settings(
    name := "HabitTracker",
    idePackagePrefix := Some("org.habittracker.application")
  )
libraryDependencies ++= Seq(
  "org.typelevel" %% "case-insensitive" % "1.5.0"
)
