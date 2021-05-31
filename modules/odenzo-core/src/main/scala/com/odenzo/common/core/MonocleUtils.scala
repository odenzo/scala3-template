package com.odenzo.common.core

import monocle.*
import monocle.syntax.all.*

object MonocleUtils {

  case class User(name: String, address: Address)
  case class Address(streetNumber: Int, streetName: String)

  val anna = User("Anna", Address(12, "high street"))

  def someMonocleExample() = {
    val user = User("Anna", Address(12, "high street"))

    user.focus(_.name).replace("Bob")
    // res: User = User("Bob", Address(12, "high street"))

    user.focus(_.address.streetName).modify(_.toUpperCase)
    // res: User = User("Anna", Address(12, "HIGH STREET"))

    user.focus(_.address.streetNumber).get
  }
}
