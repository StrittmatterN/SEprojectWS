package aview

import java.awt.{Color, Toolkit}

import scala.swing._
import scala.swing.{BorderPanel, Button, ComboBox, Dimension, FlowPanel}
import control.ControllerInterface
import scala.swing.event.ButtonClicked

class A_StartView(infotextbar: Infotextbar, controller: ControllerInterface) extends BorderPanel {
  val startButton: Button = new Button("start") {

    preferredSize = new Dimension(100, 50)
    foreground = Color.BLACK
    background = Color.WHITE
    font = Font("System", Font.Italic, 30)
  }
  val windowSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val lbl1 = new Label("player     ")
  val nrOfPlrCB = new ComboBox(List(2, 3, 4, 5, 6))
  listenTo(controller)

  def mainPanel: FlowPanel = new FlowPanel() {
    background = Color.WHITE
    contents += new FlowPanel() {
      background = Color.WHITE
      foreground = Color.BLACK
      preferredSize = new Dimension(300, 150)
      contents += nrOfPlrCB
      contents += lbl1
      contents += startButton
    }
  }

  add(mainPanel, BorderPanel.Position.South)
  infotextbar.text = "select number of player"
  preferredSize = new Dimension(200, 400)

  listenTo(startButton)

  reactions += {
    case ButtonClicked(x) if x == startButton =>
      if (nrOfPlrCB.item.isInstanceOf[Int]) {
        controller.evaluate(nrOfPlrCB.item.toString)
        infotextbar.text = s"game starts with ${nrOfPlrCB.item} player"
      }
  }
}
