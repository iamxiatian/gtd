organization := "ruc"
name := "xiatian"
version := "0.1"

scalaVersion := "2.12.3"
sbtVersion := "1.2.1"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "ruc.xiatian.gtg.common"
  )

buildInfoKeys ++= Seq[BuildInfoKey](
  "author" -> "XiaTian"
)

buildInfoKeys += buildInfoBuildNumber
buildInfoOptions += BuildInfoOption.BuildTime
buildInfoOptions += BuildInfoOption.ToJson

libraryDependencies += "com.typesafe" % "config" % "1.3.3"

//XML support
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

//command line parser
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"

//Scala wrapper for Joda Time.
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.16.0"

//Scala better file
libraryDependencies += "com.github.pathikrit" %% "better-files-akka" % "3.0.0"

//database drivers
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"

libraryDependencies += "org.rocksdb" % "rocksdbjni" % "5.7.2"
libraryDependencies += "com.sleepycat" % "je" % "18.3.1"

libraryDependencies += "org.janusgraph" % "janusgraph-core" % "0.3.1"
libraryDependencies += "org.janusgraph" % "janusgraph-berkeleyje" % "0.3.1"
libraryDependencies += "org.janusgraph" % "janusgraph-lucene" % "0.3.1"

//gephi
//libraryDependencies += "org.gephi" % "graph-api" % "0.9.2"

//jena
//libraryDependencies += "org.apache.jena" % "jena-core" % "3.6.0"
//libraryDependencies += "org.apache.jena" % "jena-tdb" % "3.6.0"
//libraryDependencies += "org.apache.jena" % "jena-arq" % "3.6.0"


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3"
)

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "ch.qos.logback" % "logback-core" % "1.2.3"

libraryDependencies += "com.google.guava" % "guava" % "24.0-jre"

libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"

//scala语法的简单的HTTP包
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.1"

//NLP libraries
//libraryDependencies += "com.hankcs" % "hanlp" % "portable-1.5.2"
libraryDependencies += "org.ansj" % "ansj_seg" % "5.1.1"
libraryDependencies += "org.ahocorasick" % "ahocorasick" % "0.3.0"

libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.9.0"

//CIRCE JSON Parser
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % "0.10.1")

//wikipedia parser
//https://bitbucket.org/axelclk/info.bliki.wiki/wiki/Home
libraryDependencies += "info.bliki.wiki" % "bliki-core" % "3.1.0"

//Scala Test library
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

scalacOptions in Test ++= Seq("-Yrangepos")


import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
//enablePlugins(JavaServerAppPackaging)
enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("xiatian.spider.Main")

mappings in(Compile, packageDoc) := Seq()

//把运行时需要的配置文件拷贝到打包后的主目录下
//mappings in Universal += file("my.conf") -> "my.conf"
mappings in Universal ++= directory("web")
mappings in Universal ++= directory("conf")
mappings in Universal ++= directory("models")

javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  "-J-Xms4G",
  "-J-Xmx8G"
)

//解决windows的line too long问题
scriptClasspath := Seq("*")

initialCommands in console +=
  """
    |import java.io._
    |import java.util.Date
    |
    |import scala.collection.JavaConverters._
    |import scala.collection.mutable
    |import scala.util.{Failure, Success, Try}
    |
    |
  """.stripMargin
