package model

import scala.util.Random

case class GameTable(nrOfPlrs: Int = 0, currPlr: Int = 0, nrOfRounds: Int = 0, currRound: Int = 0,
                     cardDeck: CardDeck = CardDeck(List[String](), List[String]()),
                     var player: Vector[Player] = Vector[Player](),
                     var whiteCards: List[WhiteCard] = List[WhiteCard](),
                     var blackCards: List[BlackCard] = List[BlackCard](),
                     placedWhiteCards: Map[Player, String] = Map[Player, String](),
                     var currBlack: String = "") extends ModelInterface {

  override def initGame(nrOfPlrs: Int): GameTable = {
    setNrRounds(nrOfPlrs)
    GameTable.Builder()
      .withNrOfRounds(this.nrOfRounds)
      .withNrOfPlrs(this.nrOfPlrs)
      .build()
  }

  override def setNrRounds(nrOfPlrs: Int): GameTable = {
    if (nrOfPlrs == 2) copy(nrOfRounds = 4)
    else if (nrOfPlrs == 3) copy(nrOfRounds= 3)
    else copy(nrOfRounds = 4)
  }

  override def addPlr(name: String): GameTable = {
    copy(player = player :+ Player(name, List[WhiteCard](), isOnIt = true))
  }

  override def createDeck(deck: CardDeck): GameTable = {
    val whites = this.whiteCards
    val blacks = this.blackCards
    for (o <- cardDeck.whites) whites :+ o
    for (o <- cardDeck.blacks) blacks :+ o
    copy(whiteCards = whites, blackCards = blacks)
  }

  override def setCardDeck(deck: CardDeck): GameTable = copy(cardDeck = deck)

  override def getWhitesOrBlacks(color: String): List[Card] = {
    if (color == "blacks") blackCards
    else if (color == "whites") whiteCards
    else throw new Exception("parameter in getCardDeck must either be whites or blacks")
  }

  override def getDeck: CardDeck = cardDeck

  override def createHand(): List[WhiteCard] = {
    val whiteDeck = Random.shuffle(whiteCards)
    var handOutWhites = List[WhiteCard]()
    var count = 0
    for (x <- whiteDeck if whiteDeck.nonEmpty; if count < 5) {
      handOutWhites = handOutWhites :+ x
      count += 1
    }
    handOutWhites
  }

  override def setPlrHands(): Vector[Player] = {
    for (x <- player) {
      x.cards = createHand()
    }
    player
  }

  override def handOutCards(): GameTable = {
    setPlrHands()
    copy()
  }

  override def drawWhiteCard(): GameTable = {
    var whites = Random.shuffle(whiteCards)
    var plr = Vector[Player]()
    for (x <- plr) {
      val card = whites.head
      whites = whites.filterNot(_ == card)
      plr = plr :+ Player(x.name, x.cards :+ card, x.isOnIt)
    }
    copy(whiteCards = whites, player = plr)
  }

  override def showBlackCard(): GameTable = {
    var reduced = blackCards
    reduced = Random.shuffle(reduced)
    val black = reduced.head
    val round = currRound + 1
    copy(blackCards = reduced.filterNot(_ == black), currBlack = black.text1, currRound = round)
  }

  override def setPlrAnswer(currPlayer: Int, answer: WhiteCard): GameTable = {
    var currPlaced = placedWhiteCards
    currPlaced = currPlaced + (player(currPlayer) -> answer.text1)
    copy(placedWhiteCards = currPlaced)
  }

  override def getCurrPlr: Int = currPlr

  override def setNextPlr(): GameTable = copy(currPlr = (currPlr + 1) % nrOfPlrs)

  override def clearRound(): GameTable = {
    copy(placedWhiteCards = Map[Player, String]())
  }

  override def printGT(): Unit = {
    val output = "white cards: " + whiteCards.toString() + "\nblack cards: " + blackCards.toString() +
      s"\ncurrent black card: $currBlack\ndisplayed white cards: ${placedWhiteCards.toString()}\n" +
      ""
    println(output)
    for (x <- player) {
      println(s"current cards from ${x.name}: ${x.cards.toString()}\nnr of plr: $nrOfPlrs")
    }
  }

  override def getGT: GameTable = this

  object GameTable {
    case class Builder() {
      var nrOfPlrs: Int = 0
      var nrOfRounds: Int = 0

      def withNrOfPlrs(players: Int): Builder = {
        nrOfPlrs = players
        this
      }

      def withNrOfRounds(rounds: Int): Builder = {
        nrOfRounds = rounds
        this
      }

      def build(): GameTable = {
        model.GameTable(nrOfPlrs, nrOfRounds)
      }
    }
  }

}
