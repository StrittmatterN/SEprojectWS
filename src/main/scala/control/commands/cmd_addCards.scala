package control.commands

import control.Controller
import util.Command
import model.{BlackCard, WhiteCard}

class cmd_addCards(text: String, controller: Controller) extends Command {
  var undoWhites: List[WhiteCard] = List[WhiteCard]()
  var undoBlacks: List[BlackCard] = List[BlackCard]()

  override def doStep(): Unit = {
    if (!text.contains("_")) {
      var tmp = controller.gameTable.whiteCards
      undoWhites = tmp
      tmp = tmp :+ WhiteCard(text)
      controller.gameTable.whiteCards = tmp
    } else {
      var tmp = controller.gameTable.blackCards
      undoBlacks = tmp
      tmp = tmp :+ BlackCard(text)
      controller.gameTable.blackCards = tmp
    }
  }

  override def undoStep(): Unit = {
    if(text.contains("_")) controller.gameTable.blackCards = undoBlacks
    else controller.gameTable.whiteCards = undoWhites
  }

  override def redoStep(): Unit = doStep()
}
