package com.adtran.utilapp.logparser.aaa

import cats.data.{NonEmptyChain, NonEmptyList}
import cats.parse.Rfc5234.*
import cats.parse.*
import cats.parse.Numbers.*
import cats.parse.Accumulator.*
import cats.parse.{Numbers, Parser, Parser0}
import cats.parse.Parser.*
import com.comcast.ip4s.Ipv4Address

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQuery
import scala.util.Try

object ParserAtoms:

  // Validators

  /** v is an Int stricly within the given range */
  def intInside(a: Int, b: Int)(v: String): Boolean =
    val i = v.toInt
    i > a && i < b

  /** v is an Int within the given range, including the endpoint values */
  def intWithin(a: Int, b: Int)(v: String): Boolean =
    val i = v.toInt
    i >= a && i <= b

  val kComma                = Parser.char(',')
  val kDash                 = Parser.char('-')
  val kDot                  = Parser.char('.')
  val listSep: Parser[Unit] = kComma // .surroundedBy(Rfc5234.wsp.soft).void

  val kModule: Parser[Unit]    = Parser.ignoreCase("module")
  val kNamespace: Parser[Unit] = Parser.ignoreCase("namespace")

  def exactlyDigits(count: Int): Parser[String] = Numbers.digit.repExactlyAs(count)

  val year: Parser[String] = exactlyDigits(4).filter(v => intWithin(1900, 3000)(v))
  val month                = exactlyDigits(2).filter(v => intWithin(1, 12)(v))
  val day                  = exactlyDigits(2).filter(v => intWithin(1, 31)(v))
  val hour                 = exactlyDigits(2).filter(v => intWithin(0, 24)(v)) // Forget 0-24 or 01=>24
  val minute               = exactlyDigits(2).filter(v => intWithin(0, 60)(v))
  val second               = exactlyDigits(2).filter(v => intWithin(0, 60)(v))
  val msec                 = exactlyDigits(3)
  val offset               = exactlyDigits(4)                                  // No Z format, hmmm..

  val httpMethod = Parser.stringIn(List("GET", "POST", "PUT", "PATCH"))

  /** One segment of three digits, validated into the model class */
  val ipNumberSegment = Numbers.digits.filter(v => v.length <= 3)

  val ipV4: Parser[Ipv4Address] = ipNumberSegment
    .repSep(4, 4, kDot)
    .map((v: NonEmptyList[String]) => v.toList.mkString("."))
    .mapFilter(v => com.comcast.ip4s.Ipv4Address.fromString(v))
    .withContext("IPV4")
  // Meh, non-standard timestamps not as Instant format w/ Z for UTC.

  /** EOL character LF/CRLF/CR tried in order */
  val kEOL: Parser[Unit] = (Rfc5234.lf | Rfc5234.crlf | Rfc5234.cr).void

  def parseBadLogTimestamp(v: String): Instant = DateTimeFormatter.ISO_DATE_TIME.parse(v, Instant.from)
  val timestamp: Parser[Instant]               = Parser.until(kComma).mapFilter(ts => Try { parseBadLogTimestamp(ts) }.toOption)
  val name                                     = Parser.until(kComma)

  val jsonObject = Parser.until(kComma)
// val fullLine =
