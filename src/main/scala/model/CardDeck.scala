package model

case class CardDeck(var whites: List[String], var blacks: List[String]) {
  def printWhites: String = whites.toString()
  def printBlacks: String = blacks.toString()
}
