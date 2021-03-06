import sbt._
object Dependencies {

  lazy val stdLibs = Seq(
    "com.lihaoyi"          %% "pprint"          % Version.pprint,
    "com.lihaoyi"          %% "os-lib"          % Version.osLib,
    "ch.qos.logback"        % "logback-classic" % Version.logback,
    "rocks.heikoseeberger" %% "slf4s"           % Version.slf4s
    // "com.outr" %% "scribe" % scribeVersion
  )

  // These are my standard stack and are all ScalaJS enabled.
  lazy val cats = Seq("org.typelevel" %% "cats-core" % Version.cats, "org.typelevel" %% "cats-effect" % Version.catsEffect)

  lazy val fs2 = Seq(
    "co.fs2" %% "fs2-core"             % Version.fs2, // CE 3
    "co.fs2" %% "fs2-io"               % Version.fs2, //
    "co.fs2" %% "fs2-reactive-streams" % Version.fs2  // circe-fs2 in the circe lib
  )

  val monocle = Seq(
    "dev.optics" %% "monocle-core"  % Version.monocle,
    "dev.optics" %% "monocle-macro" % Version.monocle,
    "dev.optics" %% "monocle-law"   % Version.monocle % Test
  )

  /** Currently this is only for the binary serialization */
  // val libs_html = Seq("com.lihaoyi" %% "scalatags" % Version.scalaTags, "com.github.japgolly.scalacss" %% "core" % Version.scalaCss)

  // As of 0.14.1 Circe Suite Partually migrated to scala 3
  lazy val circe = Seq(
    "io.circe" %% "circe-core"    % Version.circe,
    // "io.circe" %% "circe-jackson212" % Version.circe,
    "io.circe" %% "circe-generic" % Version.circe,
    "io.circe" %% "circe-extras"  % Version.circe,
    "io.circe" %% "circe-parser"  % Version.circe,
    "io.circe" %% "circe-pointer" % Version.circe,
    // "io.circe" %% "circe-pointer-literal"    % Version.circe, // Not available 0.14.1
    // "io.circe" %% "circe-optics"  % circeOpticsVersion
    // "io.circe" %% "circe-literal" % Version.circe, // Not available as of 0.14.1
    "io.circe" %% "circe-scodec"  % Version.circe
    // "io.circe" %% "circe-fs2"            % circeVersion
  )

  lazy val testingMUnit = Seq( // All these have scalajs and Scala 3
    // "org.mockito"    % "mockito-core"        % Version.mockito   % Test,
    // "org.scalamock" %% "scalamock" % "5.1.0" % Test
    "org.scalameta" %% "munit"               % Version.munit     % Test,
    "org.scalameta" %% "munit-scalacheck"    % Version.munit     % Test,
    "org.typelevel" %% "munit-cats-effect-3" % Version.munitCats % Test
  )

  lazy val scodec = Seq(
    //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
    "org.scodec" %% "scodec-core" % Version.scodec,
    "org.scodec" %% "scodec-bits" % Version.scodecBits
    // ("org.scodec" %% "scodec-stream" % "3.0.0-RC2"),
    // "org.scodec"  %% "scodec-cats"   % "1.1.0-RC3"
  )

  // val libs_squants = Seq("org.typelevel" %% "squants" % squantsV)

  // Have to choose CE2 or CE3 on Scala 3
  lazy val http4s = Seq(
    //  //  "io.circe"       %% "circe-spire"          % "0.1.0",   Meh, stuck at 2.12
    "org.http4s" %% "http4s-core"         % Version.http4s,
    "org.http4s" %% "http4s-dsl"          % Version.http4s,
    "org.http4s" %% "http4s-blaze-server" % Version.http4s,
    "org.http4s" %% "http4s-blaze-client" % Version.http4s,
    "org.http4s" %% "http4s-circe"        % Version.http4s
    // "org.http4s" %% "http4s-scalatags"    % Version.http4s
    // "org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
  )

  // 1.0.0-Mx is on scala 3
  lazy val doobie = Seq(
    // Start with this one     (skunk?)
    "org.tpolecat" %% "doobie-core"     % Version.doobie,
    "org.tpolecat" %% "doobie-hikari"   % Version.doobie,
    "org.tpolecat" %% "doobie-postgres" % Version.doobie
    // "org.tpolecat" %% "doobie-quill"     % doobieV, // Support for Quill 3.4.10
    // "org.tpolecat" %% "doobie-scalatest" % Version.doobie % Test
  )

  // val chimney = Seq()
  // val spire = Seq()

  lazy val all = http4s ++ scodec ++ testingMUnit ++ circe ++ fs2 ++ cats ++ stdLibs ++ monocle

}
