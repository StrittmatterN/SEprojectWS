package model

import scala.collection.mutable.ListBuffer

case class GameTable(cardDeck: CardDeck, player: Vector[Player], whiteCards: List[WhiteCard],
                     blackCards: List[BlackCard], placedWhiteCards: Map[Player, String], var currBlack: String) {


  def createDeck(cardDeck: CardDeck): GameTable = {

    val whiteList = ListBuffer[WhiteCard]()
    val blackList = ListBuffer[BlackCard]()

    for (white <- cardDeck.whites) whiteList.addOne(WhiteCard(white))
    for (black <- cardDeck.blacks) blackList.addOne(BlackCard(black))

    val wl = whiteList.toList
    val bl = blackList.toList

    copy(cardDeck, player, wl, bl)
  }


  def drawCards(currPlr: Int, n: Int): GameTable = {

    var count = 0
    var wList = List[WhiteCard]()
    wList = wList ++ whiteCards
    var pVec = Vector[Player]()


    for (p <- player) {
      var tmpWhites = List[WhiteCard]()
      while (count < n) {
        tmpWhites = tmpWhites :+ wList(count)
        count = (count + 1) % whiteCards.length
      }
      pVec = pVec :+ Player(p.name, tmpWhites)
    }
    copy(cardDeck, pVec, wList, blackCards, placedWhiteCards, currBlack)
  }


  def drawBlack (): GameTable = {
    var newBlackList = List[BlackCard]()
    newBlackList = newBlackList ++ blackCards
    val black = blackCards.head
    newBlackList = newBlackList.filterNot(_ == black)
    copy(cardDeck, player, whiteCards, newBlackList, placedWhiteCards, black.toString)
  }

  def placeWhite (plr: Int, card: WhiteCard): GameTable = {
    var placedCardMap = Map[Player, String]()

    if (placedWhiteCards != null) placedCardMap ++= placedWhiteCards

    var plrCards = List[WhiteCard]()
    plrCards ++= player(plr).cards
    placedCardMap += (player(plr) -> card.toString)
    plrCards = plrCards.filterNot(_ == card)

    var pVec: Vector[Player] = Vector()
    pVec = pVec ++ player
    pVec = pVec.updated(plr, Player(player(plr).name, plrCards))

    copy(cardDeck, pVec, whiteCards, blackCards, placedCardMap, currBlack)
  }

  override def toString: String = {
    val sb = new StringBuilder
    sb ++= "Hey"
    sb.toString()
  }





}
