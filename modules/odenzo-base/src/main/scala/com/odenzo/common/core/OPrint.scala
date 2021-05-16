package com.odenzo.common.core

import pprint.*
import cats.syntax.all.*
import com.odenzo.common.core.Secret

object OPrint {

  def oprint[A](a: A): String = pp.apply(a, 120, 10000).render

  def secretHandler(a: Any): Option[Tree] = {
    a match {
      case a: Secret => pprint.Tree.Literal(f"Secret(${a.toString})").some
      case _         => Option.empty[Tree]
    }

  }

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
