name := "MonsterHunter"

version := "1.0"

scalaVersion := "2.12.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
libraryDependencies += "org.rogach" %% "scallop" % "3.1.1"