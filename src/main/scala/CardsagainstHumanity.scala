import model.{BlackCards, GameTable, Player, WhiteCards}
import aview.Tui

import scala.io.StdIn.readLine

object CardsagainstHumanity {
  def main(args: Array[String]): Unit = {

    val tui = new Tui
    var input: String = "0"
    val gameTable = new GameTable(3)

    if (args.length>0) input=args(0)
    else do{
      input = readLine()

      tui.processInputLine(input, gameTable)
    }while(input != "q")
  }

}

