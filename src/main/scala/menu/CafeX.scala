package menu

import java.util.{Currency, Locale}
import models.MenuItem
import models.Constants._

object CafeX {
  def main(args: Array[String]): Unit = {
    println("\nCafe X!\n")

    println("Menu\n")

    val menu = List(Cola, Coffee, CheeseSandwich, SteakSandwich, MillionairesBurger)

    for (item <- menu) {
      println(item.toString)
    }

    val input = scala.io.StdIn.readLine()


    println("\nAll drinks")
    val allDrinksCost = calculateCostAndServiceCharge(List(Cola, Coffee, Cola))
    println(s"Total cost - ${toPound(allDrinksCost._1)}, Service charge - ${toPound(allDrinksCost._2)}")


    println("\nCold food and drinks")
    val coldFoodAndDrinkCost = calculateCostAndServiceCharge(List(Cola, Coffee, CheeseSandwich, CheeseSandwich))
    println(s"Total cost - ${toPound(coldFoodAndDrinkCost._1)}, Service charge - ${toPound(coldFoodAndDrinkCost._2)}")


    println("\nHot food and drinks")
    val hotFoodAndDrinkCost = calculateCostAndServiceCharge(List(Cola, Coffee, CheeseSandwich, SteakSandwich, SteakSandwich))
    println(s"Total cost - ${toPound(hotFoodAndDrinkCost._1)}, Service charge - ${toPound(hotFoodAndDrinkCost._2)}")

    println("\nMillionaires Burger")
    val millionairesBurger = calculateCostAndServiceCharge(List(MillionairesBurger))
    println(s"Total cost - ${toPound(millionairesBurger._1)}, Service charge - ${toPound(millionairesBurger._2)}")

    println("\nMillionaires Burger and 3 SteakSandwiches")
    val lotsOfHotFood = calculateCostAndServiceCharge(List(MillionairesBurger, SteakSandwich, SteakSandwich, SteakSandwich))
    println(s"Total cost - ${toPound(lotsOfHotFood._1)}, Service charge - ${toPound(lotsOfHotFood._2)}")

  }

  def calculateCostAndServiceCharge(items: List[MenuItem]): (Double, Double) = {

    val totalCost = calculateTotalCost(items)
    val serviceChargePercentage = calculateServiceChargePercentage(items)

    val serviceCharge = if (serviceChargePercentage > 0) totalCost / 100 * serviceChargePercentage else 0

    (totalCost, if (serviceCharge > MaximumCharge) MaximumCharge else serviceCharge)
  }

  def calculateTotalCost(items: List[MenuItem]): Double = {
    if (items.isEmpty) 0
    else items.head.price + calculateTotalCost(items.tail)
  }

  def calculateServiceChargePercentage(items: List[MenuItem]): Int = {
    def serviceChargePercentage(items: List[MenuItem], serviceCharge: Int): Int = {
      items match {
        case Nil => serviceCharge
        case x :: tail =>
          val newServiceCharge = if (!x.drink && x.`type`.equals("Hot")) 20 else if (!x.drink && (10 > serviceCharge)) 10 else serviceCharge
          serviceChargePercentage(tail, newServiceCharge)
      }
    }

    serviceChargePercentage(items, 0)
  }

  def toPound(price: Double): String = {
    val formatter = java.text.NumberFormat.getCurrencyInstance
    val pound = Currency.getInstance(new Locale("en", "GB"))
    formatter.setCurrency(pound)
    formatter.format(price)
  }
}
