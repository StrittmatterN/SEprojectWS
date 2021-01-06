package aview

import control.{ControllerInterface, ThirdPageEvent}

import scala.swing._
import scala.swing.event.ButtonClicked

class B_AddCardsView(infotextbar: Infotextbar, controller: ControllerInterface) extends Dialog {
  preferredSize = new Dimension(400, 100)
  title = "Do you want to add new cards?"
  val cardTF = new TextField("your Card text")
  val addCardButton = new Button("Add")
  val continueButton = new Button("Continue")
  val undoButton = new Button("Undo")

  val buttonView: FlowPanel = new FlowPanel() {
    contents += addCardButton
    contents += continueButton
    contents += undoButton
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += cardTF
    contents += buttonView
  }

  listenTo(controller)
  listenTo(addCardButton)
  listenTo(continueButton)
  listenTo(undoButton)

  reactions += {
    case ButtonClicked(x) if x == undoButton =>
      controller.undo()
      cardTF.text = "your card text"
    case ButtonClicked(x) if x == continueButton =>
      this.close()
      controller.save()
      controller.evaluate("continue")
    case ButtonClicked(x) if x == addCardButton =>
      controller.evaluate(cardTF.text)
      cardTF.text = "your next card text"
    case _: ThirdPageEvent =>
      this.close()
      infotextbar.text = "enter player names"
  }
}
