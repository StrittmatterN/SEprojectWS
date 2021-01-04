package aview

import control._

import scala.swing.Publisher

class Tui(controller: ControllerInterface) extends Publisher {
  def processInput(input: String): Unit = {
    input match {
      case "quit" =>
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case _ => controller.evaluate(input)
    }
  }
  def update(): Unit = {
    println(controller.stateToString())
    println(controller.getGameTable.printGT())
  }

  listenTo(controller)
  reactions += {
    case _: UpdateTuiEvent => update()
  }
}