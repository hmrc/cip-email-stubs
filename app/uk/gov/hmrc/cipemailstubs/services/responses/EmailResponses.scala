/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.cipemailstubs.services.responses

object EmailResponses extends CommonResponses {

  val notificationResponse =
    """
      |{
      |  "id": "ecf20f0a-86af-4ebf-9012-e48bc6a31174",
      |  "reference": "1234",
      |  "content": {
      |    "subject": "CIP Email Verification Service",
      |    "body": "cip-phone-service needs to verify your email.\nYour email verification code is 123456.\nUse this code within 1 minutes to verify your email.",
      |    "from_email": "GOVUK"
      |  },
      |  "uri": "https://api.notifications.service.gov.uk/v2/notifications/ecf20f0a-86af-4ebf-9012-e48bc6a31174",
      |  "template": {
      |    "id": "dce5ac8a-0970-41a0-b993-bde1beab5825",
      |    "version": 6,
      |    "uri": "https://api.notifications.service.gov.uk/services/f6a3a40d-29b2-46f5-bcd1-36a4f6837171/templates/dce5ac8a-0970-41a0-b993-bde1beab5825"
      |  }
      |}
    """.stripMargin

  val badRequestTeamOnlyError =
    """
      |{
      |  "errors": [
      |    {
      |      "error": "BadRequestError",
      |      "message": "Can't send to this recipient using a team-only API key"
      |    }
      |  ],
      |  "status_code": 400
      |}
    """.stripMargin

  val badRequestTrialModeOnlyError =
    """
      |{
      |  "errors": [
      |    {
      |      "error": "BadRequestError",
      |      "message": "Can't send to this recipient when service is in trial mode - see https://www.notifications.service.gov.uk/trial-mode"
      |    }
      |  ],
      |  "status_code": 400
      |}
    """.stripMargin

  val rateLimitError =
    """
      |{
      |  "errors": [
      |    {
      |      "error": "RateLimitError",
      |      "message": "Exceeded rate limit for key type TEAM/TEST/LIVE of 3000 requests per 60 seconds"
      |    }
      |  ],
      |  "status_code": 429
      |}
    """.stripMargin

  val tooManyRequestsError =
    """
      |{
      |  "errors": [
      |    {
      |      "error": "TooManyRequestsError",
      |      "message": "Exceeded send limits (LIMIT NUMBER) for today"
      |    }
      |  ],
      |  "status_code": 429
      |}
    """.stripMargin

  val exception =
    """
      |{
      |  "errors": [
      |    {
      |      "error": "Exception",
      |      "message": "Internal server error"
      |    }
      |  ],
      |  "status_code": 500
      |}
    """.stripMargin

}
