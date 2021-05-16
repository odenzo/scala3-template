package com.odenzo.common.core

import pprint.*
import cats.syntax.all.*
import com.odenzo.common.core.Secret
import io.circe.*
import io.circe.syntax.*

object OPrint {

  def oprint[A](a: A): String = pp.apply(a, 120, 10000).render

  def secretHandler(a: Any): Option[Tree] = {
    a match {
      case a: Secret => pprint.Tree.Literal(f"Secret(${a.toString})").some
      case _         => Option.empty[Tree]
    }

  }

  def dprint[T](a: T): String = {
    a match {
      case j: Json       => jprint(j)
      case j: JsonObject => jprint(j)
      case general       => pprint.apply(general).render
    }
  }

  def jprint(jo: JsonObject): String = dprint(jo.asJson)

  def jprint(a: Json): String = a.spaces4

  val pp =
    new PPrinter(
      defaultWidth = 80, // Because often after logback prefix
      defaultHeight = 750,
      defaultIndent = 2,
      additionalHandlers = (secretHandler _).unlift,
      colorLiteral = fansi.Color.Yellow ++ fansi.Bold.On,
      colorApplyPrefix = fansi.Color.Magenta ++ fansi.Bold.On
    )
}
