import sbt._
import MyCompileOptions._
import Dependencies._

inThisBuild(
  Seq(
    organization      := "com.odenzo",
    organizationName  := "odenzo",
    startYear         := Some(2021),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage          := Some(url("https://github.com/odenzo/scala3template")),
    developers        := List(Developer("odenzo", "odenzo", "mail@blackhole.com", url("https://github.com/odenzo"))),
    scalaVersion      := "3.0.0",
    scalacOptions ++= optsV3_0 ++ warningsV3_0 ++ lintersV3_0,
    semanticdbEnabled := true,
    bspEnabled        := false,
    testFrameworks += new TestFramework("munit.Framework")
  )
)

Test / fork              := true
Test / parallelExecution := false
Test / logBuffered       := false

// TypeLevel Literally
// scalandio Chimney

lazy val root =
  (project in file("."))
    .aggregate(core, secrets)

lazy val core = (project in file("modules/odenzo-core"))
  .settings(
    libraryDependencies ++= Dependencies.stdLibs ++ Dependencies.cats ++ Dependencies.circe ++
      Dependencies.testing ++ Dependencies.fs2 ++ Dependencies.doobie
  )

lazy val secrets = (project in file("modules/odenzo-secrets"))
  .dependsOn(core)
  .settings(libraryDependencies ++= Dependencies.all)

lazy val web = (project in file("modules/odenzo-web"))
  .dependsOn(core, secrets)
  .settings(libraryDependencies ++= Dependencies.testing)

lazy val webapp = (project in file("app/webapp"))
  .dependsOn(core, secrets, web)
  .settings(
    libraryDependencies ++= Dependencies.stdLibs ++
      Dependencies.cats ++
      Dependencies.circe ++
      Dependencies.testing ++
      Dependencies.http4s
  )
