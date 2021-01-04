package control.commands

import control.Controller
import util.Command
import model.{Player, WhiteCard}

class cmd_addPlayer(name: String, controller: Controller) extends Command {

  override def doStep(): Unit = {
    var tmp = controller.getGameTable.player
    tmp = tmp :+ Player(name, List[WhiteCard](), isOnIt = true)
    controller.getGameTable.player = tmp
  }

  override def undoStep(): Unit = {
    var tmp = controller.getGameTable.player
    tmp = tmp.filterNot(_ == tmp.last)
    controller.getGameTable.player = tmp
  }

  override def redoStep(): Unit = doStep()
}
