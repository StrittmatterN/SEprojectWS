import control.Controller
import model.GameTable
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import util.Observer

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    val controller = new Controller(new GameTable)
    val observer: Observer = new Observer {
      var updated = false
      override def update(): Unit = updated = true
    }
    "notifys the observer" in {
      controller.state.getCurrState shouldBe "setup state part 1"
      "sets the nr of players" in {
        controller.evaluate("2")
        controller.gameTable.getGT.nrOfPlrs should be (2)
      }
      controller.state.getCurrState shouldBe "setup state part 2"
    }

    "adds cards" in {
      controller.evaluate("Mama")
      controller.evaluate("continue")
      controller.state.getCurrState shouldBe "setup state part 3"
    }

    "adds players" in {
      controller.evaluate("Niklas")
      controller.evaluate("Paul")
      controller.state.getCurrState shouldBe "set white cards state"
    }

    "set a white card from the current player" in {
      "is first player" in {
        controller.gameTable.getCurrPlr should be (0)
        controller.gameTable.getGT.player(controller.gameTable.getCurrPlr).name shouldBe "Niklas"
      }
      "places the first card of the curr plr" in {
        controller.evaluate("0")
        controller.gameTable.getGT.placedWhiteCards.size should be (1)
      }
      "automatically set the next player" in {
        controller.gameTable.getGT.player(controller.gameTable.getCurrPlr).name shouldBe "Paul"
      }
      "is second player" in {
        controller.gameTable.getCurrPlr should be (1)
      }
      "places the third card of the curr plr" in {
        val thirdcard = controller.getGameTable.player(controller.getGameTable.getCurrPlr).cards(2)
        controller.evaluate("2")
        controller.getGameTable
          .placedWhiteCards(controller.getGameTable.player(controller.getGameTable.getCurrPlr))
          .head should be (thirdcard)
      }
    }
    "finishes" in {
      controller.evaluate("")
      controller.state.getCurrState shouldBe "final state"
    }
  }
}
