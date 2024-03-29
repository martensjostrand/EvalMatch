name := "EvalMatch"

version := "0.1-SNAPSHOT"

organization := "me.marten"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.6.1",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
    "me.marten" %% "scalpi" % "0.1-SNAPSHOT",
    "me.marten" %% "scalpglpk" % "0.1-SNAPSHOT"
)

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
                    "releases"  at "http://scala-tools.org/repo-releases")

scalacOptions ++= Seq("-deprecation", "-unchecked")

mainClass := Some("evalmatch.EvalMatchRunner")

