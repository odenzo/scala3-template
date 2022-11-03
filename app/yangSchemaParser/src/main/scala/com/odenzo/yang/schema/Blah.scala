package com.odenzo.yang.schema

object Blah {

  val stream = fs2.Stream(1, 3, 4, 5, 5, 6, 7)
  stream.changesBy((v: Int) => v % 2) // Ignore consecutive even or odd
}
