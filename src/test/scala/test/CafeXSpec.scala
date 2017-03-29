/*
* Copyright 2017 HM Revenue & Customs
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
package test

import models.MenuItem
import org.scalatest.FlatSpec
import org.scalatest._
import menu.CafeX._
import models.Constants._

class CafeXSpec extends FlatSpec {

  "A menu item" should "print correctly" in {
    assert(MenuItem("Beer", "Cold", true, 5.0).toString == "- Beer - Cold - £5.00")
  }

  "An order of drinks" should "not include a service charge" in {
    assert(calculateCostAndServiceCharge(List(Coffee, Cola))._2 == 0)
  }

  "An empty list" should "not include a cost" in {
    assert(calculateCostAndServiceCharge(List())._1 == 0)
    assert(calculateCostAndServiceCharge(List())._2 == 0)
  }

  "An order of cold food and drinks" should "include a service charge of 10 percent of the total cost" in {

    val costs = calculateCostAndServiceCharge(List(CheeseSandwich, CheeseSandwich, Coffee, Cola))

    assert((costs._1 / 100 * 10) == costs._2)
  }

  "An order of cold food" should "include a service charge of 10 percent of the total cost" in {

    val costs = calculateCostAndServiceCharge(List(CheeseSandwich, CheeseSandwich, CheeseSandwich, CheeseSandwich))

    assert((costs._1 / 100 * 10) == costs._2)
  }

  "An order of hot food and drink" should "include a service charge of 20 percent of the total cost" in {

    val costs = calculateCostAndServiceCharge(List(Coffee, SteakSandwich, Cola, SteakSandwich))

    assert((costs._1 / 100 * 20) == costs._2)
  }

  "An order of hot food" should "have a max service charge of £20" in {

    val costs = calculateCostAndServiceCharge(List(MillionairesBurger, MillionairesBurger))

    assert(costs._2 <= 20.00)
  }


}
