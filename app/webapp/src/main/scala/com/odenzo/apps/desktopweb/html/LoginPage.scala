package com.odenzo.accounting.desktopweb.html

import scalatags.Text
import scalatags.Text.all._

object LoginPage {

  def fullLoginPage(): Text.TypedTag[String] = {
    import scalatags._
    import scalatags.Text._
    import scalatags.Text.all._

    html(
      head(title := "E-Trade Control Center"),
      body(
        h1("e-trade login page"),
        p("Login With Callback:", a("CLICK_HERE", href := "/etrade/login")),
        p("Login w/ CopyPaste:", a("NEW WINDOW", href := "/etrade/login", target := "_new")),
        p("Once you have the token, please enter it below:"),
        div(
          `class` := "form",
          p("Enter the token: "),
          form(
            action := "/etrade/verification",
            method := "POST",
            id     := "verifyForm",
            label(`for` := "verifier", "Enter The Verification Code from ETrade"),
            input(name  := "verifier", id   := "verifier", `type` := "text", width := "10em"),
            input(value := "Submit", `type` := "submit")
          )
        ),
        div(p(a("REFRESH TOKEN", href := "/etrade/oauth/refresh_token")), p(a("LOGOUT", href := "/etrade/oauth/cancel_token")))
      )
    )
  }

  def loginHeader(): Text.TypedTag[String] = div(backgroundColor := "yellow", p(span("LOGOUT"), span("RENEW TOKEN")))

}
