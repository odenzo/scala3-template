package com.odenzo.base

import cats._
import cats.data._
import cats.effect.IO

import java.util.concurrent.CompletableFuture
import scala.concurrent.Future

trait FU {

  def eitherToError[F[_]: ApplicativeThrow, A](t: Either[Throwable, A]): F[A]      = ApplicativeError[F, Throwable].fromEither(t)
  def optToError[F[_]: ApplicativeThrow, A](t: Option[A])(err: Throwable): F[A]    = ApplicativeError[F, Throwable].fromOption(t, err)
  def optionToError[F[_]: ApplicativeThrow, A](t: Option[A])(err: Throwable): F[A] =
    t match {
      case None    => ApplicativeThrow[F].raiseError(err)
      case Some(t) => ApplicativeThrow[F].pure(t)
    }

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
  def exactlyOne[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[A] = {
    val err = OError(s"Requires List Size 1 but  ${l.length}: $msg")
    if (l.length > 1) ApplicativeThrow[F].raiseError[A](err)
    else optionToError(l.headOption)(err)
  }

  /** Ensures 0 or 1 elements in a list, errors if > 1 */
  def optionOne[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[Option[A]] = {
    val err = OError(s"Expected List Size 0 or 1 but  ${l.length}: $msg")
    if (l.length > 1) ApplicativeThrow[F].raiseError(err)
    else ApplicativeThrow[F].pure(l.headOption)
  }

  /** Raises an Option.empty to Error */
  def required[F[_]: ApplicativeThrow, A](msg: String)(o: Option[A]): F[A] = optionToError(o)(OError(s"Required Value Missing: $msg"))

  /** Get Or ElseM */
  def whenEmpty[F[_]: ApplicativeThrow, A](x: F[A])(o: Option[A]): F[A] = o.fold(x)(ApplicativeThrow[F].pure(_))

  def requireNone[F[_]: ApplicativeThrow, A](msg: String)(o: Option[A]): F[Unit] = {
    o match {
      case None    => ApplicativeThrow[F].pure(())
      case Some(v) => ApplicativeThrow[F].raiseError(OError(s"Option Value Must Be Empty: $v -- $msg"))
    }
  }

  def nonEmptyListIO[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[NonEmptyList[A]] = {
    optionToError(NonEmptyList.fromList(l))(OError(s"NEL Requires List of 1+ $msg"))
  }

}
