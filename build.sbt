import MyCompileOptions._

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

lazy val library = new {
  object Version {
    val log4j      = "2.14.0"
    val logback    = "1.2.3"
    val mockito    = "3.10.0"
    val munit      = "0.7.26"
    val slf4j      = "1.7.30"
    val slf4s      = "0.1.1"
    val catsEffect = "3.1.1"
    val cats       = "2.6.1"
    val circe      = "0.14.0-M7"
    val doobie     = "1.0.0-M2"
    val fs2        = "3.0.3"
    val http4s     = "1.0.0-M21"
    val monocle    = "3.0.0-M5"
    val munitCats  = "1.0.3"
    val osLib      = "0.7.7"
    val pprint     = "0.6.6"
    val scala      = "1.15.3"
    val scalaCss   = "0.7.0"
    val scalaTags  = "0.9.4"
    val scodec     = "2.0.0"
    //val scribeVersion = "3.5.4"
    //val squantsV                  = "1.7.4"
  }

  val stdLibs = Seq(
    "com.lihaoyi"          %% "pprint"          % Version.pprint,
    "com.lihaoyi"          %% "os-lib"          % Version.osLib,
    "ch.qos.logback"        % "logback-classic" % Version.logback,
    "rocks.heikoseeberger" %% "slf4s"           % Version.slf4s
    //"com.outr" %% "scribe" % scribeVersion
  )

  // These are my standard stack and are all ScalaJS enabled.
  val cats = Seq("org.typelevel" %% "cats-core" % Version.cats, "org.typelevel" %% "cats-effect" % Version.catsEffect)

  val fs2 = Seq(
    "co.fs2" %% "fs2-core" % Version.fs2 // For cats 2 and cats-effect 2
    //"co.fs2" %% "fs2-io"   % Version.fs2V // circe-fs2 in the circe lib
  )

  //val libs_monocle = Seq(
  //  "com.github.julien-truffaut" %% "monocle-core"  % monocleVersion,
  //  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
  //  "com.github.julien-truffaut" %% "monocle-law"   % monocleVersion % "test"
  //)

  /** Currently this is only for the binary serialization */

  //val libs_html  = Seq("com.lihaoyi" %% "scalatags" % scalaTagsV, "com.github.japgolly.scalacss" %% "core" % scalaCssV)
  val circe = Seq(
    "io.circe" %% "circe-core"    % Version.circe,
    "io.circe" %% "circe-generic" % Version.circe,
    "io.circe" %% "circe-extras"  % Version.circe,
    "io.circe" %% "circe-parser"  % Version.circe,
    "io.circe" %% "circe-pointer" % Version.circe,
    //"io.circe" %% "circe-optics"  % circeOpticsVersion
    //"io.circe" %% "circe-literal"        % circeVersion
    "io.circe" %% "circe-scodec"  % Version.circe
    // "io.circe" %% "circe-fs2"            % circeVersion
  )

  val testing = Seq( // All these have scalajs and Scala 3
    "org.mockito"    % "mockito-core"        % Version.mockito   % Test,
    "org.scalameta" %% "munit"               % Version.munit     % Test,
    "org.scalameta" %% "munit-scalacheck"    % Version.munit     % Test,
    "org.typelevel" %% "munit-cats-effect-3" % Version.munitCats % Test
  )

  val scodec = Seq(
    //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
    ("org.scodec" %% "scodec-core" % Version.scodec)
    // ("org.scodec" %% "scodec-stream" % "3.0.0-RC2"),
    //"org.scodec"  %% "scodec-bits" % "1.1.22" // Use this in more places
    // "org.scodec"  %% "scodec-cats"   % "1.1.0-RC3"
  )

  //val libs_squants = Seq("org.typelevel" %% "squants" % squantsV)

  //val libs_http4s = Seq(
  //  //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
  //  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  //  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  //  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  //  "org.http4s" %% "http4s-circe"        % http4sVersion
  //  //"org.http4s" %% "http4s-scalatags"    % http4sVersion
  //  //"org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
  //)

  val doobie = Seq(
    // Start with this one     (skunk?)
    "org.tpolecat" %% "doobie-core"     % Version.doobie,
    "org.tpolecat" %% "doobie-hikari"   % Version.doobie,
    "org.tpolecat" %% "doobie-postgres" % Version.doobie
    //"org.tpolecat" %% "doobie-quill"     % doobieV, // Support for Quill 3.4.10
    //"org.tpolecat" %% "doobie-scalatest" % doobieV % "test"
  )

}

// TypeLevel Literally
// scalandio Chimney

lazy val root =
  (project in file("."))
    .aggregate(core, secrets)

lazy val core = (project in file("modules/odenzo-core"))
  .settings(
    libraryDependencies ++= library.stdLibs ++ library.cats ++ library.circe ++
      library.testing ++ library.fs2 ++ library.doobie
  )

lazy val secrets = (project in file("modules/odenzo-secrets"))
  .dependsOn(core)
//.settings(libraryDependencies ++= libs_http4s)

//lazy val web = (project in file("modules/odenzo-web"))
//  .dependsOn(core, secrets)
//  .settings(libraryDependencies ++= libs_test)

//lazy val webapp = (project in file("app/webapp"))
//  .dependsOn(core, secrets, web)
//  .settings(libraryDependencies ++= libs_std ++ libs_cats ++ libs_circe ++ libs_test ++ libs_http4s)

//////////////////////////// LIBRARIES
