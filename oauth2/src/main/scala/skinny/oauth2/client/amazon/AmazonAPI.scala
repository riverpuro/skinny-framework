package skinny.oauth2.client.amazon

import skinny.logging.Logging
import skinny.oauth2.client._
import skinny.util.JSONStringOps
import scala.util.control.NonFatal

/**
 * Amazon API client.
 */
trait AmazonAPI extends Logging {

  def user(token: OAuth2Token): Option[AmazonUser] = {
    try {
      val response = OAuth2Client.resource {
        BearerRequest("https://api.amazon.com/user/profile").accessToken(token.accessToken)
      }
      logger.debug(s"Amazon authorized user: ${response.body}")
      JSONStringOps.fromJSONString[AmazonUser](response.body)
    } catch {
      case NonFatal(e) =>
        logger.error(s"Failed to get current Amazon user information because ${e.getMessage}", e)
        None
    }
  }

}

object AmazonAPI extends AmazonAPI
