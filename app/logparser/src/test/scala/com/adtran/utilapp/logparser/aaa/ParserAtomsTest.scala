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

class ParserAtomsTest extends munit.FunSuite {
  val logger = LoggerFactory.getLogger
  test("IP Segment") {
    val v = "0"
    ParserAtoms.ipNumberSegment.parse(v) match {
      case Left(err)  => fail(s"Parsing: $v -> $err")
      case Right(res) =>
        logger.info(s"Results: $v ==> $res")
        Assertions.assert(res._1.isEmpty)
    }

  }

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
}
