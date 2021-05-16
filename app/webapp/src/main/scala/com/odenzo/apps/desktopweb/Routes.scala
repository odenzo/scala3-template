package com.odenzo.accounting.desktopweb

import com.odenzo.accounting.desktopweb.html.OAuthCallback
import com.odenzo.base.IOU
import com.odenzo.etrade.api.AccountsApi
import com.odenzo.http4s.server.Https4sServerUtils
import org.http4s._
import org.http4s.dsl._
import scala.concurrent.ExecutionContext

trait ETradeRoutingUtils extends Https4sServerUtils {
  object OptionalSyncQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Boolean]("sync")
  object OAuthVerifierQPM              extends QueryParamDecoderMatcher[String]("oauth_verifier")
  object OAuthTokenQPM                 extends QueryParamDecoderMatcher[String]("oauth_token")

}

/** Few quick web pages to faciltate getting the E-Trade OAuth stuff. API Based but we-redirect. Not sure what to do really */
class ETradeRoutes(etradeSvc: ETradeServices) extends ETradeRoutingUtils {



  val routes: HttpRoutes[IO] = HttpRoutes
    .of[IO] {
      case GET -> Root / "etrade" / "lobby" => Ok(etradeSvc.home())

      case GET -> Root / "etrade" / "auth" / "reception" =>
        for {
            session <- etradeSvc.sessions.accessSession(defaultUser)
             account               <- AccountsApi.listAccounts(session)

             }   yield Ok(account)


    }

  val authRoutes: HttpRoutes[IO] = HttpRoutes
    .of[IO] {

      case GET -> Root / "etrade" / "login" => etradeSvc.initialLogin()

      case GET -> Root / "etrade" / "oauth_callback" :? OAuthVerifierQPM(verifyer) +& OAuthTokenQPM(auth_token) =>
        for {
          _ <- etradeSvc.loginAccepted(verifyer)

          ok <- Ok(OAuthCallback.fullPage(verifyer, auth_token))
        } yield ok

      case req @ POST -> Root / "etrade" / "verification" =>
        for {
          verifier <- req.as[UrlForm].map(_.getFirst("verifier")) >>= IOU.required("Verifier oauth token")
          rs       <- etradeSvc.loginAccepted(verifier)
          // Setup user session and redirect to etrade/auth/reception
         _ = this.etradeSvc.
        } yield rs

      case GET -> Root / "etrade" / "oauth" / "refresh_token" => etradeSvc.renewToken()
      case GET -> Root / "etrade" / "oauth" / "cancel_token"  => etradeSvc.revokeToken()

    }

  //val clientRoutes = HttpRoutes.of[IO] {}
}
