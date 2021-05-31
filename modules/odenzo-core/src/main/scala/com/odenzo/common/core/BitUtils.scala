package com.odenzo.common.core

import scodec.bits.*
import scodec.bits.Literals.*

/** scodec-bits in acution */
object BitUtils extends OLogging {

  def foo() = {
    val fromBiun: BitVector = bin"0001"
    logger.info(s"$fromBiun")
    val b                   = hex"deadbeef"
    val c                   = hex"1"

  }
}
