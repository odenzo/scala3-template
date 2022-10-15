package com.adtran.utilapp.logparser.aaa

import cats.data.{NonEmptyChain, NonEmptyList}
import cats.parse.Rfc5234.*
import cats.parse.*
import cats.parse.Numbers.*
import cats.parse.Accumulator.*
import cats.parse.{Numbers, Parser, Parser0}
import cats.parse.Parser.*
import com.comcast.ip4s.Ipv4Address
import com.tersesystems.blindsight.LoggerFactory
import org.http4s.Method.GET
import org.http4s.{Method, Uri}

import java.time.Instant
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.{ChronoField, TemporalField, TemporalQuery}
import scala.util.{Failure, Success, Try}
import org.http4s.syntax.all.*
object ParserAtoms {
  private val logger = LoggerFactory.getLogger
  // Validators

  /** v is an Int stricly within the given range */
  def intInside(a: Int, b: Int)(v: String): Boolean =
    val i = v.toInt
    i > a && i < b

  /** v is an Int within the given range, including the endpoint values */
  def intWithin(a: Int, b: Int)(v: String): Boolean =
    val i = v.toInt
    i >= a && i <= b
  def exactlyDigits(count: Int): Parser[String]     = Numbers.digit.repExactlyAs(count)

  val kComma: Parser[Unit] = Parser.char(',').withContext("Comma")

  val kDash: Parser[Unit]        = Parser.char('-')
  val kDot: Parser[Unit]         = Parser.char('.')
  val listSep: Parser[Unit]      = kComma // .surroundedBy(Rfc5234.wsp.soft).void
  val httpMethod: Parser[Method] = Parser.until(kComma).mapFilter(s => Method.fromString(s).toOption)
  val httpPath: Parser[Uri.Path] = Parser.until(kComma).mapFilter(s => Try(Uri.Path.unsafeFromString(s)).toOption)

  val ipV4: Parser[Ipv4Address] = Parser
    .until(kComma)
    .mapFilter(v => com.comcast.ip4s.Ipv4Address.fromString(v))
    .withContext("IPV4")
  // Meh, non-standard timestamps not as Instant format w/ Z for UTC.

  /** EOL character LF/CRLF/CR tried in order */
  val kEOL: Parser[Unit] = (Rfc5234.lf | Rfc5234.crlf | Rfc5234.cr).void

  /** Timestamp/aka ISO_INSTANT please! 2022-09-28T14:20:57.236+0000.
    */
  val adtranDateTimeFormatter: DateTimeFormatter        = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  def parseBadLogTimestamp(ts: String): Option[Instant] = Try {
    logger.info(s"Decoding TStamp $ts")
    // DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(ts, Instant.from)
    val data          = adtranDateTimeFormatter.parse(ts)
    val epochSeconds  = data.getLong(ChronoField.INSTANT_SECONDS)
    val subsecondNano = data.getLong(ChronoField.NANO_OF_SECOND)
    Instant.ofEpochSecond(epochSeconds, subsecondNano)
  } match
    case Success(value)     => Some(value)
    case Failure(exception) =>
      logger.error(s"Failure Parsing TStamp $ts", exception)
      None

  val timestamp: Parser[Instant] = Parser
    .until(kComma)
    .mapFilter(ts => parseBadLogTimestamp(ts))
    .withContext("timestamp")
  val name: Parser[String]       = Parser.until(kComma)

  val jsonObject: Parser[String] = Parser.until(kComma)
  // val fullLine =

  /*
cala> type Flat[T <: Tuple] <: Tuple = T match
     |   case EmptyTuple => EmptyTuple
     |   case h *: t => h match
     |     case Tuple => Tuple.Concat[Flat[h], Flat[t]]
     |     case _     => h *: Flat[t]
     |

scala> def flat[T <: Tuple](v: T): Flat[T] = (v match
     |   case e: EmptyTuple => e
     |   case h *: ts => h match
     |     case t: Tuple => flat(t) ++ flat(ts)
     |     case _        => h *: flat(ts)).asInstanceOf[Flat[T]]
def flat[T <: Tuple](v: T): Flat[T]

scala> def ft[A <: Tuple, C](f: Flat[A] => C): A => C = a => f(flat(a))
def ft[A <: Tuple, C](f: Flat[A] => C): A => C

scala> val f0: ((Int, Int, Int)) => Int = x => x._1 + x._2 + x._3


scala> def g0(f: ((Int, (Int, Int))) => Int): Int = f(1,(2,3))

scala> g0(ft(f0))
val res0: Int = 6
   */
}

case class ParsingException(txt: String, error: Parser.Error) extends Throwable

object ParsingException:
  /** Checks parsing result and throws ParsingException or returns success value. */
  def throwIfBad[T](src: String, res: Either[Parser.Error, T]): T = res match {
    case Right(v)  => v
    case Left(err) => throw ParsingException(src, err)
  }
