package model

case class WhiteCard(text: String) extends Card {
  override def toString: String = text

  override def printCard(): Unit = toString

  override def addNew(card: Card): Card = throw UnsupportedOperationException
  override def remove(card: Card): Card = throw UnsupportedOperationException
}
