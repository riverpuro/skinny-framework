package skinny.oauth2.client.amazon

import skinny.oauth2.client.OAuth2User

/**
 * Authorized Amazon user basic information.
 */
case class AmazonUser(
  override val id: String) extends OAuth2User
