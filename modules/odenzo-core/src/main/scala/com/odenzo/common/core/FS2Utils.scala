package com.odenzo.common.core
import fs2.*
import cats.effect.*
import cats.effect.syntax.all.*

import cats.*
import cats.data.*
import cats.syntax.all.*

/** An excuse to update libs automatically basically. FS2 and FS2 Reactive Streams are mostly used.
  */
object FS2Utils {

  def burster() = {
    fs2
      .Stream(1, 2, 3, 4, 5)
      .map(_ + 4)
      .compile
      .toList
  }
}
