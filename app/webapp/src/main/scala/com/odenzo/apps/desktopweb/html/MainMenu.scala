package com.odenzo.accounting.desktopweb.html

import cats.effect.IO
import com.odenzo.etrade.api.AccountsApi
import com.odenzo.etrade.auth.ETradeSession
import com.odenzo.etrade.models.Account
import scalatags.Text
import scalatags.Text.all._

object MainMenu {

  def fullPage(message: List[String], session: ETradeSession): Text.TypedTag[String] = {
    val accounts: IO[Account] = AccountsApi.listAccounts(session)
    html(head(title := "E-Trade Menu"), body(LoginPage.loginHeader(), h1("e-trade Main Menu"), p("Hi There")))
  }

}
