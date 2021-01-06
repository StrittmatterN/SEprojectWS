package control

import model.GameTable

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {
  def undo()
  def redo()
  def load()
  def save()
  def getGameTable: GameTable

  def nextState()
  def setPage(page: Int)
  def evaluate(input: String)
  def stateToString(): String
}

class FirstPageEvent extends Event
class SecondPageEvent extends Event
class ThirdPageEvent extends Event
class NextStateEvent extends Event
class UpdateTuiEvent extends Event
class UndoEvent extends Event
class UpdateInfotextbarEvent extends Event
class UpdateGuiEvent extends Event