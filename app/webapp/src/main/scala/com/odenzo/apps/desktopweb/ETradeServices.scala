package com.odenzo.accounting.desktopweb
import cats.effect.*
import cats.effect.syntax.all.*
import cats.*
import cats.data.*
import cats.syntax.all.*
import cats.effect.IO
import com.odenzo.accounting.desktopweb.html.{LoginPage, MainMenu}
import com.odenzo.base.OPrint.oprint
import com.odenzo.etrade.api.AccountsApi
import com.odenzo.etrade.auth.{ETradeAuthentication, ETradeSession, ETradeSessionManager}
import com.odenzo.etrade.models.{ConsumerKey, ETradeSecrets}
import com.odenzo.http4s.server.Https4sServerUtils
import com.odenzo.https.client.utils.Clients
import com.tersesystems.blindsight.LoggerFactory
import org.http4s.{Response, Status, Uri}
import org.http4s.Status.{Redirection, TemporaryRedirect}
import org.http4s.client.Client
import org.http4s.client.oauth1.{Consumer, Token}
import org.http4s.headers.Location
import org.http4s.implicits.http4sLiteralsSyntax
import scalatags.*
import scalatags.Text.all.*
import scribe.format.message

class ETradeServices(sandbox: Boolean = true, secretsResource: String = "etrade-secrets.json")(implicit cs: ContextShift[IO])
    extends Https4sServerUtils {

  private val log = LoggerFactory.getLogger
  val sessions    = new ETradeSessionManager(sandbox, secretsResource)

  def home(): Text.TypedTag[String] = LoginPage.fullLoginPage()

}

object ETradeServices {
  val defaultUser = "stevef"
}
