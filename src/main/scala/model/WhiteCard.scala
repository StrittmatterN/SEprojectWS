package model

case class WhiteCard(text: String) extends Card {
  override def toString: String = text

  override def printCard(): Unit = println(text)

  override def createNewCard(text: String): Card = WhiteCard(text)
}
