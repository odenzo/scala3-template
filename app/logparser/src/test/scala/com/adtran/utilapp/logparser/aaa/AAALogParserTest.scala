package com.adtran.utilapp.logparser.aaa

import cats.*
import cats.data.*
import cats.effect.*
import cats.effect.syntax.all.*
import cats.parse.Parser
import cats.syntax.all.*
import com.comcast.ip4s.Ipv4Address
import com.tersesystems.blindsight.LoggerFactory
import munit.Assertions

class AAALogParserTest extends munit.FunSuite {
  private val logger = LoggerFactory.getLogger

  val noObject =
    """2022-09-28T14:20:57.236+0000,reillyj3_adt,192.168.1.250,GET,/api/restconf/data/adtran-cloud-platform-uiworkflow:transitions/transition=da5cc28a-ea49-4c8c-8481-540cfcda9431,,200 OK
      |""".stripMargin

  val withObject =
    """2022-09-28T14:20:56.930+0000,reillyj3_adt,192.168.1.250,POST,/api/restconf/operations/adtran-cloud-platform-uiworkflow:create,{"input": {"interface-context": {"interface-name": "BAAMZT-OLT4-48", "interface-type": "gpon", "number-of-lower-layer-interfaces": 0, "profile-vector-name": "OLT_Leaf_GPON_Interface_Vector"}}},200 OK
      |""".stripMargin

  test("Plain") {
    AAALogParser.lineParser.parse(noObject) match {
      case Left(err)  => fail(s"Parsing: -> ${pprint(err)} ${err._2}")
      case Right(res) =>
        logger.info(s"Results: ==> ${pprint.apply(res)}")
        Assertions.assert(res._1.isEmpty)
    }

  }

}
