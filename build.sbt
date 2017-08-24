name := "hive_p3"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

lazy val commonSettings = Seq(
  organization := "com.example",
  version := "1.0",
  scalaVersion := "2.12.3"
)

lazy val yarnTask = (project in file("yarn-task"))
    .settings(
      commonSettings,
      assemblyMergeStrategy in assembly := {
        case PathList("META-INF", xs @ _*) => MergeStrategy.discard
        case x => MergeStrategy.first
      },
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-library" % "2.12.3",
        "org.apache.hadoop" % "hadoop-common" % "2.8.1",
        "org.apache.hadoop" % "hadoop-yarn-client" % "2.8.1"
      )
    )

lazy val webApp = (project in file("akka-http"))
    .settings(
      commonSettings,
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-library" % "2.12.3",
        "com.typesafe.akka" % "akka-http_2.12" % "10.0.9",
        "org.scalatest" %% "scalatest" % "3.0.1" % "test"
      )
    )
