package com.adtran.utilapp.logparser.aaa

import com.monovore.decline.*
import com.monovore.decline.effect.*
import cats.effect.*
import cats.effect.syntax.all.*
import cats.*
import cats.data.*
import cats.syntax.all.*
import com.adtran.utilapp.logparser.aaa.ProgramCmd.options
import com.tersesystems.blindsight._
import com.tersesystems.blindsight.DSL._
import java.nio.file.Path

/** Decline Command Line Parser. Throw in src path validation and we mkdir destDir if needed */
case class ProgramCmd(srcDir: Path, destDir: Path, plot: Boolean)

object ProgramCmd:
  val userOpt: Opts[Boolean] = Opts.flag("plot", help = "Output Files for Plotting w/ ??? ", "p").orFalse
  val srcDir: Opts[Path]     = Opts.argument[Path]("srcDir").withDefault(Path.of("/tmp/firefly-aaa"))
  val outDir: Opts[Path]     = Opts.option[Path]("out", "Directory to outpout files to", "o").withDefault(Path.of("."))

  val options: Opts[ProgramCmd] = (srcDir, outDir, userOpt).mapN(ProgramCmd.apply)

object CommandLineMain extends CommandIOApp("aaa-api-parser", header = "Parse AAA Logs for API Usage", true, "0.0.1") {
  val logger                            = LoggerFactory.getLogger
  override def main: Opts[IO[ExitCode]] =
    options.map { (cmd: ProgramCmd) =>
      logger.info(s"Running: ${pprint.apply(cmd)}")
      ProgramMain.process(cmd) *> IO.pure(ExitCode.Success)
    }
}
