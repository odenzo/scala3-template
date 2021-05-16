import MyCompileOptions._

val javart                      = "1.11" // java.runtime.version
lazy val supportedScalaVersions = Seq("3.0.0")

ThisBuild / scalaVersion := "3.0.0"
semanticdbEnabled        := true
bspEnabled               := false

ThisBuild / organization := "com.odenzo"

Test / fork              := true
Test / parallelExecution := false
Test / logBuffered       := false

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.defaultLocal, Resolver.bintrayRepo("tersesystems", "maven"))

scalacOptions := (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((3, n))            => optsV3_0 ++ warningsV3_0 ++ lintersV3_0
  case Some((2, n)) if n >= 13 => optsV2_13_5 ++ warningsV2_13_5 ++ lintersV2_13_5
  case _                       => Seq("-Yno-adapted-args")
})

lazy val commonSettings = Seq(
  resolvers ++= Seq(
    Resolver.defaultLocal,
    Resolver.jcenterRepo // This is JFrogs Maven Repository for reading
  )
)

val catsEffectVersion         = "3.1.1"
val catsVersion               = "2.6.1"
val circeGenericExtrasVersion = "0.13.0"
val circeOpticsVersion        = "0.13.0"
val circeVersion              = "0.14.0-M7"
val doobieV                   = "0.9.0"
val fs2V                      = "3.0.3"
val http4sVersion             = "1.0.0-M21"
val monocleVersion            = "3.0.0-M5"
val munitV                    = "0.7.26"
val munitVCatsV               = "1.0.3"
val osLibV                    = "0.7.7"
val pprintVersion             = "0.6.6"
val scalaCheckVersion         = "1.15.3"
val scalaCssV                 = "0.7.0"
val scalaTagsV                = "0.9.4"
val scalaTestVersion          = "3.2.6"
val scodecV                   = "1.11.7"
val scribeVersion             = "3.5.4"
//val squantsV                  = "1.7.4"

// TypeLevel Literally
// scalandio Chimney
// ScalaMock?
lazy val etrade_project =
  (project in file("."))
    .aggregate(common, models, http4s, etrade, desktop)

lazy val common = (project in file("modules/common"))
  .settings(commonSettings, libraryDependencies ++= libs_std ++ libs_cats ++ libs_circe ++ libs_test ++ libs_fs2 ++ libs_scodec)

lazy val http4s = (project in file("modules/http4s"))
  .dependsOn(common)
  .settings(libraryDependencies ++= libs_http4s)

lazy val models = (project in file("modules/models"))
  .dependsOn(common)
  .settings(libraryDependencies ++= libs_test)

lazy val etrade = (project in file("modules/etrade"))
  .dependsOn(common, models, http4s)
  .settings(libraryDependencies ++= libs_std ++ libs_cats ++ libs_circe ++ libs_test ++ libs_http4s)

lazy val desktop = (project in file("app/desktop"))
  .dependsOn(common, models, http4s, etrade)
  .settings(libraryDependencies ++= libs_std ++ libs_cats ++ libs_circe ++ libs_test)

//////////////////////////// LIBRARIES
val libs_std = Seq(
  "com.lihaoyi" %% "pprint" % pprintVersion,
  "com.lihaoyi" %% "os-lib" % osLibV
  //"com.outr" %% "scribe" % scribeVersion
)

// These are my standard stack and are all ScalaJS enabled.
val libs_cats = Seq("org.typelevel" %% "cats-core" % catsVersion, "org.typelevel" %% "cats-effect" % catsEffectVersion)

val libs_fs2 = Seq(
  "co.fs2" %% "fs2-core" % fs2V // For cats 2 and cats-effect 2
  //"co.fs2" %% "fs2-io"   % fs2V // circe-fs2 in the circe lib
)

//val libs_monocle = Seq(
//  "com.github.julien-truffaut" %% "monocle-core"  % monocleVersion,
//  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
//  "com.github.julien-truffaut" %% "monocle-law"   % monocleVersion % "test"
//)

/** Currently this is only for the binary serialization */

//val libs_html  = Seq("com.lihaoyi" %% "scalatags" % scalaTagsV, "com.github.japgolly.scalacss" %% "core" % scalaCssV)
val libs_circe = Seq(
  "io.circe" %% "circe-core"    % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  //"io.circe" %% "circe-generic-extras" % circeGenericExtrasVersion,
  "io.circe" %% "circe-parser"  % circeVersion
  //"io.circe" %% "circe-optics"  % circeOpticsVersion
  //"io.circe" %% "circe-literal"        % circeVersion
  // "io.circe" %% "circe-scodec"         % circeVersion,
  // "io.circe" %% "circe-fs2"            % circeVersion
)

val libs_test = Seq( // All these have scalajs and Scala 3
  "org.scalameta" %% "munit"               % munitV      % Test,
  "org.scalameta" %% "munit-scalacheck"    % munitV      % Test,
  "org.typelevel" %% "munit-cats-effect-3" % munitVCatsV % Test
)

val libs_scodec = Seq(
  //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
  ("org.scodec" %% "scodec-core" % "2.0.0")
  // ("org.scodec" %% "scodec-stream" % "3.0.0-RC2"),
  //"org.scodec"  %% "scodec-bits" % "1.1.22" // Use this in more places
  // "org.scodec"  %% "scodec-cats"   % "1.1.0-RC3"
)

//val libs_squants = Seq("org.typelevel" %% "squants" % squantsV)

val libs_http4s = Seq(
  //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion
  //"org.http4s" %% "http4s-scalatags"    % http4sVersion
  //"org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
)

val lib_doobie = Seq(
  // Start with this one     (skunk?)
  "org.tpolecat" %% "doobie-core"     % doobieV,
  "org.tpolecat" %% "doobie-hikari"   % doobieV,
  "org.tpolecat" %% "doobie-postgres" % doobieV
  //"org.tpolecat" %% "doobie-quill"     % doobieV, // Support for Quill 3.4.10
  //"org.tpolecat" %% "doobie-scalatest" % doobieV % "test"
)
