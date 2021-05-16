package com.odenzo.base

import java.util.concurrent.CompletableFuture
import scala.concurrent.Future
import cats.data._
import cats.effect.IO

trait IOU {

  def eitherToError[A](t: Either[Throwable, A]): IO[A]      = IO.fromEither(t)
  def optionToError[A](t: Option[A])(err: Throwable): IO[A] = IO.fromOption(t)(err)

  def toIO[A](fn: => CompletableFuture[A]): IO[A] = {
    import scala.jdk.FutureConverters._
    val ff: IO[Future[A]] = IO.delay(fn.asScala)
    IO.fromFuture(ff) // Does IO.async under the hood.
  }

  /** Allows  toIO(Future.successful("a") or toIO(callReturningFuture(args))
    * such that the future is deflation because fn is call-by-name
    */
  def fromFuture[A](fn: => Future[A]): IO[A] = {
    IO.fromFuture(IO(fn))
  }

  /** Ensures list has exactly one element or raisesError */
  def exactlyOne[A](msg: String)(l: List[A]): IO[A] = {
    val err = OError(s"Requires List Size 1 but  ${l.length}: $msg")
    if (l.length > 1) IO.raiseError[A](err)
    else IO.fromOption(l.headOption)(err)
  }

  /** Ensures 0 or 1 elements in a list, errors if > 1 */
  def optionOne[A](msg: String)(l: List[A]): IO[Option[A]] = {
    val err = OError(s"Expected List Size 0 or 1 but  ${l.length}: $msg")
    if (l.length > 1) IO.raiseError[Option[A]](err)
    else IO.pure(l.headOption)
  }

  /** Raises an Option.empty to Error */
  def required[A](msg: String)(o: Option[A]): IO[A] = {
    IO.fromOption(o)(OError(s"Required Value Missing: $msg"))
  }

  /** Get Or ElseM */
  def whenEmpty[A](x: IO[A])(o: Option[A]): IO[A] = o.fold(x)(IO.pure)

  def requireNone[A](msg: String)(o: Option[A]): IO[Unit] = {
    o match {
      case None    => IO.unit
      case Some(v) => IO.raiseError[Unit](OError(s"Option Value Must Be Empty: $v -- $msg"))
    }
  }

  def nonEmptyListIO[A](msg: String)(l: List[A]): IO[NonEmptyList[A]] = {
    IO.fromOption(NonEmptyList.fromList(l))(OError(s"NEL Requires List of 1+ $msg"))
  }

}

object IOU extends IOU
