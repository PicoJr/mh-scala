name := "MonsterHunter"

version := "1.0"

scalaVersion := "2.12.4"

resolvers += Resolver.bintrayRepo("stg-tud", "maven")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) += "-no-java-comments"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "de.tuda.stg" %% "rescala" % "0.20.0"
