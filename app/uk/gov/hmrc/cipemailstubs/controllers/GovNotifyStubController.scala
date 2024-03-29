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

package uk.gov.hmrc.cipemailstubs.controllers

import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.cipemailstubs.services.GovNotifyStubService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}

@Singleton()
class GovNotifyStubController @Inject()(cc: ControllerComponents, service: GovNotifyStubService)
  extends BackendController(cc) {

  def email: Action[JsValue] = Action(parse.json).async { implicit request =>
    service.email((request.body \ "email_address").as[String])
  }

  def status(notificationId: String): Action[AnyContent] = Action.async {
    service.status(notificationId)
  }
}
