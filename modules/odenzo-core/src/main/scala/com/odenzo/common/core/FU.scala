package com.odenzo.common.core

import cats.*
import cats.data.*
import cats.syntax.all.*
import cats.effect.*
import cats.effect.syntax.all.*
import scala.concurrent.Future
import java.util.concurrent.CompletableFuture
import com.odenzo.common.core.OError

/** Some tagless final style utilities, usually used like ``` val name = "odenzo" ; queryData(name) >>= FU.required("No record for $name")
  * Where queryData is String => F[Option[Record]] >>=
  */
trait FU {

  def eitherToError[F[_]: ApplicativeThrow, A](t: Either[Throwable, A]): F[A]      = ApplicativeError[F, Throwable].fromEither(t)
  def optionToError[F[_]: ApplicativeThrow, A](t: Option[A])(err: Throwable): F[A] = ApplicativeError[F, Throwable].fromOption(t, err)

  def toIO[A](fn: => CompletableFuture[A]): IO[A] = {
    import scala.jdk.FutureConverters.*
    val ff: IO[Future[A]] = IO.delay(fn.asScala)
    IO.fromFuture(ff) // Does IO.async under the hood.
  }

  /** Allows toIO(Future.successful("a") or toIO(callReturningFuture(args)) such that the future is delayed because fn is call-by-name
    */
  def fromFuture[A](fn: => Future[A]): IO[A] = IO.fromFuture(IO(fn))

  /** Ensures list has exactly one element or raisesError
    */
  def exactlyOne[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[A] =
    val len = l.length
    val err = OError(s"Requires List Size 1 but  $len: $msg")
    if len != 1 then ApplicativeThrow[F].raiseError[A](err)
    else optionToError(l.headOption)(err)

  /** Ensures 0 or 1 elements in a list, errors if > 1
    */
  def optionOne[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[Option[A]] =
    val err = OError(s"Expected List Size 0 or 1 but  ${l.length}: $msg")
    if l.length > 1 then  ApplicativeThrow[F].raiseError(err)
    else ApplicativeThrow[F].pure(l.headOption)

  /** Raises an Option.empty to Error
    */
  def required[F[_]: ApplicativeThrow, A](msg: String)(o: Option[A]): F[A] = optionToError(o)(OError(s"Required Value Missing: $msg"))

  /** If option is empty execute fnF else return option value in F. Currently no widening/variance
    */
  def whenEmpty[F[_]: ApplicativeThrow, A](fnF: F[A])(o: Option[A]): F[A] = o.fold(fnF)(ApplicativeThrow[F].pure(_))

  /** Reqiore the option to be empty, return F.unit or raise an error */
  def requireNone[F[_]: ApplicativeThrow, A](msg: String)(o: Option[A]): F[Unit] = {
    o match {
      case None    => ApplicativeError[F, Throwable].unit
      case Some(v) => ApplicativeThrow[F].raiseError(OError(s"Option Value Must Be Empty: $v -- $msg"))
    }
  }

  def nonEmptyListIO[F[_]: ApplicativeThrow, A](msg: String)(l: List[A]): F[NonEmptyList[A]] =
    optionToError(NonEmptyList.fromList(l))(OError(s"NEL Requires List of 1+ $msg"))

}
