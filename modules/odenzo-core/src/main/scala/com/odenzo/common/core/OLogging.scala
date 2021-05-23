package com.odenzo.common.core

import io.circe.*
import io.circe.syntax.*

/** This will be the place to deal with Scribe and standard logging packages. Stuff done in test stays in test, including the CI filters.
  */
trait OLogging {

  /** Debug print a few cmmon types or delegate to pretty printer I want to handle Showable and non-Showable, how to check at runtime if
    * showable? So, it will now use show :-)
    *
    * @param a
    *   @return
    * @tparam T
    */

  //  /** Clears all handlers and modifiers (e.g. muted classes and packages) and set local level to l */
  //  def resetTo(l: Level): Logger = {
  //    scribe.Logger.root.clearHandlers().clearModifiers().withHandler(minimumLevel = Some(l)).replace()
  //  }
  //
  //  def setLogLevel(l: Level): Logger = {
  //    scribe.Logger.root.clearHandlers().withHandler(minimumLevel = Some(l)).replace()
  //  }
  //
  //  def mutePackages(l: List[String]): Logger = applyFilter(excludePackageSelction(l, Level.Warn, Priority.Highest))
  //
  //  /** @return a filter that can be used with .withModifier()
  //    */
  //  def excludePackageSelction(packages: List[String], atOrAboveLevel: Level, priority: Priority): FilterBuilder = {
  //    val ps: List[Filter] = packages.map(p => packageName.startsWith(p))
  //    val fb               = select(ps: _*).exclude(level < atOrAboveLevel).includeUnselected.copy(priority = priority)
  //    fb
  //  }
  //
  //  def excludeByClasses(clazzes: List[Class[_]], minLevel: Level): FilterBuilder = {
  //    val names   = clazzes.map(_.getName)
  //    val filters = names.map(n => className(n))
  //    select(filters: _*).include(level >= minLevel)
  //  }

  /** Creates an exclude filter for the given class name that anything under the minLevel is filtered. This needs to be applied (generally
    * to the root logger). Set as highest priority.
    *
    * @param clazz
    *   @param minLevel
    *
    * @return
    */
  //  def excludeByClass(clazz: Class[_], minLevel: Level): FilterBuilder = {
  //    val name   = clazz.getName
  //    val filter = className(name)
  //    select(filter).exclude(level < minLevel).priority(Priority.Highest)
  //  }
  //
  //  /** FilterBuilder is a LogModifier */
  //  def applyFilter(filter: FilterBuilder): Logger = {
  //    applyLogModifier(filter)
  //  }
  //
  //  def applyLogModifier(mod: LogModifier): Logger = {
  //    scribe.Logger.root.withModifier(mod).replace()
  //  }
  //
  //  def clearLogModifier(mon: LogModifier): Logger = {
  //    scribe.Logger.root.withoutModifier(mon) // And there is a clear too.
  //  }
  //
  //  def replaceModifiers(packages: List[String], l: Level): Unit = {
  //    scribe.debug(s"Setting Packages Level to $l")
  //    val pri      = Priority.Normal // unnecessary since clearing existing modifiers, but handy for future.
  //    val replaced = scribe.Logger.root
  //      .clearModifiers()
  //      .withModifier(excludePackageSelction(packages, l, pri))
  //      .replace()
  //    ()
  //  }

}
