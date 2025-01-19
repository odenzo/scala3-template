import MyCompileOptions._

inThisBuild(
  Seq(
    organization      := "com.adtran",
    organizationName  := "adtran",
    startYear         := Some(2022),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage          := Some(url("https://github.com/odenzo/scala3template")),
    developers        := List(Developer("odenzo", "odenzo", "mail@blackhole.com", url("https://github.com/odenzo"))),
    scalaVersion      := "3.2.0",
    scalacOptions ++= optsV3_0 ++ warningsV3_0 ++ lintersV3_0,
    semanticdbEnabled := true,
    bspEnabled        := true,
    mainClass         := Some("com.adtran.utilapp.logparser.aaa.CommandLine")
  )
)

Test / fork              := true
Test / parallelExecution := false
Test / logBuffered       := false

lazy val root = (project in file("."))
  .settings(name := "ParsingUtils")
  .aggregate(yangparse, apilogs)

lazy val yangparse = (project in file("app/yangParser"))
  .settings(
    name := "YangSchemaParsing",
    libraryDependencies ++= Libs.cats ++ Libs.catsParse ++ Libs.stdLibs ++ Libs.monocle ++ Libs.circe ++ Libs.fs2,
    libraryDependencies ++= Libs.testingMUnit
  )

lazy val apilogs = (project in file("app/logparser"))
  .settings(
    name := "LogFileParsing",
    libraryDependencies ++= Libs.cats ++ Libs.catsParse ++ Libs.stdLibs ++ Libs.blindsight ++ Libs.decline ++ Libs.circe,
    libraryDependencies ++= Libs.comcastNetorks ++ Libs.http4s,
    libraryDependencies ++= Libs.testingMUnit
  )
