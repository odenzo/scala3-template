package com.odenzo.accounting.desktopweb.html

import cats.effect.IO
import com.odenzo.etrade.api.AccountsApi
import com.odenzo.etrade.auth.ETradeSession
import com.odenzo.etrade.models.Account
import scalatags.Text
import scalatags.Text.all._

object OAuthCallback {

  def fullPage(verify: String, oauthToken: String): Text.TypedTag[String] = {

    html(
      head(title := "E-Trade Login Succeeded"),
      body(LoginPage.loginHeader(), h1("e-trade Main Menu"), p("Verify Token: ", span(verify)), p("oauth_token: ", span(oauthToken)))
    )
  }

}
