package com.adtran.utilapp.logparser.aaa

import cats.effect.*
import cats.effect.syntax.all.*
import cats.*
import cats.data.*
import cats.syntax.all.*
import cats.parse.*
import cats.parse.Parser.void
import com.comcast.ip4s.Ipv4Address

import java.time.Instant
import scala.Tuple.{Drop, Take}
import scala.deriving.Mirror

/** Parsing for AAA Accounting Logs (as od Sep 2022) */
object AAALogParser {

  import ParserAtoms.*

  val lp: Parser[(Instant, String, Ipv4Address, String, String, String)] =
    (timestamp <* void(kComma), name <* kComma, ipV4 <* kComma, httpMethod <* kComma, Parser.until(kComma), Parser.until(kEOL)).tupled

  val model: Parser[AAALogLine] = lp.map { (tup: (Instant, String, Ipv4Address, String, String, String)) =>
    summon[Mirror.Of[AAALogLine]].fromProduct(tup)
  }
}

case class AAALogLine(ts: Instant, user: String, ip: Ipv4Address, method: String, url: String, body: String, resultLine: String)
