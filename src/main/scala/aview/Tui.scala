package aview

import model._
import control._

class Tui (controller: Controller) {

  val player = Vector(Player("Paul", List[WhiteCard]()), Player("Niklas", List[WhiteCard]()))

  val def_blacks: List[String] = List[String]("Meine Oma mag _", "Ich bin schockiert wenn ich _ sehe",
    "Ich nutze Seife nur um _","Die Demokratie ist _"
    ,"Studium bringt mir _","Russland macht alles _","Ich bin so weil _","Dieses Spiel ist _",
    "Mario Barth kann _","Angela Merkel will _")
  val def_whites: List[String] = List[String]("Kartoffeln", "Schwarze Einhörner","Hitler töten","Menschen zu töten","Baum",
    "unheimlich sinnlos","garnichts"
    ,"super","Bombenanschläge","Wasser zu verschwenden","hugo","fawe","adsads"
    ,"adfs","gfdagads","fasfsa","fsafsa","fasfasfas","afsfsaafs")

  val cards: CardDeck = CardDeck(def_whites, def_blacks)

  def processInputLine(input: String) : Unit = {
    input match {
      case "new" => println(controller.init(cards, player))
      case "draw" => println(controller.drawCards(controller.currPlr, 2))
      case "show" => println(controller.drawBlack())
      case "q" =>
      case "print" => controller.gameTable
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case x :: Nil => if (x > 6 || x < 0) println("ungültig") else println(controller.put(x))
        case _ =>
      }
    }
  }
}
