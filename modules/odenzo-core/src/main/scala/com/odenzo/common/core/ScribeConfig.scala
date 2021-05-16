//package com.odenzo.base
//
//import scribe.{Level, Logger, Priority}
//import scribe.filter._
//
//trait ScribeHelpers {
//
//  def applyFilter(fb: FilterBuilder): Logger = {
//    scribe.Logger.root.withModifier(fb).replace()
//  }
//
//  def excludeByClass(clazzes: List[Class[_]], minLevel: Level): FilterBuilder = {
//    val names   = clazzes.map(_.getName)
//    scribe.info(s"Filtering Classes: $names to $minLevel")
//    val filters = names.map(n => className(n))
//    select(filters: _*).include(level >= minLevel)
//  }
//
//  /** Helper to filter out messages in the packages given below the given level
//    *
//    * @return a FilterBuilder kindof LogModifier filter that can be used with .withModifier()
//    */
//  def setLevelOnPackages(packages: List[String], atOrAboveLevel: Level, priority: Priority = Priority.Normal): FilterBuilder = {
//    val ps: List[Filter] = packages.map(p => packageName.startsWith(p))
//    select(ps: _*)
//      .include(level >= atOrAboveLevel)
//      .exclude(level < atOrAboveLevel)
//      .priority(priority)
//  }
//
//  def resetAllToLevel(level: Level = Level.Debug) = {
//    scribe.info(s"Resetting Levels to $level")
//    scribe.Logger.root.clearHandlers().clearModifiers().withHandler(minimumLevel = Some(level)).replace()
//  }
//}
//
///** Scribe can be a sf4j implementer -- but can't pipe log loback for its file based config.
//  * For simple stuff thats a pain since I am not putting in a log control API.
//  */
//object ScribeConfig extends ScribeHelpers {
//
//  def testConfig() = {
//    // Reset and turn all the com.odenzo.utils.logging levels to Debug for noisy ness
//    import scribe.format._
//    resetAllToLevel(Level.Debug) // filter in logback
//    // See FormatBlock
//    val myFormatter: Formatter = formatter"SMOKER  [$timeStamp] $levelPaddedRight $position - $message$newLine"
//    val strict                 = Formatter.strict
//
//    Logger.root.clearHandlers().withHandler(formatter = myFormatter).replace()
//    mutePackages(noisyHttp)
//  }
//
//  def mutePackages(packages: List[String]) = {
//    val packagesToMute =
//      List("org.http4s.blazecore", "org.http4s.blaze", "org.http4s.client", "com.datastax.oss.driver")
//    val filter         = setLevelOnPackages(packages, Level.Info)
//    applyFilter(filter)
//
//    // applyFilter(excludePackageSelction(List("org.http4s.client.middlware")))
//  }
//
//  val noisyHttp = List("org.http4s.blazecore", "org.http4s.blaze", "org.http4s.client")
//}
