package aview

import java.awt.{Color, Toolkit}

import scala.swing._
import scala.swing.{BorderPanel, Button, ComboBox, Dimension, FlowPanel}
import control.ControllerInterface
import javax.swing.ImageIcon

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
      preferredSize = new Dimension(1000, 60)
      contents += nrOfPlrCB
      contents += lbl1
      contents += startButton
    }
  }

  def titlePanel: FlowPanel = new FlowPanel() {
    background = Color.BLACK
    preferredSize = new Dimension(1000, 400)
    private val titlePic = new ImageIcon("src/main/scala/aview/Z_Cards_Against_Humanity_logo.png").getImage
    private val titlePic500 = titlePic.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH)
    contents += new Label {
      icon = new ImageIcon(titlePic500)
    }
  }

  add(mainPanel, BorderPanel.Position.South)
  add(titlePanel, BorderPanel.Position.North)
  infotextbar.text = "select number of player"
  preferredSize = new Dimension(1000, 400)

  listenTo(startButton)

  reactions += {
    case ButtonClicked(x) if x == startButton =>
      if (nrOfPlrCB.item.isInstanceOf[Int]) {
        controller.evaluate(nrOfPlrCB.item.toString)
        infotextbar.text = s"game starts with ${nrOfPlrCB.item} player"
      }
  }
}
