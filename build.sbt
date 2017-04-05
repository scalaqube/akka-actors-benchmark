name := "untitled"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.2"

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.17"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.17"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.17"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.4.17"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.23"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.4.17"