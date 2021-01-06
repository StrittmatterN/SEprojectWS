package aview

import java.awt.Color

import control._
import model.WhiteCard

import scala.swing.BorderPanel.Position
import scala.swing._
import scala.swing.event.ButtonClicked

class D_GameTablePage(infotextbar: Infotextbar, controller: ControllerInterface) extends BorderPanel {
  preferredSize = new Dimension(500, 500)
  background = Color.WHITE
  foreground = Color.BLACK
  val placeCardButton = new Button("Place card")
  val newRoundButton = new Button("New round")
  val currRoundLbl = new Label(s"Round ${controller.getGameTable.currRound} of ${controller.getGameTable.nrOfRounds}")
  val fillLb = new Label(" ")
  val currBlackLbl = new Label(s"${controller.getGameTable.currBlack}")
  infotextbar.text = s"${controller.getGameTable.player(controller.getGameTable.currPlr).name} begins!"

  var availablePlayerCards =
    new ComboBox[WhiteCard](controller.getGameTable.player(controller.getGameTable.currPlr).cards)
  var placedWhites = new ListView[String]()

  val currRoundPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    background = Color.WHITE
    foreground = Color.BLACK
    contents += currRoundLbl
    contents += fillLb
    contents += currBlackLbl
  }
  val availableWhitesPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    background = Color.WHITE
    foreground = Color.BLACK
    contents += placedWhites

  }
  val showPlacedWhiteCardsPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    background = Color.WHITE
    foreground = Color.BLACK
    contents += availablePlayerCards
  }
  val buttonPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    background = Color.WHITE
    foreground = Color.BLACK
    contents += placeCardButton
    contents += newRoundButton
  }


  val gridPanel: GridPanel = new GridPanel(2, 2) {
    contents += currRoundPanel
    contents += showPlacedWhiteCardsPanel
    contents += availableWhitesPanel
    contents += buttonPanel
  }

  add(gridPanel, Position.Center)

  listenTo(controller)
  listenTo(placeCardButton)
  listenTo(newRoundButton)

  reactions += {
    case _: UpdateInfotextbarEvent =>
      infotextbar.text = s"Current player: ${controller.getGameTable.player(controller.getGameTable.currPlr).name}"
    case _: UpdateGuiEvent =>
      currRoundLbl.text = s"Round ${controller.getGameTable.currRound} of ${controller.getGameTable.nrOfRounds - 1}"
      currBlackLbl.text = s"${controller.getGameTable.currBlack}"
      var displayedWhites = List[String]()
      for (x <- controller.getGameTable.placedWhiteCards) {
        displayedWhites = displayedWhites :+ s"Player ${x._1.name} put: ${x._2} "
      }
      placedWhites = new ListView[String](displayedWhites)
      placedWhites.revalidate()
      placedWhites.repaint()

      availablePlayerCards =
        new ComboBox[WhiteCard](controller.getGameTable.player(controller.getGameTable.getCurrPlr).cards)
      availablePlayerCards.revalidate()
      showPlacedWhiteCardsPanel.contents.update(0, availablePlayerCards)
      availableWhitesPanel.revalidate()
      availableWhitesPanel.contents.update(0, placedWhites)

      repaint()
    case ButtonClicked(x) if x == placeCardButton =>
      if (availablePlayerCards.selection.index > -1) {
        controller.evaluate(availablePlayerCards.selection.index.toString)
        publish(new UpdateInfotextbarEvent)
      }
    case ButtonClicked(x) if x == newRoundButton =>
      controller.evaluate("")
  }


}
