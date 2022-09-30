import sbt._
object Libs {

  lazy val stdLibs = Seq(
    "com.lihaoyi"   %% "pprint"           % Version.pprint,
    "com.lihaoyi"   %% "os-lib"           % Version.osLib,
    "org.typelevel" %% "case-insensitive" % Version.caseInsensitive,
    "org.typelevel" %% "literally"        % Version.literally
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
    // "io.circe" %% "circe-extras"  % Version.circe,
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

  val catsParse = Seq("org.typelevel" %% "cats-parse" % Version.catsParse)
  val squants   = Seq("org.typelevel" %% "squants" % Version.squants)

  // val chimney = Seq()
  val spire = Seq("org.typelevel" %% "spire" % Version.spire)

  val decline = Seq("com.monovore" %% "decline" % Version.decline, "com.monovore" %% "decline-effect" % Version.decline)

  val blindsight = Seq(
    "com.tersesystems.blindsight" %% "blindsight-logstash" % Version.blindsight
    /*  "com.fasterxml.jackson.module" % "jackson-module-scala" % Version.jackson*/
  )

  lazy val slf4s = Seq("ch.qos.logback" % "logback-classic" % Version.logback, "rocks.heikoseeberger" %% "slf4s" % Version.slf4s)

  // Scala 3 work in progress but not published
  val pureconfig = Seq("com.github.pureconfig" %% "pureconfig-core" % Version.pureconfig)

  val comcastNetorks = Seq("com.comcast" %% "ip4s-core" % Version.comcastNetworks)
  lazy val all       = catsParse ++ scodec ++ testingMUnit ++ circe ++ fs2 ++ cats ++ stdLibs ++ monocle ++ decline ++ blindsight ++ pureconfig

}
