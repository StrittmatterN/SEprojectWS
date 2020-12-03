package aview

import model._
import control._
import util.Observer

class Tui (controller: Controller) extends  Observer {

  controller.add(this)
  val player = Vector(Player("Paul", List[WhiteCard]()), Player("Niklas", List[WhiteCard]()))

  val def_blacks: List[String] = List[String]("_ + _ = _",
    "I’m going on a cleanse this week. Nothing but kale juice and _.", "_. Its a trap", "_. That was so metal.",
    "During sex, I like to think about _.", "What's that sound?", "Why am I sticky?", "Next on ESPN2: The world" +
      "series of _", "What's that smell?")
  val def_whites: List[String] = List[String]("Silence.", "72 virgins", "Daddy issues.", "BATMAN",
    "Used panties.", "Former President George W. Bush.", "Doing crimes.", "Peeing a little bit.",
    "An erection that lasts longer than four hours.", "Bananas.", "The Hustle.", "However much weed $20 can buy.",
    "White people.", "Itchy pussy.", "Being able to talk to elephants.", "Drinking alone.")

  val cards: CardDeck = CardDeck(def_whites, def_blacks)

  def processInputLine(input: String) : Unit = {
    input match {
      case "z" =>
        controller.undo
        println(controller.gameTable)
      case "y" =>
        controller.redo
        println(controller.gameTable)
      case "new" =>
        controller.init(cards, player)
        println(controller.gameTable)
      case "draw" =>
        controller.drawCards(controller.currPlr, 3)
        println(controller.gameTable)
      case "show" =>
        controller.drawBlack()
        println(controller.gameTable)
      case "next" =>
        controller.currPlr = (controller.currPlr + 1) % player.length
        println(controller.gameTable)
      case "q" =>
      case "print" => println(controller.gameTable.toString)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case x :: Nil => if (x > 6 || x < 0) println("ungültig") else {
          controller.put(x)
          println(controller.gameTable)
        }
        case _ =>
      }
    }
  }

  override def update(): Unit = {
    println(controller.gameTable)
  }
}
