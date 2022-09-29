package com.odenzo.yang.schema

import cats.parse.Parser
import com.odenzo.yang.schema.SchemaParsers.*

class DevTest extends munit.FunSuite {

  test("Label") {
    val src = "module"
    val x   = label.parse("module  somelabel ")
    println(s"X: ${pprint.apply(x)}")
  }
}
