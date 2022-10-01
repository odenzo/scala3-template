package com.adtran.utilapp.logparser.aaa

import cats.effect.*
import cats.effect.syntax.all.*
import cats.*
import cats.data.*
import cats.syntax.all.*
import cats.parse.Parser
import com.comcast.ip4s.Ipv4Address
import com.tersesystems.blindsight.LoggerFactory
import munit.Assertions

import java.time.Instant
import java.time.temporal.{ChronoField, TemporalAccessor}

class ParserAtomsTest extends munit.FunSuite {
  val logger = LoggerFactory.getLogger

  test("IPNumber") {
    import com.comcast.ip4s.*
    val v: Ipv4Address = ipv4"127.0.0.1"
    ParserAtoms.ipV4.parse(v.toString) match {
      case Left(err)                         => fail(s"Parsing: $v -> $err")
      case Right(res: (String, Ipv4Address)) =>
        logger.info(s"Results: $v ==> $res")
        Assertions.assert(res._1.isEmpty)
        Assertions.assert(res._2 === v)
    }

  }

  test("AdTran DateTimeFormat") {
    val data: TemporalAccessor = ParserAtoms.adtranDateTimeFormatter.parse("2022-09-28T14:20:57.236+0000")
    val epochSeconds           = data.getLong(ChronoField.INSTANT_SECONDS)
    val subsecondNano          = data.getLong(ChronoField.NANO_OF_SECOND)
    val instant                = Instant.ofEpochSecond(epochSeconds, subsecondNano)
    logger.info(s"Instant = $instant")

  }

  test("AdTran Timestamp") {
    val i: Either[Parser.Error, (String, Instant)] = ParserAtoms.timestamp.parse("2022-09-28T14:20:57.236+0000,morestuff")
    i match
      case Left(value)  => logger.error(s"${pprint.apply(value)}")
      case Right(value) => logger.info(s"Correct Instant: $value")
  }
}
