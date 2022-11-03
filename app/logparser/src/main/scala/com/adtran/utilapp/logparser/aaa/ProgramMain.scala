package com.adtran.utilapp.logparser.aaa

import cats.effect.*
import cats.effect.syntax.all.*
import cats.*
import cats.data.*
import cats.syntax.all.*
import os.CommandResult
import scala.util.chaining.*
import java.nio.file.Path
import com.tersesystems.blindsight._
import com.tersesystems.blindsight.DSL._

object ProgramMain {
  val logger = LoggerFactory.getLogger

  /** Files of interest are this with possibly an appended ".NN" which is non-zero padded integer */
  val filePrefix = "firefly-aaa-accounting.log"

  def process(cmd: ProgramCmd): IO[Unit] =
    unpackFiles(cmd.srcDir) *>
      IO.unit

  def unpackFiles(nioPath: Path) = IO
    .blocking {
      val dir: os.Path = os.Path(nioPath)
      logger.info(s"Processing Directory: $dir")
      val valid        = os.exists(dir) && os.isDir(dir) // && os. asFile.canRead
      IO.raiseUnless(valid)(throw new IllegalArgumentException(s"$dir doesn't exist as readable directory"))

      os.list(dir)
        .filter(file => os.isFile(file) && file.last.startsWith(filePrefix) && file.ext === "gz")
        .filterNot(file => os.exists(dir / file.last.dropRight(3)))
        .tap(files => logger.info(s"Going to gunzip these files: ${pprint.apply(files)}"))
        .toList
    }
    .flatMap(_.traverse(f => gunzip(f)))

  def gunzip(file: os.Path): IO[Unit] =
    logger.info(s"gunzip $file")
    IO.blocking { os.proc(s"gunzip", file).call() }
      .flatMap {
        case CommandResult(0, _)   => IO.unit
        case result: CommandResult =>
          // Handle Error
          val stdErr = result.err.text()
          logger.warn(s"Failure: $result - $stdErr ")
          IO.raiseError(Throwable(s"Problem UNGZIPPING CODE: ${result.exitCode} : $stdErr "))
      }
}
