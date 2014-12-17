package skinny.controller.feature

import skinny.oauth2.client._
import skinny.oauth2.client.amazon.{ AmazonAPI, AmazonUser }

/**
 * Amazon OAuth2 Login Controller.
 *
 * {{{
 * export SKINNY_OAUTH2_CLIENT_ID_AMAZON=xxx
 * export SKINNY_OAUTH2_CLIENT_SECRET_AMAZON=yyy
 * }}}
 */
trait AmazonLoginFeature extends OAuth2LoginFeature[AmazonUser] {

  override protected def provider = OAuth2Provider.Amazon

  override protected def createAuthenticationRequest(): AuthenticationRequest = {
    val req = AuthenticationRequest(provider)
      .clientId(clientId)
      .responseType(ResponseType.Code)
      .state(state)
      .scope("profile")
      .redirectURI(redirectURI)
    if (scope != null) req.scope(scope) else req
  }

  override protected def retrieveAuthorizedUser(token: OAuth2Token): AmazonUser = {
    AmazonAPI.user(token).getOrElse {
      handleWhenLoginFailed()
      haltWithBody(401)
    }
  }

}
