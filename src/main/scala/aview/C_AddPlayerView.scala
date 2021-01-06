package aview

import control.{ControllerInterface, NextStateEvent}

import scala.swing._
import scala.swing.event.ButtonClicked

class C_AddPlayerView(toolbar: Infotextbar, controller: ControllerInterface) extends Dialog {
  preferredSize = new Dimension(400, 100)
  title = "Enter player names"
  val inputTF = new TextField("Enter player name")
  val continueButton = new Button("Continue")
  val undoButton = new Button("Undo")
  val addedPlrInfo: TextField = new TextField("") {
    editable = false
  }

  contents = new BoxPanel(Orientation.Horizontal) {
    contents += inputTF
    contents += new FlowPanel() {
      contents += continueButton
      contents += undoButton
    }
    contents += toolbar
  }
  listenTo(controller)
  listenTo(continueButton)
  listenTo(undoButton)

  reactions += {
    case ButtonClicked(x) if x == continueButton =>
      if (inputTF.text == "Enter player name" || inputTF.text == "Enter next player name") {
        inputTF.validate()
      } else {
        controller.evaluate(inputTF.text)
        inputTF.text = "Enter next player name"
      }
    case ButtonClicked(x) if x == undoButton =>
      inputTF.text = "Enter player name"
      controller.undo()
    case _: NextStateEvent =>
      controller.evaluate("")
      this.close()
  }
}
