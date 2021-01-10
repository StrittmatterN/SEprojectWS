package aview

import java.awt.{Color, Toolkit}

import control.ControllerInterface

import scala.swing._
import control._
import javax.swing.UIManager
import javax.swing.plaf.basic.BasicLookAndFeel

class Gui (controller: ControllerInterface) extends Frame {
  title = "Cards Against Humanity"
  val infotextbar = new Infotextbar
  val firstPage = new A_StartView(infotextbar, controller)
  val addPlrPage = new C_AddPlayerView(infotextbar, controller)
  val windowSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val mainPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += firstPage
    contents += infotextbar
  }
  preferredSize = new Dimension(1000, 500)
  background = Color.WHITE
  peer.setUndecorated(false)
  peer.setResizable(true)
  peer.setDefaultCloseOperation(3)
  this.contents = mainPanel

  def setNextPage(page: Int): Unit = {
    page match {
      case 1 =>
        mainPanel.contents.update(0, firstPage)
        this.validate()
      case 2 =>
        val addCardsPage = new aview.B_AddCardsView(infotextbar, controller)
        addCardsPage.open()
      case 3 => addPlrPage.open()
      case 4 =>
        val gameTablePage = new aview.D_GameTablePage(infotextbar, controller)
        mainPanel.contents.update(0, gameTablePage)
        this.validate()
    }
  }
  listenTo(controller)
  reactions += {
    case _: FirstPageEvent => setNextPage(1)
    case _: SecondPageEvent => setNextPage(2)
    case _: ThirdPageEvent => setNextPage(3)
    case _: NextStateEvent => setNextPage(4)
  }
}

class Infotextbar extends TextField {
  horizontalAlignment = Alignment.Center
  editable = false
  background = Color.DARK_GRAY
  foreground = Color.WHITE
}
