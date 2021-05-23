package com.odenzo.accounting.desktopweb

import cats.effect.unsafe.*
import cats.effect.IOPlatform
import cats.effect.ExitCode
import com.odenzo.base.OPrint.oprint
import com.odenzo.http4s.server.HTTPD

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object HTTPServer extends IOApp {

  val useSandbox: Boolean = true

  override def run(args: List[String]): IO[ExitCode] = {
    val eTradeServices                    = new ETradeServices(sandbox = true) // Has Session Manager embedded
    val routes                            = new ETradeRoutes(eTradeServices).routes
    val runHttpServer: Fiber[IO, Nothing] = HTTPD.background("localhost", 5555, routes)
    val openBrowser: IO[CommandResult]    = IO(os.proc("open", "http://localhost:5555/etrade").call())
    for {
      _     <- IO(logger.info("Starting HTTPD Server Style"))
      fiber <- IO(runHttpServer)
      _     <- IO.sleep(FiniteDuration(2, "seconds"))
      res   <- openBrowser
      _     <- IO(logger.info(s"Browser Open Results ${oprint(res)}"))
      _     <- IO.sleep(FiniteDuration(20, TimeUnit.MINUTES)) // Should join on the fiber which never quits
    } yield ExitCode.Success
  }

}
