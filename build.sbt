name := "hadoop_p2"

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

val scalatraVersion = "2.+"

lazy val webApp = (project in file("scalatra-auth"))
    .settings(
      commonSettings,
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra-auth" % scalatraVersion,
        "org.scalatra" %% "scalatra" % scalatraVersion,
        "org.scalatra" %% "scalatra-json" % scalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % scalatraVersion % "test",
        "org.json4s" %% "json4s-jackson" % "3.+",
        "com.typesafe.slick" %% "slick" % "3.+",
        "com.h2database" % "h2" % "1.+",
        "com.typesafe.akka" %% "akka-actor" % "2.+",
        "javax.servlet" % "javax.servlet-api" % "3.+" % "provided",
        "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
      )
    ).settings(jetty(): _*)
