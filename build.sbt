import Dependencies._
import MyCompileOptions._

inThisBuild(
  Seq(
    organization      := "com.odenzo",
    organizationName  := "odenzo",
    startYear         := Some(2022),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage          := Some(url("https://github.com/odenzo/scala3template")),
    developers        := List(Developer("odenzo", "odenzo", "mail@blackhole.com", url("https://github.com/odenzo"))),
    scalaVersion      := "3.1.0",
    scalacOptions ++= optsV3_0 ++ warningsV3_0 ++ lintersV3_0,
    semanticdbEnabled := true,
    bspEnabled        := false
  )
)

Test / fork              := true
Test / parallelExecution := false
Test / logBuffered       := false

lazy val root = (project in file("."))
  .settings(name := "YangParserProject")
  .aggregate(yangparse)

lazy val yangparse = (project in file("app/yangParser"))
  .settings(
    libraryDependencies ++= Dependencies.cats ++ Dependencies.catsParse ++ Dependencies.stdLibs
      ++ Dependencies.monocle ++ Dependencies.circe ++ Dependencies.fs2,
    libraryDependencies ++= Dependencies.testingMUnit
  )

// docs/mdoc --help
lazy val docs = project // new documentation project
  .in(file("yang-parser-docs")) // important: it must not be docs/
  .settings(mdocVariables := Map("VERSION" -> version.value), moduleName := "yang-parser-docs")
  .dependsOn(root)
  .enablePlugins(MdocPlugin, DocusaurusPlugin)
