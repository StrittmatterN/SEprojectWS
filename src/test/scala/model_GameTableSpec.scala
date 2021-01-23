import model.{CardDeck, GameTable, WhiteCard}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class model_GameTableSpec extends AnyWordSpec with Matchers {
  "A GameTable" should { "new" should {
    var gameTable = new GameTable
    "inits the game" in {
      gameTable = gameTable.initGame(2)
    }
    "have a player size" in {
      gameTable.player.length should be (2)
    }
    "have players" in {
      gameTable.addPlr("Niklas")
      gameTable.addPlr("Paul")
      gameTable.player(0).name should be ("Niklas")
      gameTable.player(1).name should be ("Paul")
    }

    "creates a deck" in {
      val cardDeck = CardDeck(List[String]("I'm a white card"), List[String]("I'm a black _"))
      gameTable = gameTable.createDeck(cardDeck)
      gameTable.whiteCards.length should be (1)
      gameTable.blackCards.length should be (1)
      gameTable.whiteCards.head.text1 should be ("I'm a white card")
    }

    "handouts the cards" in {
      gameTable = gameTable.handOutCards()
      gameTable.player(0).cards shouldNot be (null)
      gameTable.player(0).cards.isEmpty
    }

    "place a white card for a player" in {
      gameTable = gameTable.setPlrAnswer(0, WhiteCard("Dagobert Duck"))
    }

    "set the next current player" in {
      gameTable = gameTable.setNextPlr()
      gameTable.currPlr should be (1)
    }

  }
  }
}
