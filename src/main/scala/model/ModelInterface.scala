package model

trait ModelInterface {
  def initGame(nrOfPlrs: Int): GameTable
  def setNrRounds(nrOfPlrs: Int): GameTable
  def addPlr(name: String): GameTable
  def createDeck(deck: CardDeck): GameTable
  def setCardDeck(deck: CardDeck): GameTable
  def getDeck: CardDeck
  def createHand(): List[WhiteCard]
  def setPlrHands(): Vector[Player]
  def showBlackCard(): GameTable
  def setPlrAnswer(currPlayer: Int, answer: WhiteCard): GameTable
  def getCurrPlr: Int
  def setNextPlr(): GameTable
  def clearRound(): GameTable
  def handOutCards(): GameTable
  def printGT()
  def getGT: GameTable
}
