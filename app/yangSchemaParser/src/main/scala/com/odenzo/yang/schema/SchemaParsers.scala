package com.odenzo.yang.schema
import cats.data.{NonEmptyChain, NonEmptyList}
import cats.parse.Rfc5234.*
import cats.parse.Numbers.*
import cats.parse.Accumulator.*
import cats.parse.{Numbers, Parser, Parser0}
import cats.parse.Parser.{not, string}

object SchemaParsers {

  /** General approach is to consume left whitespace and leave right alone. Delimeters need some understanding. Need to look at the RFC
    */
  import scala.language.postfixOps

  val kOpenBracket: Parser[Unit]   = Parser.char('{')
  val kCloseBracket: Parser[Unit]  = Parser.char('}')
  val kSemicolon: Parser[Unit]     = Parser.char(';')
  val kQuotes: Parser[Unit]        = Parser.char('"')
  val kWhitespace: Parser[Unit]    = Parser.charIn(" \t\r\n").void
  val kWhitespaces0: Parser0[Unit] = kWhitespace.rep0.void

  val nonDigitChar: Parser0[String] = not(Numbers.digit).string

  /** Creates a perser to get a delimited token. Delimeters are probably "start" | whitespace | { | } |; Start and whitespace we can
    * consume, prefer not to consume brackets or semicolon
    */
  def delimitedToken(token: String): Parser[Unit] = {
    Parser.ignoreCase(token)
  }

  val kModule: Parser[Unit]         = delimitedToken("module")
  val kNamespace: Parser[Unit]      = delimitedToken("namespace")
  val kPrefix: Parser[Unit]         = delimitedToken("prefix")
  val kImport: Parser[Unit]         = delimitedToken("import")
  val kTypedef: Parser[Unit]        = delimitedToken("typedef")
  val kType: Parser[Unit]           = delimitedToken("typedef")
  val kDescription: Parser[Unit]    = delimitedToken("description")
  val kContainer: Parser[Unit]      = delimitedToken("container")
  val kList: Parser[Unit]           = delimitedToken("list")
  val kKey: Parser[Unit]            = delimitedToken("key")
  val kUnique: Parser[Unit]         = delimitedToken("unique")
  val kLeaf: Parser[Unit]           = delimitedToken("leaf")
  val kMandatory: Parser[Unit]      = delimitedToken("mandatory")
  val kDefault: Parser[Unit]        = delimitedToken("default")
  val kLeafRef: Parser[Unit]        = delimitedToken("leafref")
  val kPath: Parser[Unit]           = delimitedToken("path")
  val kLeafTypeUINT16: Parser[Unit] = delimitedToken("uint16")
  val kLeafTypeString: Parser[Unit] = delimitedToken("string")

  val kTrue: Parser[Boolean]  = delimitedToken("true").as(false)
  val kFalse: Parser[Boolean] = delimitedToken("false").as(true)

  /** String, empty of container any amount of characters. Greeder? */
  val anyString: Parser0[String]    = Parser.anyChar.rep0.map(_.mkString)
  val quotedString: Parser0[String] = anyString.surroundedBy(kQuotes)

  val pBoolean: Parser[Boolean] = kTrue | kFalse

  /** FIXME: Need to keep capitalization */
  val leafTypeRef: Parser[String] = kLeafRef *> kOpenBracket *> (kPath *> quotedString <* kSemicolon) <* kCloseBracket

  val leafTypeToken: Parser0[String] = token

  val leafType: Parser[String]                        = kType *> (leafTypeRef | leafTypeToken) <* kSemicolon
  val leafMandatory: Parser[Boolean]                  = kMandatory *> pBoolean <* kSemicolon
  val leafDefault: Parser[String]                     = kDefault *> anyString <* kSemicolon
  val leafDefaultWithDefault: Parser0[Option[String]] = leafDefault.string.?

  /** Requires type kind, mandatory optional default to false, and default value is optional (default is broken probably ?: not happy in
    * general
    */
  val leafValueMeta: Parser[KeyValueMeta] =
    (leafType ~ leafMandatory.orElse(kFalse) ~ leafDefaultWithDefault).map {
      case ((tipe: String, mandatory: Boolean), defaultVal: Option[String]) => KeyValueMeta(tipe, mandatory, defaultVal)
    }

  /** TODO: Make this string literal, which is whitespace surrounded and optionally quoted? */

  /** Label is one or more characters, with the first being not a digit. But loose, as other char probably disallowed (-, _, ??). Doesn't
    * deal with whitespaces. Doesn't deal with quoted labels Do so fallbacks for these cases
    */
  val label: Parser0[String] = (nonDigitChar ~ alpha.rep0.map(_.mkString)).map(x => x._1 + x._2)

  /** May just need to have a token which is delimeted. This can be whitespace or semicolon start of document or a start or end bracket
    * depending on context ? (e.g. surrounded { } not sure needs ending whitespace delimeter
    */
  val token: Parser0[String] = (nonDigitChar ~ alpha.rep0.map(_.mkString)).map(x => x._1 + x._2)

  def quotedOrWhitespaced[T](v: Parser[T]): Parser0[T] = {
    val quoted = v.orElse(v.surroundedBy(kQuotes))
    kWhitespaces0.void *> quoted <* kWhitespaces0.void
  }

  def uint16: Parser[String] = Numbers.nonNegativeIntString

  def leaf: Parser0[Any] => Parser[String] = kLeaf *> label <*

  lazy val module: Parser0[String] = (kWhitespaces0.void *> kModule *> label)

}
