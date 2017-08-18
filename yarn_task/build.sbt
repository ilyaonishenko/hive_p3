name := "yarn_task"

version := "1.0"

scalaVersion := "2.12.3"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

//mainClass in assembly := Some("com.example.HdfsWorker")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.12.3",
  "org.apache.hadoop" % "hadoop-common" % "2.8.1",
  "org.apache.hadoop" % "hadoop-yarn-client" % "2.8.1"
)