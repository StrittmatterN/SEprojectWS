import aview.Tui
import control.Controller
import model.{BlackCard, GameTable, Player, WhiteCard}
import scala.io.StdIn.readLine

object CardsagainstHumanity {

  def main(args: Array[String]): Unit = {

    val controller = new Controller(GameTable(null, null, null, null, null, null, 0))
    val tui = new Tui(controller)
    controller.notifyObservers()

    var input = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")

  }
}
