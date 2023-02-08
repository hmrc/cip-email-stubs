/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.cipemailstubs.services

import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.{BadRequest, Created, Forbidden, InternalServerError, NotFound, Ok, TooManyRequests}
import uk.gov.hmrc.cipemailstubs.services.responses.{EmailResponses, StatusResponses}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class GovNotifyStubService @Inject()(implicit val executionContext: ExecutionContext) {

  def email(email: String): Future[Result] = Future {
    email match {
      case "teamonly@something.com" => BadRequest(Json.parse(EmailResponses.badRequestTeamOnlyError))
      case "trialmode@something.com" => BadRequest(Json.parse(EmailResponses.badRequestTrialModeOnlyError))
      case "systemclock@something.com" => Forbidden(Json.parse(EmailResponses.authErrorSystemClockError))
      case "invalidtoken@something.com" => Forbidden(Json.parse(EmailResponses.authErrorInvalidTokenError))
      case "ratelimit@something.com" => TooManyRequests(Json.parse(EmailResponses.rateLimitError))
      case "toomanyrequests@something.com" => TooManyRequests(Json.parse(EmailResponses.tooManyRequestsError))
      case "exception@something.com" => InternalServerError(Json.parse(EmailResponses.exception))
      case _ => Created(Json.parse(EmailResponses.notificationResponse))
    }
  }

  def status(notificationId: String): Future[Result] = Future {
    notificationId match {
      case "validation-d385-4b17-a0b4-23a85c0c5b1a" => BadRequest(Json.parse(StatusResponses.validationError))
      case "invalidtoken-d385-4b17-a0b4-23a85c0c5b1a" => Forbidden(Json.parse(StatusResponses.authErrorInvalidTokenError))
      case "systemclock-d385-4b17-a0b4-23a85c0c5b1a" => Forbidden(Json.parse(StatusResponses.authErrorSystemClockError))
      case "noresult-d385-4b17-a0b4-23a85c0c5b1a" => NotFound(Json.parse(StatusResponses.noResultFoundError))
      case _ => Ok(Json.parse(parseNotificationResponse(notificationId)))
    }
  }

  def parseNotificationResponse(notificationId: String): String = {
    notificationId match {
      case "16770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.permanentFailure
      case "26770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.technicalFailure
      case "36770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.temporaryFailure
      case "46770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.created
      case "56770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.sending
      case "66770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.pending
      case "76770ea0-d385-4b17-a0b4-23a85c0c5b1a" => StatusResponses.sent
      case _ => StatusResponses.delivered
    }
  }
}
