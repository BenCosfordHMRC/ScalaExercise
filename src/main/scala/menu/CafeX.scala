package menu

import java.util.{Currency, Locale}
import models.MenuItem
import models.Constants._

object CafeX {
  def main(args: Array[String]): Unit ={
    println("\nCafe X!\n")

    println("Menu\n")

    val menu = List(Cola, Coffee, CheeseSandwich, SteakSandwich)

    for(item <- menu){
      println(item.toString)
    }

    val input = scala.io.StdIn.readLine()


    println("\nAll drinks")
    val allDrinksCost = calculateCostAndServiceCharge(List(Cola,Coffee,Cola))
    println(s"Total cost - ${toPound(allDrinksCost._1)}, Service charge - ${toPound(allDrinksCost._2)}")


    println("\nCold food and drinks")
    val coldFoodAndDrinkCost = calculateCostAndServiceCharge(List(Cola,Coffee,CheeseSandwich,CheeseSandwich))
    println(s"Total cost - ${toPound(coldFoodAndDrinkCost._1)}, Service charge - ${toPound(coldFoodAndDrinkCost._2)}")


    println("\nHot food and drinks")
    val hotFoodAndDrinkCost = calculateCostAndServiceCharge(List(Cola,Coffee,CheeseSandwich,SteakSandwich,SteakSandwich))
    println(s"Total cost - ${toPound(hotFoodAndDrinkCost._1)}, Service charge - ${toPound(hotFoodAndDrinkCost._2)}")
  }

  def calculateCostAndServiceCharge(items: List[MenuItem]): (Double,Double) ={

    var serviceCharge = 0.0
    var totalCost = 0.0
    var applyServiceCharge = false
    var apply20Charge = false

    for(item <- items){
      totalCost = totalCost + item.price

      if(item.drink.equals(false)){
        if(item.`type`.equals("Hot")){
          /** If there is hot food **/
          apply20Charge = true
        }
        /** If there is cold food **/
        applyServiceCharge = true
      }
    }

    if(applyServiceCharge && !apply20Charge){
      /** If there is only cold food **/
      serviceCharge = (totalCost / 100) * 10
    } else if(apply20Charge){
      serviceCharge = (totalCost / 100) * 20
    }

    /** Returns total cost and service charge (max of £20)**/
    (totalCost, if(serviceCharge > MaximumCharge){MaximumCharge} else serviceCharge)
  }

  def toPound(price: Double): String ={
    val formatter = java.text.NumberFormat.getCurrencyInstance
    val pound = Currency.getInstance(new Locale("en", "GB"))
    formatter.setCurrency(pound)
    formatter.format(price)
  }
}
