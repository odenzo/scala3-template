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
import ParserAtoms.*
import fs2.io.file.Files
import fs2.text
import org.http4s.*
import org.http4s.Uri.Path
import org.http4s.Uri
import fs2.io.file.Path as FS2Path
import io.circe.*
import io.circe.syntax.*

/** Parsing for AAA Accounting Logs (as od Sep 2022) */
object AAALogParser {

  // 2022-09-28T14:18:13.414+0000,{"message":"Anonymous access not authorized"},10.160.34.135,GE and some other, fuk non-structured logging.
  def parseFile(f: os.Path) = {
    val theFile = FS2Path.fromNioPath(f.toNIO)
    Files[IO]
      .readAll(theFile)
      .through(text.utf8.decode)
      .through(text.lines) // This is going to assume nobody puts a EOL in each log record.
      .map(s => parseLine(s))
      .
    // .handleErrorWith()
//      .intersperse("\n")
//      .through(text.utf8.encode)
//      .through(Files[IO].writeAll(Path("testdata/celsius.txt")))
  }

  def successPipe[T: Encoder](file: FS2Path): fs2.Pipe[IO, T, Unit] = _.toString()
    .intersperse("\n")
    .through(text.utf8.encode)
    .through(Files[IO].writeAll(Path("testdata/celsius.txt")))
  // val errorPipe = fs2.Pipe

  /** We don't like parse errors, so throw as exception leaking impl. for the caller to dispose of as required. */
  def parseLine(s: String): Either[Parser.Error, Nothing] = lineParser.parse(s).map(res => ParsingException.throwIfBad(src, res))

  lazy val lineParser: Parser[AAALogLine] = {
    val lp: Parser[(Instant, String, Ipv4Address, Method, Path, String)] =
      (timestamp <* kComma, name <* kComma, ipV4 <* kComma, httpMethod <* kComma, httpPath <* kComma, Parser.until(kEOL) <* kEOL).tupled

    lp.map { tup => summon[Mirror.Of[AAALogLine]].fromProduct(tup) }
  }
}

case class AAALogLine(ts: Instant, user: String, ip: Ipv4Address, method: Method, path: Path, body: String) derives Codec
