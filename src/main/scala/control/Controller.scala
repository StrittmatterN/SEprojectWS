package control

import model._

import scala.collection.mutable.ListBuffer


class Controller(var gameTable: GameTable) {

  var currPlr = 0
  var round = 0

  def init(cardDeck: CardDeck, player: Vector[Player]): Unit = {
    var whiteListBuf = ListBuffer[WhiteCard]()
    var blackListBuf = ListBuffer[BlackCard]()
    for (c <- cardDeck.whites) whiteListBuf += WhiteCard(c)
    for (c <- cardDeck.blacks) blackListBuf += BlackCard(c)

    val whiteList = whiteListBuf.toList
    val blackList = blackListBuf.toList

    println(whiteList)
    println(blackList)
    gameTable = GameTable(cardDeck, player, whiteList, blackList, null, null)
  }

  def drawCards(currPlr: Int, n: Int): Unit = {
    gameTable = gameTable.drawCards(currPlr, n)
  }

  def drawBlack(): Unit = {
    gameTable = gameTable.drawBlack()
    println(gameTable.currBlack)
  }

  def put(index: Int): Unit = {
    val white = gameTable.player(currPlr).cards(index)
    gameTable = gameTable.placeWhite(currPlr, white)
    currPlr = (currPlr + 1) % gameTable.player.length
  }

}
