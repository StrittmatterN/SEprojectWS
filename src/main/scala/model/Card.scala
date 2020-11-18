package model

trait Card {
  def printCard()
  def addNew (card: Card): Card
  def remove (card: Card): Card
}

case class Cards(cardList: List[Card]) extends Card {

  override def printCard(): Unit = cardList.foreach((c: Card) => c.printCard())

  override def addNew(card: Card): Cards = {
    val tmp = cardList :+ card
    copy(tmp)
  }

  override def remove(card: Card): Cards = copy(cardList.filterNot(_ == card))
}
