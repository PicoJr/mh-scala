name := "MonsterHunter"

version := "1.0"

scalaVersion := "2.12.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) += "-no-java-comments"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "org.scala-graph" %% "graph-core" % "1.12.1"
libraryDependencies += "org.scala-graph" %% "graph-dot" % "1.12.1"
