package com.odenzo.common.core

import cats.Show

import io.circe.Codec

import scala.util.Random

/** Note: User oprint instead of pprint to continue masking */
case class Secret(secret: String) {
  override def toString = s"${secret.take(2)}...${secret.takeRight(2)}"
}

object Secret {

  def generatePassword(len: Int = 15): String = Random.alphanumeric.take(len).toList.mkString
  def generate: Secret                        = Secret(generatePassword())
  def generate(len: Int = 15): Secret         = Secret(generatePassword(len))

  import io.circe.generic.auto.*

  implicit val show: Show[Secret] = Show.fromToString[Secret]
}

case class LoginCreds(user: String, password: Secret)

object LoginCreds {
  def genForUser(user: String) = LoginCreds(user, Secret.generate(15))
}
