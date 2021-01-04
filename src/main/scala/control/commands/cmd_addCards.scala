package control.commands

import control.Controller
import util.Command
import model.{CardDeck, WhiteCard, BlackCard}

class cmd_addCards(text: String, controller: Controller) extends Command {

  var undoDeck: CardDeck = controller.getGameTable.cardDeck

  override def doStep(): Unit = {
    var tmpCards = controller.getGameTable.cardDeck
    undoDeck = tmpCards
    var notContains = true
    if (text.contains("_")) {
      for (x <- tmpCards.blacks) {
        if (x.equals(text)) notContains = false
      }
    } else {
      for (x <- tmpCards.whites) {
        if (x.equals(text)) notContains = false
      }
    }
    if (notContains) {
      if (text.contains("_")) tmpCards.blacks :+ BlackCard(text)
      else tmpCards.whites :+ WhiteCard(text)
    }
    val newCardDeck = CardDeck(tmpCards.whites, tmpCards.blacks)
    controller.getGameTable.setCardDeck(newCardDeck)
  }

  override def undoStep(): Unit = {
    controller.getGameTable.setCardDeck(undoDeck)
  }

  override def redoStep(): Unit = doStep()
}
