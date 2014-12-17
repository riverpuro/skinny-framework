package controller

import skinny.controller.feature.AmazonLoginFeature
import skinny.oauth2.client.OAuth2User
import skinny.oauth2.client.amazon.AmazonUser

class AmazonController extends ApplicationController with AmazonLoginFeature {

  override def redirectURI = "http://localhost:8080/example/amazon/callback"

  override protected def saveAuthorizedUser(user: AmazonUser): Unit = {
    session.setAttribute("user", user)
  }

  override protected def handleWhenLoginSucceeded(): Any = {
    redirect302(url(Controllers.amazon.okUrl))
  }

  def ok = {
    set("user", session.getAs[OAuth2User]("user"))
    set("amazon", session.getAs[AmazonUser]("user"))
    render("/amazon/ok")
  }

}
