package com.odenzo.common.core

import cats.*
import cats.data.*
import cats.syntax.all.*
import io.circe.*

type FError = ApplicativeError[?, Throwable]
object OError {

  def NOT_IMPLEMENTED: Throwable                                  = OError("Not Implemented")
  def apply(t: Throwable): Throwable                              = t
  def apply(msg: String): Throwable                               = new Throwable(msg)
  def apply(msg: String, cause: Throwable): Throwable             = new Throwable(msg, cause)
  def apply(msg: String, json: Json): Throwable                   = new Throwable(msg + s"\n${json.spaces4}")
  def apply(msg: String, json: Json, cause: Throwable): Throwable = new Throwable(msg + s"\n${json.spaces4}", cause)

}
