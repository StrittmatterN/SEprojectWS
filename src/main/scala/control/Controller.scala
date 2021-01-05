package control

import model.{GameTable, ModelInterface}
import control.commands._
import module.Module
import util._
import util.fileio.IOFile
import com.google.inject._

import scala.swing.Publisher

class Controller @Inject() (var gameTable: ModelInterface) extends ControllerInterface with Publisher {

  trait ControllerState {
    def evaluate(input: String)
    def getCurrState: String
    def getNextState: ControllerState
  }
  var state: ControllerState = setupState1(this)
  val injector: Injector = Guice.createInjector(new Module)
  val fileManager: IOFile = injector.getInstance(classOf[IOFile])
  val undoManager = new UndoManager

  // input -> nr of plr
  case class setupState1 (controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      controller.gameTable = controller.gameTable.initGame(input.toInt)
      controller.gameTable = controller.fileManager.load(controller.gameTable)
      controller.setPage(2)
      controller.publish(new UpdateTuiEvent)
      controller.nextState()
    }

    override def getCurrState: String = "Cards against Humanity by Niklas and Paul\n"

    override def getNextState: ControllerState = setupState2(controller)
  }

  // input -> Card text
  case class setupState2(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      if (input.equals("continue")) {
        controller.nextState()
        controller.publish(new UpdateTuiEvent)
        controller.publish(new ThirdPageEvent)
      } else {
        controller.undoManager.doStep(new cmd_addCards(input, controller))
        controller.publish(new UpdateToolBarEvent)
        controller.publish(new UpdateTuiEvent)
      }
    }

    override def getCurrState: String = "state: adding cards\n"

    override def getNextState: ControllerState = setupState3(controller)
  }

  // input -> names of all players
  case class setupState3(controller: Controller) extends ControllerState {
    println("name all players")
    override def evaluate(input: String): Unit = {
      if (input.isEmpty) return
      controller.undoManager.doStep(new cmd_addPlayer(input, controller))
      controller.publish(new UpdateTuiEvent)

      if (controller.getGameTable.player.size == controller.getGameTable.nrOfPlrs) {
        controller.gameTable = controller.gameTable.createDeck(controller.gameTable.getDeck)
        controller.gameTable = controller.gameTable.handOutCards()
        controller.nextState()
        controller.publish(new NextStateEvent)
      }
    }

    override def getCurrState: String = "state: adding players and handout cards"

    override def getNextState: ControllerState = setWhiteCardState(controller)
  }

  // input -> none = set new round -> idx of cards of curr plr
  case class setWhiteCardState(controller: Controller) extends ControllerState {
    println("input: none for clearing the round or index of the players white card")
    override def evaluate(input: String): Unit = {
      if (input.isEmpty
        || controller.getGameTable.placedWhiteCards.size.equals(controller.getGameTable.player.size)) {
        controller.gameTable = controller.gameTable.clearRound()
        controller.gameTable = controller.gameTable.showBlackCard()
        controller.publish(new UpdateToolBarEvent)
        controller.publish(new UpdateTuiEvent)
      } else {
        if (controller.getGameTable.placedWhiteCards.size.equals(controller.getGameTable.player.length)) {
          controller.gameTable = controller.gameTable.drawWhiteCard()
          controller.gameTable = controller.gameTable.handOutCards()
          controller.publish(new UpdateTuiEvent)
          controller.nextState()
        }
        val currentPlayer = controller.getGameTable.getCurrPlr
        if (input.toInt < controller.getGameTable.player(currentPlayer).cards.length || input.toInt >= 0) {
          controller.gameTable =
            controller.gameTable
              .setPlrAnswer(currentPlayer, controller.getGameTable.player(currentPlayer).cards(input.toInt))
          controller.gameTable = controller.gameTable.setNextPlr()
          controller.publish(new UpdateTuiEvent)
        }
      }
      if (controller.getGameTable.currRound >= controller.getGameTable.nrOfRounds) {
        controller.nextState()
      }
    }

    override def getCurrState: String = controller.getGameTable.currBlack

    override def getNextState: ControllerState = {
      if (controller.getGameTable.currRound >= controller.getGameTable.nrOfRounds) {
        publish(new UpdateTuiEvent)
        finalState(controller)
      } else this
    }
  }

  case class finalState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      println("Thanks for playing Cards Against Humanity by Niklas and Paul")
      System.exit(0)
    }

    override def getCurrState: String = "exit with q"

    override def getNextState: ControllerState = this
  }

  override def undo(): Unit = {
    undoManager.undoStep()
    publish(new UndoEvent)
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    publish(new UndoEvent)
  }

  override def load(): Unit = gameTable = fileManager.load(gameTable)

  override def save(): Unit = fileManager.save(gameTable)

  override def getGameTable: GameTable = gameTable.getGT

  override def nextState(): Unit = state = state.getNextState

  override def setPage(page: Int): Unit = {
    page match {
      case 1 => publish(new FirstPageEvent)
      case 2 => publish(new SecondPageEvent)
      case 3 => publish(new ThirdPageEvent)
    }
  }

  override def evaluate(input: String): Unit = state.evaluate(input)

  override def stateToString(): String = {
    state match {
          case _: setupState1 => "setup state part 1"
          case _: setupState2 => "setup state part 2"
          case _: setupState3 => "setup state part 3"
          case _: setWhiteCardState => "set white cards state"
          case _: finalState => "final state"
    }
  }
}
