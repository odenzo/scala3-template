import sbt._

object Version {

  // Very Standard Stack
  val cats            = "2.8.0"
  val catsEffect      = "3.3.14"
  val catsParse       = "0.3.8"
  val circe           = "0.14.3"
  val doobie          = "1.0.0-RC2"
  val fs2             = "3.3.0"


  val fs2DiffsonCirce = "4.3.0"


  val fs2Data         = "1.5.1"

  val monocle         = "3.1.0"
  val http4s          = "1.0.0-M37"
  // Logging Options
  val logback         = "1.4.1"
  val slf4s           = "0.3.0" // Prefer blindsight
  val blindsight      = "1.5.2"
  val jackson         = "2.11.0"

  // Testing Options, munit usually, or weavertest for integrations

  val munit     = "0.7.29"
  val munitCats = "1.0.7"


  // Common Utility Libraries
  val osLib           = "0.8.1"
  val pprint          = "0.8.0"
  val scodec          = "2.2.0"
  val scodecBits      = "1.1.34"
  val spire           = "0.18.0"
  val squants         = "1.8.3"
  val decline         = "2.3.1"
  val pureconfig      = "0.17.1" // Possibly switch to Ciris
  val comcastNetworks = "3.2.0"
  val caseInsensitive = "1.3.0"
  val literally       = "1.1.0"
  val ducktape        = "0.1.0-RC2"


  // libraryDependencies += "org.gnieh" %% f"diffson-$jsonLib" % "4.1.1"
}
