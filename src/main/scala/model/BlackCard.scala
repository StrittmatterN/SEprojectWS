package model

case class BlackCard(text: String) extends Card {

  override def toString: String = text

  override def printCard(): Unit = println(text)

  override def createNewCard(text: String): Card = BlackCard(text)
}
